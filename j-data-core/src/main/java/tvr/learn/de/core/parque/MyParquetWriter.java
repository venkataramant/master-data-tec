package tvr.learn.de.core.parque;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.util.HadoopOutputFile;

public class MyParquetWriter {

	public static Schema getSchema(String pathName) throws IOException {
		return new Schema.Parser().parse(new File(pathName));
	}

	public static void main(String... args) {
		try {
			Schema userSchema = getSchema("src/main/resources/StudentAvroSchema.json");
			System.out.println(userSchema);
			String nameNodeURL = args[0];
			String usersParquetFileName = args[1];
			Configuration conf=new Configuration();
			conf.set("fs.defaultFS", nameNodeURL);
			conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
			HadoopOutputFile hoFile= HadoopOutputFile.fromPath(new Path(usersParquetFileName),conf);
			ParquetWriter<GenericRecord> apWriter = AvroParquetWriter.<GenericRecord>builder(hoFile)
					.withSchema(userSchema)
					.withConf(conf)
					.build();
			Random random = new Random();
			for (int i = 0; i < 5; i++) {
				GenericRecord user = new GenericData.Record(userSchema);
				user.put("firstName", "student" + i);
				user.put("rollNumber", i);
				user.put("GPA", random.nextDouble(5));
				user.put("active", random.nextInt(2) == 0 ? Boolean.FALSE : Boolean.TRUE);
				apWriter.write(user);
			}
			apWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}