package tvr.bigdata.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

public class SparkIceBergExample2 {
	public static void main(String[] args) {

		// Create Spark Session
		var builder = SparkSession.builder().appName("Spark Iceberg MinIO").master("local[*]")
				.config("spark.sql.catalog.spark_catalog", "org.apache.iceberg.spark.SparkSessionCatalog")
				.config("spark.sql.catalog.spark_catalog.type", "hive")
				.config("spark.sql.catalog.spark_catalog.warehouse", "thrift://localhost:9083")
				.config("spark.hadoop.fs.s3a.endpoint", "http://localhost:9000")
				.config("spark.hadoop.fs.s3a.access.key", "minio_access_key")
				.config("spark.hadoop.fs.s3a.secret.key", "minio_secret_key")
				.config("spark.hadoop.fs.s3a.path.style.access", "true")
				.config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
				.config("spark.hadoop.fs.s3a.connection.ssl.enabled", "false");
		SparkSession spark = builder.getOrCreate();

		// Stop Spark session
		spark.stop();
	}
}
