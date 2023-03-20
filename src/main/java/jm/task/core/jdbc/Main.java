package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte)10);
        userService.saveUser("Petr", "Petrunov", (byte)15);
        userService.saveUser("Sergey", "Sergunov", (byte)20);
        userService.saveUser("Oleg", "Olegovich", (byte)25);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        // реализуйте алгоритм здесь
    }
}
