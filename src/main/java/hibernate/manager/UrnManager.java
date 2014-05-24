package hibernate.manager;

import hibernate.model.Urn;
import hibernate.specific.UrnDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import util.HibernateUtil;

public class UrnManager implements IUrnManager {

	private UrnDAO urnDAO = new UrnDAO();

	public List<Urn> loadAllUrns() {
		List<Urn> allUrns = new ArrayList<Urn>();
		try {
			HibernateUtil.beginTransaction();
			allUrns = urnDAO.findAll(Urn.class);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return allUrns;
	}

	public Urn findUrnById(Integer id) {
		Urn urn = null;
		try {
			HibernateUtil.beginTransaction();
			urn = urnDAO.findByUrnId(id);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			return null;
		}
		return urn;
	}

	public void saveNewUrn(Urn urn) {
		try {
			HibernateUtil.beginTransaction();
			urnDAO.save(urn);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.err.println("ERROR: Saving Urn: " + urn.getName());
			HibernateUtil.rollbackTransaction();
		}

	}

	public void updateUrn(Urn urn) {
		try {
			HibernateUtil.beginTransaction();
			urnDAO.update(urn);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			System.err.println("ERROR: Updating User: " + urn.getName());
			HibernateUtil.rollbackTransaction();
		}

	}

}
