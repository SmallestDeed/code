package com.sandu.analysis.kafka;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaUtil {
	static Properties props = new Properties();
	static {
		props.put("bootstrap.servers", "192.168.1.240:9092,192.168.1.241:9092,192.168.1.242:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		//props.put("batch.size", 16384);
		//props.put("linger.ms", 1);
		//props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	}
	
	public static void send(String topic, String msg) {
		Producer<String, String> producer = new KafkaProducer<>(props);
		producer.send(new ProducerRecord<String, String>(topic, msg), new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception e) {
				if (e != null) {
					e.printStackTrace();
				} else {
					//System.out.printf("topic = %s, offset = %d%n", metadata.topic(), metadata.offset());
				}
			}
		});
		// producer.send(new ProducerRecord<String, String>(topic2, msg));
		producer.close();
	}
 
	  
 
	
    public static void main(String[] args) throws Exception {
        String topic = "realtime-result-topic";
        KafkaUtil.send(topic, "aaa");
    }
	

}
