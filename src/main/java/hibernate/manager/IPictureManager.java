package hibernate.manager;

import hibernate.model.Photo;
import hibernate.model.User;

import java.util.List;

public interface IPictureManager {
	public List<Photo> loadAllPictures();

	public Boolean saveNewPicture(Photo pic, User user);

	public Photo findPictureById(Integer id);
}
