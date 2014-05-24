package hibernate.specific;

import hibernate.generic.IGenericUserDAO;
import hibernate.model.User;

public interface IUserDAO extends IGenericUserDAO<User, Integer> {

}
