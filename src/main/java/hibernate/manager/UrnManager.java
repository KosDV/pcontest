package hibernate.manager;

import hibernate.model.Urn;
import hibernate.specific.UrnDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import util.HibernateUtil;

public class UrnManager implements IUrnManager {

	private UrnDAO urnDAO = new UrnDAO();

	public Urn loadUrn() {
		Urn urn = null;
		try {
			HibernateUtil.beginTransaction();
			urn = urnDAO.findByUrnId(1);
			HibernateUtil.commitTransaction();
			return urn;
		} catch (HibernateException ex) {
			System.err.println(ex.getMessage());
            throw new HibernateException(ex.getCause());
		}
	}

	public void saveNewUrn(Urn urn) {
		try {
			HibernateUtil.beginTransaction();
			urnDAO.save(urn);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.err.println("ERROR: Saving Urn: " + urn.getName());
			HibernateUtil.rollbackTransaction();
			throw new HibernateException(ex.getCause());
		}

	}

	public void updateUrn(Urn urn) {
		try {
			HibernateUtil.beginTransaction();
			urnDAO.update(urn);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.err.println("ERROR: Updating User: " + urn.getName());
			HibernateUtil.rollbackTransaction();
			throw new HibernateException(ex.getCause());
		}

	}

}
