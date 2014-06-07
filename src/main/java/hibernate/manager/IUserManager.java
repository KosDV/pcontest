package hibernate.manager;

import hibernate.model.User;

import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface IUserManager {
	public User findByUserNif(String userNif);

	public List<User> loadAllUsers();

	public void saveNewUser(User user);

}
