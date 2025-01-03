package lrn.tvr.course.spark.basic;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.google.common.collect.Maps.EntryTransformer;

public class SimpleWordCount {
	private static final Pattern SPACE = Pattern.compile(" ");

	private static void wordCount1(String fileURI) {

		try {
			Path filePath = Path.of(new URI(fileURI));

			List<String> lines = Files.readAllLines(filePath);
			EntryTransformer<String, List<String>, Integer> convertList2Int = (k, v) -> v.size();
			Map<String, List<String>> words = lines.stream()
					.map(x -> x.split(" "))
					.flatMap(x -> Arrays.stream(x))
					.filter(x -> x != "")
					.collect(Collectors.groupingBy(w -> w));
			Map<String, Integer> word_count = Maps.transformEntries(words, convertList2Int);
			word_count.entrySet().forEach(x -> System.out.println(x.getKey() + "::" + x.getValue()));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void wordCount2(String fileURI) {

		try {
			Path filePath = Path.of(new URI(fileURI));

			List<String> lines = Files.readAllLines(filePath);
			lines.stream()
					.map(x -> SPACE.split(x))
					.flatMap(x -> Arrays.stream(x))
					.filter(x -> x != "")
					.forEach(System.out::println);
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String fileURI = null;
		if (args.length == 0) {
			Scanner sc = new Scanner(System.in);
			System.out.println("File enter Path of file with protocol");
			fileURI = sc.nextLine();
		} else {
			fileURI = args[0];
		}
		wordCount1(fileURI);
	}

}
