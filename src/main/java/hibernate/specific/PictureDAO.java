package hibernate.specific;

import hibernate.generic.GenericPictureDAO;
import hibernate.model.Picture;

import org.hibernate.Query;

import util.HibernateUtil;

@SuppressWarnings("serial")
public class PictureDAO extends GenericPictureDAO<Picture, Integer> implements
		IPictureDAO {
	public Picture findByPictureById(Integer id) {
		Picture pic = null;
		String hql = "from Picture pic where pic.id = :id";
		Query query = HibernateUtil.getSession().createQuery(hql)
				.setParameter("id", id);
		pic = findOne(query);
		return pic;
	}
}
