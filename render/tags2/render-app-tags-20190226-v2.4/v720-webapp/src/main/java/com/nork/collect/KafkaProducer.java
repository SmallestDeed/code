package com.nork.collect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KafkaProducer<T> {
	private final static Logger LOGGER = LogManager.getLogger(KafkaProducer.class);
	private String topic;
	private String bootstrapServers;
	private static ObjectMapper mapper = new ObjectMapper();
	private Producer<String, String> producer;
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 10L, TimeUnit.SECONDS,
			new ArrayBlockingQueue(20), new ThreadFactoryBuilder().setNameFormat("KafkaProducer-%d").build(),
			new RejectedMessageProducer());

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setBootstrapServers(String bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}

	public void init() {
		LOGGER.info("Kafka Producer Info: bootstrap-servers=" + this.bootstrapServers + ", topic=" + this.topic);

		Properties props = new Properties();
		props.put("bootstrap.servers", this.bootstrapServers);
		props.put("acks", "1");
		props.put("key.serializer", StringSerializer.class);
		props.put("value.serializer", StringSerializer.class);

		this.producer = new org.apache.kafka.clients.producer.KafkaProducer(props);
	}

	public void close() {
		this.producer.close();
	}

	public void send(T obj) {
		String message = toJson(obj);
		this.executor.execute(new SendWorker(this.producer, this.topic, message));
	}

	private String toJson(T obj) {
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	private byte[] toJsonBytes(T obj) {
		byte[] json = null;
		try {
			json = mapper.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	public class RejectedMessageProducer implements RejectedExecutionHandler {
		public RejectedMessageProducer() {
		}

		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			KafkaProducer.LOGGER.error("Task " + r.toString() + " rejected from " + executor.toString());
		}
	}

	public class SendWorker implements Runnable {
		private Producer<String, String> producer;
		private String topic;
		private String content;

		public SendWorker(Producer<String, String> producer, String topic, String content) {
			this.producer = producer;
			this.topic = topic;
			this.content = content;
		}

		public void run() {
			if (this.producer != null) {
				Future future = this.producer.send(new ProducerRecord(this.topic, this.content));
				try {
					RecordMetadata recordMetadata = (RecordMetadata) future.get();
					KafkaProducer.LOGGER.info("Finish Sended: topic=" + recordMetadata.topic() + ", partition="
							+ recordMetadata.partition() + ", offset=" + recordMetadata.offset() + ", timestamp="
							+ recordMetadata.timestamp());
				} catch (InterruptedException | ExecutionException e) {
					KafkaProducer.LOGGER.error("Exception: " + e.getMessage());
				}
			} else {
				KafkaProducer.LOGGER.error("Don't send, because connection server failure. topic=" + this.topic);
			}
		}
		
	}
}