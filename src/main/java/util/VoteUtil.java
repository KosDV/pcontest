package util;

import hibernate.manager.VoteManager;
import hibernate.model.Vote;

import java.util.List;

import org.hibernate.HibernateException;

public class VoteUtil {
    public enum Urn {

	INSTANCE;

	private VoteManager voteMgr = new VoteManager();

	public void add(Vote vote) throws HibernateException {
	    voteMgr.saveNewVote(vote);
	}

	public List<Vote> get() throws HibernateException {
	    return voteMgr.loadAllVotes();
	}

	public void remove() throws HibernateException {
	    voteMgr.deleteAllVotes();
	}
    }
}
