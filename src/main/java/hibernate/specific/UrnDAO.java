package hibernate.specific;

import hibernate.generic.GenericUrnDAO;
import hibernate.model.Urn;

import org.hibernate.Query;

import util.HibernateUtil;

@SuppressWarnings("serial")
public class UrnDAO extends GenericUrnDAO<Urn, Integer> implements IUrnDAO {
	public Urn findByUrnId(Integer id) {
		Urn urn = null;
		String hql = "from Urn urn where urn.id = :id";
		Query query = HibernateUtil.getSession().createQuery(hql)
				.setParameter("id", id);
		urn = findOne(query);
		return urn;
	}
}
