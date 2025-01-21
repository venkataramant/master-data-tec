package tvr.learn.dataeng.spark;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class SparkHadoop {
    public static void main(String[] args) {
        // Set up the Spark configuration
        SparkConf conf = new SparkConf()
                .setAppName("RemoteSparkConnection")
                .setMaster("local[*]"); // Replace with your remote Spark master URL

        // Initialize JavaSparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.s
        // Initialize SparkSession
        SparkSession spark = SparkSession.builder()
                .appName("RemoteSparkConnection")
                .config(conf)
                .getOrCreate();

        // Example: Load a simple DataFrame
        spark.read().json("hdfs://<hdfs-host>:9000/path/to/your/json/data")
                .show();

        // Stop the context
        sc.stop();
        spark.stop();
    }
}
