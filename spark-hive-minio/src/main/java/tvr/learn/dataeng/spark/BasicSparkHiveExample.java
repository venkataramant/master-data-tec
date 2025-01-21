package tvr.learn.dataeng.spark;


import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class BasicSparkHiveExample {

    public static void main(String[] args) throws Exception {
        System.out.print("raman");
        SparkSession ss = SparkSession.builder()
                .appName("BasicSparkExample")
                .master("local[*]")
                .config("hive.metastore.uris", "thrift://localhost:9083")
                .config("spark.sql.warehouse.dir", "hdfs://127.0.0.1:9000/user/hive/warehouse")
//    .config("spark.jars", "/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/iceberg-spark-1.5.2.jar,/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/iceberg-hive-runtime-1.5.2.jar,/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/hadoop-aws-3.4.0.jar,/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/aws-java-sdk-bundle-1.12.761.jar,/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/hadoop-common-3.3.6.jar")
//    .config("spark.sql.hive.metastore.jars", "/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/iceberg-spark-1.5.2.jar,/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/iceberg-hive-runtime-1.5.2.jar,/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/hadoop-aws-3.4.0.jar,/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/aws-java-sdk-bundle-1.12.761.jar,/Users/ramj/tvr-works/gh/venkataramant/local-data-lake/libs/hive_aux_jars/hadoop-common-3.3.6.jar")

				.enableHiveSupport()
                .getOrCreate();
//		ss.catalog();
        // Create a Hive database
        ss.sql("CREATE DATABASE IF NOT EXISTS test_db");
        // Create an Iceberg table in the Hive database
        ss.sql("CREATE TABLE IF NOT EXISTS test_db.iceberg_table (id INT, data STRING) USING iceberg");
        // Define schema for the DataFrame
        StructType schema = DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("id", DataTypes.IntegerType, false),
                DataTypes.createStructField("data", DataTypes.StringType, false)
        });
        // Initialize JavaSparkContext
        try (JavaSparkContext jsc = new JavaSparkContext(ss.sparkContext())) {
            // Insert some data into the Iceberg table
            List<MyRecord> data = Arrays.asList(
                    new MyRecord(1, "foo"),
                    new MyRecord(2, "bar"),
                    new MyRecord(3, "baz")
            );


            // Create a Dataset<Row> from the data
//			Dataset<Row> df = ss.createDataFrame(rdd, schema);
            var df = ss.createDataFrame(data, MyRecord.class);
            df.show();
//            df.writeTo("test_db.iceberg_table");
            df.write()
                    .format("parquet")
                    .mode("overwrite")
                    .save("hdfs://127.0.0.1:9000/user/hive/warehouse/test_db/iceberg_table");
            ss.stop();
        }


    }

}
