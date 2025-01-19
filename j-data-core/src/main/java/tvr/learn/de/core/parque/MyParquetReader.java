package tvr.learn.de.core.parque;

import java.io.IOException;

import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;

public class MyParquetReader {

	public static void main(String[] args) {
		String nameNodeURL = args[0];
		String usersParquetFileName = args[1];
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", nameNodeURL);
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		HadoopInputFile hInputFile;
		try {
			hInputFile = HadoopInputFile.fromPath(new Path(usersParquetFileName), conf);
			org.apache.parquet.hadoop.ParquetReader<GenericRecord> reader = AvroParquetReader
					.<GenericRecord>builder(hInputFile)
					.build();
			GenericRecord record=null;
			while((record=reader.read())!=null) {
				System.out.printf("row count %d\n", reader.getCurrentRowIndex());
				System.out.printf("firstName::%s GPA::%f\n", record.get("firstName"),record.get("GPA"));
			}
			
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}

	}

}
