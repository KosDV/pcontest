package hibernate.manager;

import hibernate.model.Picture;
import hibernate.model.User;

import java.util.List;

public interface IPictureManager {
	public List<Picture> loadAllPictures();

	public Boolean saveNewPicture(Picture pic, User user);

	public Picture findPictureById(Integer id);
}
