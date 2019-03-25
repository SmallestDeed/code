package com.sandu.analysis.biz.page.realtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.Optional;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.analysis.biz.page.model.RealtimeMetrics;
import com.sandu.analysis.biz.page.model.RealtimeMetricsResultVo;
import com.sandu.analysis.kafka.KafkaUtil;

import scala.Tuple2;

public class PageAnalysisStreaming {

	public static void main(String[] args) throws InterruptedException {
		SparkConf conf = new SparkConf().setAppName("StreamingTest")
				.set("spark.locality.wait", "100ms");
		JavaStreamingContext streamingContext = new JavaStreamingContext(conf, Durations.seconds(1));
		streamingContext.checkpoint("hdfs://bigdatadev4:8020/data001/spark/StreamingTest/streaming_checkpoint");
		
		Map<String, Object> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", "192.168.1.240:9092,192.168.1.241:9092,192.168.1.242:9092");
		kafkaParams.put("key.deserializer", StringDeserializer.class);
		kafkaParams.put("value.deserializer", StringDeserializer.class);
		kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
		kafkaParams.put("auto.offset.reset", "latest");
		kafkaParams.put("enable.auto.commit", true);
		//kafkaParams.put("enable.auto.commit", false);

		Collection<String> topics = Arrays.asList("flume-event-log-topic2");
		JavaInputDStream<ConsumerRecord<String, String>> stream =
				  KafkaUtils.createDirectStream(
				    streamingContext,
				    LocationStrategies.PreferConsistent(),
				    ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
				  );
		JavaPairDStream<String,Long> resultDStream = stream
		//过滤
		.filter(record->{
			String[] logFields = record.value().split("\t");
			String eventName = logFields[3];
			String eventProperty = logFields[4];
			if ("pageview".equals(eventName) && eventProperty != null && eventProperty.contains("curpage")) {
				return true;
			}
			return false;
		})
		//映射成key,value形式
		.mapToPair(filterRecord->{
			String[] logFields =filterRecord.value().split("\t");
			String eventProperty = logFields[4];
			String curpage = "";
			String[] properties = eventProperty.split(",");
	    	for(String property : properties) {
	    		String[] propEntity = property.split(":");
	    		String propName = propEntity[0];
	    		String propValue = propEntity[1];
	    		if("curpage".equals(propName)) {
	    			curpage = propValue;
	    			break;
	    		}
	    	}
			return new Tuple2<String, Long>(curpage, 1L);
		})
		//根据key进行累加
		.updateStateByKey((values, state) -> {
			Long viewCount = 0L;
			if (state.isPresent()) {
				viewCount = state.get();
			}
			for (Long value : values) {
				viewCount += value;
			}
			return Optional.of(viewCount);
		  });
		
		//输出结果
		resultDStream.foreachRDD(rdd->{
			rdd.foreachPartition(resultRecordIter->{
				List<RealtimeMetrics> list = new ArrayList<RealtimeMetrics>();
				while (resultRecordIter.hasNext()) {
					Tuple2<String, Long> tuple = resultRecordIter.next();
					String key = tuple._1;
					Long viewCount = tuple._2;
					RealtimeMetrics vo = new RealtimeMetrics();
					vo.setName(key);
					vo.setCount(viewCount);
					list.add(vo);
				}

				if (list.size() > 0) {
					RealtimeMetricsResultVo resultVo = new RealtimeMetricsResultVo();
					resultVo.setType("page_view");
					resultVo.setList(list);
					ObjectMapper mapper = new ObjectMapper();
					String resultJson = mapper.writeValueAsString(resultVo);
					KafkaUtil.send("realtime-result-topic2", resultJson);
				}
			});
		});
		
		streamingContext.start();
		streamingContext.awaitTermination();
		streamingContext.close();
		
		
		
	}
}
