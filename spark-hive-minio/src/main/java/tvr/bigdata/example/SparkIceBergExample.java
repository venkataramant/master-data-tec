package tvr.bigdata.example;
import org.apache.iceberg.catalog.TableIdentifier;
import org.apache.iceberg.spark.SparkCatalog;
import org.apache.iceberg.spark.SparkSessionCatalog;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.SaveMode;

import java.util.HashMap;
import java.util.Map;
public class SparkIceBergExample {
    public static void main(String[] args) {


        // Create Spark Session
        SparkSession spark = SparkSession.builder()
                .appName("Spark Iceberg MinIO")
                .config("spark.sql.catalog.spark_catalog", "org.apache.iceberg.spark.SparkSessionCatalog")
                .config("spark.sql.catalog.spark_catalog.type", "hive")
                .config("spark.sql.catalog.spark_catalog.warehouse", "thrift://localhost:9083")
                .config("spark.hadoop.fs.s3a.endpoint", "http://localhost:9000")
                .config("spark.hadoop.fs.s3a.access.key", "minio_access_key")
                .config("spark.hadoop.fs.s3a.secret.key", "minio_secret_key")
                .config("spark.hadoop.fs.s3a.path.style.access", "true")
                .config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
                .config("spark.hadoop.fs.s3a.connection.ssl.enabled", "false")
                .getOrCreate();

        // Read JSON file into DataFrame
        Dataset<Row> df = spark.read().json("nbgames.json");

        // Print schema for verification
        df.printSchema();

        // Write DataFrame to Iceberg table
        df.write()
                .format("iceberg")
                .mode(SaveMode.Overwrite)
                .save("spark_catalog.mydatabase.my_nb_games");

        // Stop Spark session
        spark.stop();
    }
}
