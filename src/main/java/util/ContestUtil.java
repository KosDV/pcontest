package util;

import hibernate.manager.ContestManager;
import hibernate.manager.ResultsManager;
import hibernate.model.Contest;
import hibernate.model.Results;

import java.util.List;

import org.hibernate.HibernateException;

public class ContestUtil {
    public enum Singleton {
	INSTANCE;

	private ContestManager contestMgr = new ContestManager();
	private ResultsManager resultsMgr = new ResultsManager();

	public Contest get() throws HibernateException {
	    return contestMgr.loadContest();
	}

	public void updateContestStatus(Integer status, Contest contest)
		throws HibernateException {
	    contest.setContestStatus(status);
	    contestMgr.updateContest(contest);
	}

	public void addResult(Results result) throws HibernateException {
	    resultsMgr.saveNewResult(result);
	}

	public void closeContest(Integer census, Integer numPhotos,
		Integer numVotes, List<Results> results, Contest contest)
		throws HibernateException {
	    contest.setNumCensus(census);
	    contest.setNumPhotos(numPhotos);
	    contest.setNumVotes(numVotes);
	    contest.setResults(results);
	    contest.setContestStatus(603);
	    contestMgr.updateContest(contest);
	}

    }
}
