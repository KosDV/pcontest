package hibernate.manager;

import hibernate.model.Contest;
import hibernate.specific.ContestDAO;

import org.hibernate.HibernateException;

import util.HibernateUtil;

public class ContestManager implements IContestManager {

    private ContestDAO contestDAO = new ContestDAO();

    public Contest loadUrn() {
	try {
	    HibernateUtil.beginTransaction();
	    Contest contest = contestDAO.findByUrnId(1);
	    HibernateUtil.commitTransaction();
	    return contest;
	} catch (HibernateException ex) {
	    
	    throw new HibernateException(ex.getCause());
	}
    }

    public void saveNewUrn(Contest contest) {
	try {
	    HibernateUtil.beginTransaction();
	    contestDAO.save(contest);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    HibernateUtil.rollbackTransaction();
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}

    }

    public void updateUrn(Contest contest) {
	try {
	    HibernateUtil.beginTransaction();
	    contestDAO.update(contest);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    HibernateUtil.rollbackTransaction();
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}

    }

}
