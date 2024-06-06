package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

  //  public UserDaoJDBCImpl() {

  //  }

    public void createUsersTable() {
        String command = "CREATE TABLE IF NOT EXISTS users(" +
                "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
                "LASTNAME VARCHAR(100), AGE INT, PRIMARY KEY (ID) )";

        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate(command);
            System.out.println("Table created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String command = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement()) {
            stat.execute(command);
            System.out.println("Table dropped");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String command = "INSERT INTO users (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement pstat = connection.prepareStatement(command)) {
             pstat.setString(1, name);
             pstat.setString(2, lastName);
             pstat.setByte(3, age);
             pstat.executeUpdate();
             System.out.println("User added");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String command = "DELETE users WHERE ID=?";

        try (Connection connection = Util.getConnection();
             PreparedStatement pstat = connection.prepareStatement(command)) {
            pstat.setLong(1, id);
            pstat.executeUpdate();
            System.out.println("User deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String command = "SELECT ID, NAME, LASTNAME, AGE FROM users";

        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement()) {
            ResultSet rset = stat.executeQuery(command);
            while (rset.next()) {
                User user = new User();
                user.setId(rset.getLong("ID"));
                user.setName(rset.getString("NAME"));
                user.setLastName(rset.getString("LASTNAME"));
                user.setAge(rset.getByte("AGE"));
                users.add(user);
            }
            System.out.println("Users collected");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String command = "DELETE FROM users";

        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate(command);
            System.out.println("Table cleaned");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
