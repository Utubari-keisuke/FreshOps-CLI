package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {
	private static final Scanner scanner = new Scanner(System.in);
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static String readString(String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (!input.isEmpty()) {
				return input;
			}
			System.out.println("入力が空です。もう一度入力してください。");
		}
	}

	public static int readInt(String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("半角数値を入力してください。");
			}
		}
	}

	public static int readPositiveInt(String prompt) {
		while (true) {
			int value = readInt(prompt);
			if (value >= 0) {
				return value;
			}
			System.out.println("0以上の数値を入力してください。");
		}
	}

	public static LocalDate readDate(String prompt) {
		while (true) {
			System.out.print(prompt + " (例: 2026-08-30): ");
			String input = scanner.nextLine().trim();
			try {
				return LocalDate.parse(input, DATE_FORMATTER);
			} catch (DateTimeParseException e) {
				System.out.println("※日付の形式が正しくありません (yyyy-MM-dd で入力してください)。");
			}
		}
	}
}
