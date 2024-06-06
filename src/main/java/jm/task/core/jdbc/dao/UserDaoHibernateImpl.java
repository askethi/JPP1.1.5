package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    /*
    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users(" +
                    "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
                    "LASTNAME VARCHAR(100), AGE INT, PRIMARY KEY (ID) )";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("User added");
        } catch (Exception e) {
            if (transaction != null) {
                //   transaction.rollback();
            }
        }
    }

     */

    @Override
    public void createUsersTable() {
        String command = "CREATE TABLE IF NOT EXISTS users(" +
                "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
                "LASTNAME VARCHAR(100), AGE INT, PRIMARY KEY (ID) )";

        try (Connection connection = Util.getConnection()) {
            Statement stat = connection.createStatement();
            stat.executeUpdate(command);
            System.out.println("Table created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        String command = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection()) {
            Statement stat = connection.createStatement();
            stat.execute(command);
            System.out.println("Table dropped");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory()) {
            transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            //transaction.commit();
            session.close();
            System.out.println("User added");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.flush();
            transaction.commit();
            System.out.println("User deleted");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory()) {

            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = cb.createQuery(User.class);
            criteria.from(User.class);
            users = session.createQuery(criteria).getResultList();
            //users = session.createCriteria(User.class).list();
            transaction.commit();
            System.out.println("Users collected");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage() + "Something wrong here (getAll)");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory()) {
            transaction = session.beginTransaction();
            String command = String.format("DELETE FROM %s", "users");
           // Query query = session.createQuery(command);
            session.createSQLQuery(command).executeUpdate();
            transaction.commit();
            System.out.println("Table cleaned");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }
}
