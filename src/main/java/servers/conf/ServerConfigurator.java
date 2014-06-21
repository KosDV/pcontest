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
    private static final String keystoreServer;
    private static final String keystorePassword;
    private static final String basePhotoUrl;
    private static final String bitlength;
    private static final String p;
    private static final String q;
    private static final String lambda;
    private static final String n;
    private static final String nsquare;
    private static final String g;

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
	basePhotoUrl = prop.getProperty("basePhotoUrl");
	baseWebUrl = prop.getProperty("baseWebUrl");
	webPath = prop.getProperty("webPath");
	webUrlPath = prop.getProperty("webUrlPath");
	keystoreServer = prop.getProperty("keystoreServer");
	keystorePassword = prop.getProperty("keystorePassword");
	bitlength = prop.getProperty("bitlength");
	p = prop.getProperty("p");
	q = prop.getProperty("q");
	lambda = prop.getProperty("lambda");
	n = prop.getProperty("n");
	nsquare = prop.getProperty("nsquare");
	g = prop.getProperty("g");
	printConfiguration();
    }

    public static void configure() {
	System.out.println("Server configured");
    }

    private static void printConfiguration() {
	System.out
		.println("############## Print Server configuration ###########");
	System.out.println("baseApiUrl " + baseApiUrl);
	System.out.println("photoPath " + photoPath);
	System.out.println("photoUrlPath " + photoUrlPath);
	System.out.println("basePhotoUrl" + basePhotoUrl);
	System.out.println("baseWebUrl " + baseWebUrl);
	System.out.println("webPath " + webPath);
	System.out.println("webUrlPath " + webUrlPath);
	System.out.println("#############################################");
	System.out.println("");
	System.out.println("############## Paillier configuration ###########");
	System.out.println("bitlength " + bitlength);
	System.out.println("p " + p);
	System.out.println("q " + q);
	System.out.println("lambda " + lambda);
	System.out.println("n " + n);
	System.out.println("nsquare " + nsquare);
	System.out.println("g " + g);
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

    public static String getKeystoreServer() {
	return keystoreServer;
    }

    public static String getKeystorepassword() {
	return keystorePassword;
    }

    public static String getBasephotourl() {
	return basePhotoUrl;
    }

    public static String getKeystoreserver() {
	return keystoreServer;
    }

    public static String getBitlength() {
	return bitlength;
    }

    public static String getP() {
	return p;
    }

    public static String getQ() {
	return q;
    }

    public static String getLambda() {
	return lambda;
    }

    public static String getN() {
	return n;
    }

    public static String getNsquare() {
	return nsquare;
    }

    public static String getG() {
	return g;
    }
}
