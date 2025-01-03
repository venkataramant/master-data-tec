package lrn.tvr.course.spark.sql;

import java.util.Properties;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PostgresJDBCSparkMain {
	public static void main(String... args) {

		String driverClass = "org.postgresql.Driver";
		String jdbcURL = "jdbc:postgresql://localhost:5432/myschool";
		Properties credentials = new Properties();
		credentials.setProperty("user", "postgres");
		credentials.setProperty("password", "secret123");
		credentials.setProperty("Driver", driverClass);

		var sparkSession = SparkSession.builder().appName("jdbc-postgres").master("local[*]").getOrCreate();
		var maxStudentIdDF = sparkSession.read().jdbc(jdbcURL, "(select max(id)  max_id from student) as q1",
				credentials);
		Encoder<Integer> integerEncoder = Encoders.INT();
		Dataset<Integer> maxId=maxStudentIdDF.map((MapFunction<Row, Integer>) row -> row.getInt(0),integerEncoder);
//		System.out.println(maxId.);
		var studentsCSV = sparkSession.read().format("csv").option("inferSchema", true).option("header", true)
				.load("students.csv");
		studentsCSV.show();
//		studentsCSV.write()
//		.mode(SaveMode.Append)
//		.jdbc(jdbcURL, "student", credentials);
		var studentTable = sparkSession.read().jdbc(jdbcURL, "(select * from student where id=1) as q1", credentials);
		studentTable.show();

	}
}
