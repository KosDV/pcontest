package hibernate.specific;

import hibernate.generic.GenericPictureDAO;
import hibernate.model.Photo;

import org.hibernate.Query;

import util.HibernateUtil;

@SuppressWarnings("serial")
public class PictureDAO extends GenericPictureDAO<Photo, Integer> implements
		IPictureDAO {
	public Photo findByPictureById(Integer id) {
		Photo pic = null;
		String hql = "from Photo pic where pic.id = :id";
		Query query = HibernateUtil.getSession().createQuery(hql)
				.setParameter("id", id);
		pic = findOne(query);
		return pic;
	}
}
