package tvr.lrn.hadoop.hdfs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSFileWriter {

	public static void main(String[] args) throws IOException, URISyntaxException {
		String hdfsSystem = args[0];
		String filePath = args[1];
		FileSystem hdfs = FileSystem.get(new URI(hdfsSystem), new Configuration());
		Path hdfsFilePath = new Path(hdfsSystem + "/" + filePath);
		OutputStream opStream = hdfs.exists(hdfsFilePath) ? hdfs.append(hdfsFilePath) : hdfs.create(hdfsFilePath);
		try (BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(opStream));
				Scanner scanner = new Scanner(System.in)) {
			String line = null;
			line = scanner.nextLine();
			while (line != null && !line.isEmpty()) {
				System.out.println(line);
				bWriter.write(line + System.lineSeparator());
				line = scanner.nextLine();
			}
		}
	}

}
