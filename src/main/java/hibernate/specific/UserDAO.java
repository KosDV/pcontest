package hibernate.specific;

import hibernate.generic.GenericUserDAO;
import hibernate.model.User;

import org.hibernate.Query;

import util.HibernateUtil;

@SuppressWarnings("serial")
public class UserDAO extends GenericUserDAO<User, Integer> implements IUserDAO {
	public User findByUserNif(String nif) {
		User user = null;
		String hql = "from User usr where usr.nif = :nif";
		Query query = HibernateUtil.getSession().createQuery(hql)
				.setParameter("nif", nif);
		user = findOne(query);
		return user;
	}
}
