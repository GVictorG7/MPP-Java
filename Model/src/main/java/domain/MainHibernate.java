package domain;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MainHibernate {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().
                    configure().
                    addAnnotatedClass(Operator.class).
                    buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        MainHibernate mainHibernate = new MainHibernate();

        Integer p1 = mainHibernate.addOperator(100, "op100", "pass100");

        /* List down all the operators */
        System.out.println("========add");
        mainHibernate.listOperators();
        System.out.println("-------------------update");

        /* Update operator's password */
        mainHibernate.updateOperator(p1, "pass");
        mainHibernate.listOperators();
        System.out.println("------------------------delete");

        /* Delete an operator from the database */
        mainHibernate.deleteOperator(p1);

        /* List down new list of the operators */
        mainHibernate.listOperators();
    }

    /* Method to CREATE an operator in the database */
    private Integer addOperator(int id, String name, String pass) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer p = null;

        try {
            tx = session.beginTransaction();
            Operator operator = new Operator();
            operator.setId(id);
            operator.setName(name);
            operator.setPass(pass);

            p = (Integer) session.save(operator);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return p;
    }

    /* Method to  READ all the operators */
    private void listOperators() {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            List opers = session.createQuery("FROM Operator").list();
            for (Object oper : opers) {
                Operator p = (Operator) oper;
                System.out.print("  id: " + p.getId());
                System.out.print("   nume: " + p.getName());
                System.out.println("  pass: " + p.getPass());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    private void updateOperator(Integer id, String pass) {
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            Operator p = session.get(Operator.class, id);
            p.setPass(pass);
            session.update(p);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    /* Method to DELETE an operator from the records */
    private void deleteOperator(Integer id) {
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            Operator p = session.get(Operator.class, id);
            session.delete(p);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
