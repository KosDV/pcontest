package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rest.model.PhotoDTO;

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

	private ConcurrentHashMap<Integer, List<PhotoDTO>> photosToVoteByUser = new ConcurrentHashMap<Integer, List<PhotoDTO>>();

	public void add(Integer userId, List<PhotoDTO> photosToVote) {
	    photosToVoteByUser.put(userId, photosToVote);
	}

	public List<PhotoDTO> get(Integer userId) {
	    return photosToVoteByUser.get(userId);
	}

	public void remove(Integer userId) {
	    photosToVoteByUser.remove(userId);
	}

    }

}
