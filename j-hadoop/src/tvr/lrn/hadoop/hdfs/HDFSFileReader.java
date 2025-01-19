package tvr.lrn.hadoop.hdfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSFileReader {

	public static void main(String[] args) throws IOException, URISyntaxException {
		String nameNode = args[0];
		String filePath = args[1];
		FileSystem hdfs=FileSystem.get(new URI(nameNode),new Configuration());
		Path hdfsFilePath=new Path(nameNode+filePath);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(hdfs.open(hdfsFilePath)))) {
			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
