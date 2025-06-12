package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(255), " +
            "lastName VARCHAR(255), " +
            "age TINYINT)";

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String TRUNCATE_TABLE_SQL = "TRUNCATE TABLE users";

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error creating users table", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(DROP_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error dropping users table", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving user", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error removing user by id", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all users", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(TRUNCATE_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error cleaning users table", e);
        }
    }
}