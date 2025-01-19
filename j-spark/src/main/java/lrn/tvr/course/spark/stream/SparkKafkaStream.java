package lrn.tvr.course.spark.stream;

import java.util.Arrays;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import scala.Tuple2;

public class SparkKafkaStream {

	public static void main(String[] args) throws InterruptedException {
		var jsc = new JavaStreamingContext("local[*]", "KafkaStreamingApp", Durations.seconds(15));
		Map<String, Object> kParams = Map.of(
				"bootstrap.servers", "PLAINTEXT://kafka_host:9092", "key.deserializer", IntegerDeserializer.class,
				"value.deserializer", StringDeserializer.class,
				"auto.offset.reset" , "latest",
				"enable.auto.commit" , false,
				"group.id","spark-stream-client-1");
		JavaInputDStream<ConsumerRecord<Integer, String>> messages = KafkaUtils.createDirectStream(
				jsc, LocationStrategies.PreferConsistent(),
				ConsumerStrategies.<Integer, String>Subscribe(Arrays.asList("spark-stream-topic-1"), kParams));
		JavaPairDStream<String, Long> data = messages.mapToPair(cr -> new Tuple2<Integer, String>(cr.key(), cr.value()))
//				.filter(t -> t._1 >10)
				.mapToPair(t -> new Tuple2<String, Long>(t._2, 1l))
				.reduceByKey((v1, v2) -> v1 + v2);
		data.print(50);
		jsc.start();
		jsc.awaitTermination();
	}

}
