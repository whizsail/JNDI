package org.su18.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StringUtil {

	public static String getClassName(String url) {
		return url.substring(0, url.length() - 1);
	}

	public static String getVersion(String url) {
		return url.substring(url.length() - 1);
	}

	public static String getCurrentPropertiesValue(String key) {
		String     value = "";
		Properties p     = new Properties();
		try {
			InputStream is = new FileInputStream("config.properties");
			p.load(is);
			value = p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

}
