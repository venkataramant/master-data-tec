package lrn.tvr.course.spark.util;

import java.util.Scanner;

public class LocalUtil {
	public static String getFileName(String[] args) {
		String fileName;
		if (args.length > 0) {
			fileName = args[0];
		} else {
			try (var scanner = new Scanner(System.in)) {
				fileName = scanner.nextLine();
			}
		}
		return fileName;
	}

	public static void waitForUserInput() {
		try (var scanner = new Scanner(System.in)) {
			System.out.println("Enter any Key to Exit");
			scanner.nextLine();
		}
		System.out.println("Exited");

	}
}
