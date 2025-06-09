package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age TINYINT)";
        executeNativeSQL(sql);
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        executeNativeSQL(sql);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getHibernateSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = new User(name, lastName, age);
        session.save(user);

        transaction.commit();
        session.close();
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getHibernateSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getHibernateSessionFactory().openSession();
        Query<User> query = session.createQuery("FROM User", User.class);
        List<User> result = query.list();
        session.close();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        executeNativeSQL(sql);
    }

    private void executeNativeSQL(String sql) {
        Session session = Util.getHibernateSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        transaction.commit();
        session.close();
    }
}