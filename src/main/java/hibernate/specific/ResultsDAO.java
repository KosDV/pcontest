package hibernate.specific;

import hibernate.generic.GenericResultsDAO;
import hibernate.model.Results;

import org.hibernate.Query;

import util.HibernateUtil;

public class ResultsDAO extends GenericResultsDAO<Results, Integer> implements
	IResultsDAO {

    private static final long serialVersionUID = -7479179625346039103L;

    public Results findByResultId(Integer id) {
	Results result = null;
	String hql = "from Results result where result.photoId = :photoId";
	Query query = HibernateUtil.getSession().createQuery(hql)
		.setParameter("photoId", id);
	result = findOne(query);
	return result;
    }

}
