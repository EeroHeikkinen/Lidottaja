package fi.museo2015.lidottaja.web;

public class TagUtils {
	public static String extractLastLine(String input) {
		int lastIndexOf = input.lastIndexOf('\n');
		if (lastIndexOf == -1 || lastIndexOf + 1 > input.length())
			return "";
		return input.substring(lastIndexOf + 1);
	}

	public static String withoutLastLine(String input) {
		int lastIndexOf = input.lastIndexOf('\n');
		if (lastIndexOf == -1 || lastIndexOf + 1 > input.length())
			return input;
		return input.substring(0, lastIndexOf + 1);
	}
}
