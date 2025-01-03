package lrn.tvr.course.spark.basic;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SparkFirstProgram {

	public static void main(String... strings) {
		try (final var sSession = SparkSession.builder().appName("Spark1stProgram").master("local[*]").getOrCreate()) {
			final var sContext = new JavaSparkContext(sSession.sparkContext());
			log.info("sContext is created ", sContext);
			final var intStream = Stream.iterate(1, n -> n + 1).limit(10).collect(Collectors.toList());
			System.out.println("max from streams::" + intStream.stream().reduce((x1, x2) -> x1 > x2 ? x1 : x2));
			JavaRDD<Integer> intRDD = sContext.parallelize(intStream);
			System.out.println("intRDD...count::" + intRDD.count() + " part::" + intRDD.getNumPartitions());
//			intRDD.filter(x -> x % 2 == 0).map(n -> n * n).saveAsTextFile("numbers");
			var intSum = intRDD.reduce((x1, x2) -> x1 + x2);
			log.error("intSum:: {}", intSum);
			var intMax = intRDD.reduce((x1, x2) -> x1 > x2 ? x1 : x2);
			log.error("intMax:: {}", intMax);
			var intMin = intRDD.reduce((x1, x2) -> x1 > x2 ? x2 : x1);
			log.error("intMin:: {}", intMin);

			try (final var scanner = new Scanner(System.in)) {
				scanner.nextLine();
			}
		}

	}

}
