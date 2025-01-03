package lrn.tvr.course.spark.core;

import java.util.Scanner;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

public class SparkCoreApp {

	private static String getFileName(String[] args) {
		String fileName;
		if (args.length > 0) {
			fileName = args[0];
		} else {
			try (var scanner = new Scanner(System.in)) {
				fileName = scanner.nextLine();
			}
		}
		return fileName;
	}

	public static void main(String[] args) {
		var fileName = getFileName(args);
		var scApp = new SparkCoreApp(fileName);
		var sContext = scApp.getSparkContext1();
		JavaRDD<String> plainRDD = sContext.textFile(scApp.fileName, 0);
		scApp.countLogLevels(plainRDD);
		try (var scanner = new Scanner(System.in)) {
			scanner.nextLine();
		}
		

	}

	String fileName;

	public SparkCoreApp(String fileName) {
		this.fileName = fileName;
	}

	public void countLogLevels(JavaRDD<String> plainRDD) {
		

		JavaRDD<String> words = plainRDD.map(v1 -> v1.split(",")[0]);
		System.out.println("sContext no Of Partitions::" + plainRDD.getNumPartitions());
		JavaPairRDD<String, Long> wordsCountPairRDD = words.mapToPair(v1 -> new Tuple2<>(v1, 1l));
		JavaPairRDD<String, Long> reducedWordsCountPairRDD = wordsCountPairRDD.reduceByKey((t1, t2) -> t1 + t2);
		reducedWordsCountPairRDD = reducedWordsCountPairRDD.filter(t1 -> !t1._1.equals("level"));
		JavaRDD<Tuple2<Long, String>> swappedPairRDD = reducedWordsCountPairRDD.map(t1 -> new Tuple2<>(t1._2, t1._1));
		swappedPairRDD.foreach(x -> System.out.println(x._1 + "--" + x._2));

	}

	public JavaSparkContext getSparkContext1() {
		SparkConf sConfig = getSparkConf();
		var sContext = new JavaSparkContext(sConfig);
		return sContext;

	}

	public JavaSparkContext getSparkContext2() {
		SparkConf sConfig = getSparkConf();
		var sContext = SparkContext.getOrCreate(sConfig);
		return new JavaSparkContext(sContext);

	}

	public SparkSession getSparkSession1() {
		SparkConf sConfig = getSparkConf();
		var sContext = SparkContext.getOrCreate(sConfig);
		return new SparkSession(sContext);

	}

	public SparkSession getSparkSession2() {
		return SparkSession.builder().appName("SparkSessionApp").master("local[*]").getOrCreate();

	}

	public SparkConf getSparkConf() {
		SparkConf sConfig = new SparkConf();
		sConfig.setAppName("lrn-tvr-spark-core-01");
		sConfig.setMaster("local[*]");

		return sConfig;

	}

}
