package lrn.tvr.course.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.*;

import static lrn.tvr.course.spark.util.LocalUtil.*;

public class SparkSQLBasciApp {
	String fileName;

	public SparkSQLBasciApp(String fileName) {
		this.fileName = fileName;

	}

	public void countLogLevels() {
		var sqlContext = getSQLContext();

		Dataset<Row> rows = sqlContext.read().option("header", true).csv(this.fileName);
		rows.show();
		System.out.println(rows.count());
		var COL_LEVEL = col("level");
		var COL_MONTH = col("month");
		var COL_DT = col("datetime");
		var groupRows = rows.groupBy(COL_LEVEL, date_format(COL_DT, "MM").alias("month"))
				.agg(COL_LEVEL, COL_MONTH)
				.orderBy(COL_LEVEL, COL_MONTH);
		groupRows.show();
	}

	public static void main(String[] args) {
		var fileName = getFileName(args);
		var app = new SparkSQLBasciApp(fileName);
		app.countLogLevels();
		waitForUserInput();
	}

	public SQLContext getSQLContext() {
		return new SQLContext(new SparkContext(new SparkConf().setMaster("local[*]").setAppName("SparkSQLBasicAPP")));
	}

	public SparkSession getSQLSession() {
		return SparkSession.builder()
				.config(new SparkConf().setMaster("local[*]").setAppName("SparkSQLBasicAPP"))
				.getOrCreate();
	}
}
