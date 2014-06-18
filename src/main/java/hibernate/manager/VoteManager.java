package hibernate.manager;

import hibernate.model.Vote;
import hibernate.specific.VoteDAO;

import java.util.List;

import org.hibernate.HibernateException;

import util.HibernateUtil;

public class VoteManager implements IVoteManager {
    private VoteDAO voteDAO = new VoteDAO();

    public List<Vote> loadAllVotes() {
	try {
	    HibernateUtil.beginTransaction();
	    List<Vote> allVotes = voteDAO.findAll(Vote.class);
	    HibernateUtil.commitTransaction();
	    return allVotes;
	} catch (HibernateException ex) {
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}

    }

    public Boolean saveNewVote(Vote vote) {
	try {
	    HibernateUtil.beginTransaction();

	    voteDAO.save(vote);
	    HibernateUtil.commitTransaction();
	    return true;
	} catch (HibernateException ex) {
	    HibernateUtil.rollbackTransaction();
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}
    }

    public void deleteAllVotes() {
	try {
	    HibernateUtil.beginTransaction();
	    List<Vote> votes = loadAllVotes();
	    for (int i = 0; i < votes.size(); i++) {
		voteDAO.delete(votes.get(i));
	    }
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    HibernateUtil.rollbackTransaction();
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}
    }
}
