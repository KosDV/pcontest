package servers.conf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ServerConfigurator {
	private static final String propertiesFilePath = "conf";
	private static final String propertiesFileName = "server.properties";
	private static final String baseApiUrl;
	private static final String photoPath;
	private static final String photoUrlPath;
	private static final String baseWebUrl;
	private static final String webPath;
	private static final String webUrlPath;

	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(propertiesFilePath + "/"
					+ propertiesFileName));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		baseApiUrl = prop.getProperty("baseApiUrl");
		photoPath = prop.getProperty("photoPath");
		photoUrlPath = prop.getProperty("photoUrlPath");
		baseWebUrl = prop.getProperty("baseWebUrl");
		webPath = prop.getProperty("webPath");
		webUrlPath = prop.getProperty("webUrlPath");

		printConfiguration();

	}

	public static void configure() {
		System.out.println("Balancer configured");
	}

	private static void printConfiguration() {
		System.out
				.println("############## Print Server configuration ###########");
		System.out.println("baseApiUrl " + baseApiUrl);
		System.out.println("photoPath " + photoPath);
		System.out.println("photoUrlPath " + photoUrlPath);
		System.out.println("baseWebUrl " + baseWebUrl);
		System.out.println("webPath " + webPath);
		System.out.println("webUrlPath " + webUrlPath);
		System.out.println("#############################################");
	}

	public static String getPropertiesfilepath() {
		return propertiesFilePath;
	}

	public static String getPropertiesfilename() {
		return propertiesFileName;
	}

	public static String getBaseapiurl() {
		return baseApiUrl;
	}

	public static String getPhotopath() {
		return photoPath;
	}

	public static String getPhotourlpath() {
		return photoUrlPath;
	}

	public static String getBaseweburl() {
		return baseWebUrl;
	}

	public static String getWebpath() {
		return webPath;
	}

	public static String getWeburlpath() {
		return webUrlPath;
	}

}
