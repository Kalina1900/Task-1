package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.PASSWORD;
import static jm.task.core.jdbc.util.Util.USERNAME;
import static org.hibernate.cfg.AvailableSettings.URL;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection con = Util.getConnection();


    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() {
        try (Statement st = con.createStatement()) {
            String createTableSQL = """
                    create table if not exists users(id serial primary key,
                                                     name varchar(100),
                                                     lastName varchar(100),
                                                     age smallint)""";
            st.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void dropUsersTable() {
        try (Statement st = con.createStatement()) {
            String dropTableSQL = " drop table if exists users ";
            st.executeUpdate(dropTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pre = con.prepareStatement("insert into users(name, lastName, age) values (?, ?, ?)")) {
            pre.setString(1, name);
            pre.setString(2, lastName);
            pre.setByte(3, age);
            pre.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement pre = con.prepareStatement("DELETE FROM users WHERE ID = ?")) {
            pre.setLong(1, id);
            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet rs = con.createStatement().executeQuery("select * from users")) {
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("lastName"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement pre = con.prepareStatement("TRUNCATE TABLE users")) {

            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
