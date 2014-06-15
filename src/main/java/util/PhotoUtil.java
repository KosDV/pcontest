package util;

import hibernate.model.Photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;

public class PhotoUtil {
    public static void writeToFile(InputStream photoS, File location)
	    throws IOException {
	OutputStream out = new FileOutputStream(location, false);
	int read = 0;
	byte[] bytes = new byte[1024];
	while ((read = photoS.read(bytes)) != -1) {
	    out.write(bytes, 0, read);
	}
	out.flush();
	out.close();
    }

    public enum Singleton {

	INSTANCE;

	private HashMap<Integer, HashSet<Photo>> photosToVoteByUser = new HashMap<Integer, HashSet<Photo>>();

	public void add(Integer userId, HashSet<Photo> photosToVote) {
	    photosToVoteByUser.put(userId, photosToVote);
	}

	public HashSet<Photo> get(Integer userId) {
	    return photosToVoteByUser.get(userId);
	}

	public void remove(Integer userId) {
	    photosToVoteByUser.remove(userId);
	}

    }

}
