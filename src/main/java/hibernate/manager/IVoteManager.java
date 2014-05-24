package hibernate.manager;

import hibernate.model.Urn;
import hibernate.model.Vote;

import java.util.List;

public interface IVoteManager {
	public List<Vote> loadAllVotes();

	public Boolean saveNewVote(Vote vote, Urn urn);
}
