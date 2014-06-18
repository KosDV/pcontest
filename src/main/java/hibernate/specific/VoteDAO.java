package hibernate.specific;

import hibernate.generic.GenericVoteDAO;
import hibernate.model.Vote;

import org.hibernate.Query;

import util.HibernateUtil;

public class VoteDAO extends GenericVoteDAO<Vote, Integer> implements IVoteDAO {

    private static final long serialVersionUID = -8276089182682364144L;

    public Vote findByVoteId(Integer voteId) {

	String hql = "from Vote vote where vote.id = :id";
	Query query = HibernateUtil.getSession().createQuery(hql)
		.setParameter("id", voteId);
	return findOne(query);

    }
}
