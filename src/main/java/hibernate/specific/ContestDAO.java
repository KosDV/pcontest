package hibernate.specific;

import hibernate.generic.GenericContestDAO;
import hibernate.model.Contest;

import org.hibernate.Query;

import util.HibernateUtil;

@SuppressWarnings("serial")
public class ContestDAO extends GenericContestDAO<Contest, Integer> implements IContestDAO {
	public Contest findByUrnId(Integer id) {
		Contest contest = null;
		String hql = "from Urn urn where urn.id = :id";
		Query query = HibernateUtil.getSession().createQuery(hql)
				.setParameter("id", id);
		contest = findOne(query);
		return contest;
	}
}
