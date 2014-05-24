package hibernate.generic;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

public interface IGenericUrnDAO<T, URN_ID> extends Serializable {
	public void save(T entity);

	public void merge(T entity);

	public void delete(T entity);

	public List<T> findMany(Query query);

	public T findOne(Query query);

	public List findAll(Class c);

	public T findByID(Class c, Integer id);
}
