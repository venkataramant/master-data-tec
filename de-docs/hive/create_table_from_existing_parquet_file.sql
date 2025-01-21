CREATE EXTERNAL TABLE IF NOT EXISTS s_db1.students (
  rollNumber INT,
  firstName STRING,
  GPA double,
  active boolean
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS INPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'
LOCATION '/user/spark/warehouse/s_db1.db/students/users.parquet';

CREATE TABLE IF NOT EXISTS taxis(
  vendor_id bigint,
  trip_id bigint,
  trip_distance float,
  fare_amount double,
  store_and_fwd_flag string
) using iceberg;

// Add iceberg spark runtime jar to spark/jars folder
CREATE TABLE IF NOT EXISTS taxis(
  vendor_id bigint,
  trip_id bigint,
  trip_distance float,
  fare_amount double,
  store_and_fwd_flag string
) using iceberg;
STORED AS  parquet;