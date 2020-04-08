package org.su18.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger {

	public static final String ANSI_RESET = "\u001B[0m";

	public static final String ANSI_PURPLE = "\u001B[35m";

	public static final String ANSI_RED = "\u001B[31m";

	public static final String ANSI_BLUE = "\u001B[34m";


	public static void print(String str) {
		System.out.println(str);
	}

	public static void info(String infoString) {
		System.out.println(printWithColor(infoString, ANSI_BLUE));
	}

	public static void warning(String warningString) {
		System.out.println(printWithColor(warningString, ANSI_PURPLE));
	}

	public static void error(String errorString) {
		System.out.println(printWithColor(errorString, ANSI_RED));
	}

	private static String printWithColor(String str, String color) {
		if (isLinux()) {
			return color + "[" + getLocalTime() + "] " + str + ANSI_RESET;
		}
		return str;
	}

	private static Boolean isLinux() {
		return !System.getProperty("os.name").toLowerCase().startsWith("win");
	}

	private static String getLocalTime() {
		Date       d   = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}

}
