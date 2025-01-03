package lrn.tvr.course.spark.core;

import java.util.Scanner;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.*;

import scala.Tuple2;

public class SparkCoreApp2 {

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
		var scApp = new SparkCoreApp2(fileName);
		var sSession = scApp.getSparkSession1();
		Dataset<Row> dataset = sSession.read().option("header", true).csv(scApp.fileName);
		System.out.println(dataset.count());
		dataset.show();
		var countDataset = dataset.groupBy(col("level"), date_format(col("datetime"), "M").alias("month"))
				.agg(col("level"), col("month"))
				.orderBy(col("month"),col("level"));
		countDataset.show();
		
		try (var scanner = new Scanner(System.in)) {
			scanner.nextLine();
		}

	}

	String fileName;

	public SparkCoreApp2(String fileName) {
		this.fileName = fileName;
	}

	

	public JavaSparkContext getSparkContext1() {
		SparkConf sConfig = getSparkConf();
		var sContext = new JavaSparkContext(sConfig);
		return sContext;

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
