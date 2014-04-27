package hibernate.specific;

import model.User;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;

import util.HibernateUtil;

public class UserFunctions {

    public User findByNif(String nif) {

        User user = null;

        try {
            HibernateUtil.getSession().beginTransaction();
            Query query = HibernateUtil.getSession().createQuery(
                    "from User where NIF = '" + nif + "'");
            user = (User) query.uniqueResult();
            HibernateUtil.getSession().getTransaction().commit();
        } catch (NonUniqueResultException ex) {
            System.err.println("Query returned more than one result");
            ex.printStackTrace();
            return null;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }

        return user;
    }
}
