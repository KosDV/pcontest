package hibernate.generic;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import util.HibernateUtil;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class GenericPictureDAO<T, PICTURE_ID extends Serializable> implements
		IGenericPictureDAO<T, PICTURE_ID> {

	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	public void save(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.saveOrUpdate(entity);
	}

	public void merge(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.merge(entity);
	}

	public void update(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.update(entity);
	}

	public void delete(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.delete(entity);
	}

	public List<T> findMany(Query query) {
		List<T> t;
		t = (List<T>) query.list();
		return t;
	}

	public T findOne(Query query) {
		T t;
		t = (T) query.uniqueResult();
		return t;
	}

	public List findAll(Class c) {
		Session hibernateSession = this.getSession();
		List T = null;
		Query query = hibernateSession.createQuery("from " + c.getName());
		T = query.list();
		return T;
	}

	public T findByID(Class c, Integer id) {
		Session hibernateSession = this.getSession();
		T t = null;
		t = (T) hibernateSession.get(c, id);
		return t;
	}
}
