package hibernate.manager;

import hibernate.model.User;

import java.util.List;

public interface IUserManager {
	public User findByUserNif(String userNif);

	public List<User> loadAllUsers();

	public void saveNewUser(User user);

}
