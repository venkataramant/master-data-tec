package lrn.tvr.course.spark.stream;

import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerClientMain {

	public static void main(String[] args) {
		Properties config = new Properties();
//			config.put("client.id", InetAddress.getLocalHost().getHostName());
		config.put("bootstrap.servers", "localhost:9092");
		config.put("acks", "all");
		config.put("key.serializer", IntegerSerializer.class);
		config.put("value.serializer", StringSerializer.class);
		var movies = getMovies();
		try (var producer = new KafkaProducer<Integer, String>(config)) {
			var KeyRandomer = new Random();
			var valueRandomer = new Random();
			System.out.println(producer);
			int index = 0;
			Random random = new Random();

			while (index < 1000000) {
				var key = random.nextInt(movies.size()) + 1;
				var value = movies.get(key);
				var pr = new ProducerRecord<Integer, String>("spark-stream-topic-1", key, value);
				producer.send(pr);
				producer.flush();
				index++;
				Thread.sleep(500);
			}
			System.out.println("sent " + index + " records");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<Integer, String> getMovies() {
		return Map.of(
				1, "The Shawshank Redemption", 2, "The Godfather", 3, "Great Champion", 4, "The Dark Knight", 5,
				"12 Angry Men", 6, "Schindler's List");

	}

}
