package hibernate.manager;

import hibernate.model.Urn;
import hibernate.model.Vote;
import hibernate.specific.UrnDAO;
import hibernate.specific.VoteDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import util.HibernateUtil;

public class VoteManager implements IVoteManager {
	private VoteDAO voteDAO = new VoteDAO();
	private UrnDAO urnDAO = new UrnDAO();

	public List<Vote> loadAllVotes() {
		List<Vote> allVotes = new ArrayList<Vote>();
		try {
			HibernateUtil.beginTransaction();
			allVotes = voteDAO.findAll(Vote.class);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.err.println("Error while loading all hotspots.");
			// return null;
		}
		return allVotes;
	}

	public Boolean saveNewVote(Vote vote, Urn urn) {
		try {
			HibernateUtil.beginTransaction();
			Set<Vote> votes;
			votes = urn.getListVotes();
			if (votes == null) {
				votes = new HashSet<Vote>(0);
			}
			votes.add(vote);
			urn.setListVotes(votes);
			vote.setUrn(urn);
			voteDAO.save(vote);
			urnDAO.save(urn);
			HibernateUtil.commitTransaction();
			return true;
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			System.err.println("Error while saving vote into the urn");
			return false;
		}
	}

}
