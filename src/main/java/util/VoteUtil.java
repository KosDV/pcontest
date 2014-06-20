package util;

import hibernate.manager.VoteManager;
import hibernate.model.Vote;

import java.util.List;

import org.hibernate.HibernateException;

public class VoteUtil {
    public enum Urn {

	INSTANCE;

	private VoteManager voteMgr = new VoteManager();

	public Integer add(Vote vote) throws HibernateException {
	    voteMgr.saveNewVote(vote);
	    return vote.getId();
	}

	public List<Vote> getAll() throws HibernateException {
	    return voteMgr.loadAllVotes();
	}

	public Vote get(Integer voteId) throws HibernateException {
	    return voteMgr.findById(voteId);
	}

	public void removeAll(List<Vote> voteList) throws HibernateException {
	    voteMgr.deleteAllVotes(voteList);
	}

	public void remove(Vote vote) throws HibernateException {
	    voteMgr.deleteVote(vote);
	}
    }
}
