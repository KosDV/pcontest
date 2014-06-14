package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PhotoUtil {
    public static void writeToFile(InputStream photoS, File location) throws IOException {
	OutputStream out = new FileOutputStream(location, false);
	int read = 0;
	byte[] bytes = new byte[1024];
	while ((read = photoS.read(bytes)) != -1) {
	    out.write(bytes, 0, read);
	}
	out.flush();
	out.close();
    }
}
