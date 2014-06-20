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

    public Integer individualVoteLength(int numPhotos) {
	if (numPhotos < 1)
	    return -1;
	return (int) (Math.log10(numPhotos) + 1);
    }

    public Integer totalVoteLength(int numPhotos, int individualLength) {
	Integer totalLength = numPhotos * individualLength;
	System.out.println("Num Photos: " + numPhotos + " Indidual Length: "
		+ individualLength + " Total Length");
	return totalLength;
    }

}
