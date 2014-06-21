package hibernate.manager;

import hibernate.model.Results;
import hibernate.specific.ResultsDAO;

import org.hibernate.HibernateException;

import util.HibernateUtil;

public class ResultsManager {
    private ResultsDAO resultsDAO = new ResultsDAO();

    public void saveNewResult(Results result) {
	try {
	    HibernateUtil.beginTransaction();
	    resultsDAO.save(result);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    HibernateUtil.rollbackTransaction();
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}
    }
}
