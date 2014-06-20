package util;

import hibernate.manager.ContestManager;
import hibernate.model.Contest;

import org.hibernate.HibernateException;

public class ContestUtil {
    public enum Singleton {
	INSTANCE;

	private ContestManager contestMgr = new ContestManager();

	public Contest get() throws HibernateException {
	    return contestMgr.loadContest();
	}

	public void updateStatus(Integer status, Contest contest)
		throws HibernateException {
	    contest.setContestStatus(status);
	    contestMgr.updateContest(contest);
	}
    }
}
