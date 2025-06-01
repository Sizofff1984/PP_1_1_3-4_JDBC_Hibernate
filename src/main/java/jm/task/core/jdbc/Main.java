package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();


        userService.createUsersTable();

        // Добавление 4 User(ов) в таблицу
        userService.saveUser("John", "Doe", (byte) 25);
        userService.saveUser("Jane", "Smith", (byte) 30);
        userService.saveUser("Mike", "Johnson", (byte) 35);
        userService.saveUser("Sarah", "Williams", (byte) 40);


        System.out.println(userService.getAllUsers());


        userService.cleanUsersTable();


        userService.dropUsersTable();
    }
}