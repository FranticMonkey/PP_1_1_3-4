package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.annotations.Entity;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private User user;
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        user = new User(name, lastName, age);

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
