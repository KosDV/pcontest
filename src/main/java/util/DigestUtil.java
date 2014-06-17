package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class Digest {
    public static String generateSHA2(String stringToDigest)
	    throws NoSuchAlgorithmException {
	MessageDigest md = MessageDigest.getInstance("SHA-512");
	md.update(stringToDigest.getBytes());
	byte byteData[] = md.digest();

	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < byteData.length; i++) {
	    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
		    .substring(1));
	}
	return new String(Base64.encodeBase64(sb.toString().getBytes()));
    }
}
