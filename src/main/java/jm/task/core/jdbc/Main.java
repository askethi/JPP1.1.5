package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        addUser(new User("Пол", "Маккартни", (byte) 81), us);
        addUser(new User("Джон", "Леннон", (byte) 83), us);
        addUser(new User("Джордж", "Харрисон", (byte) 80), us);
        addUser(new User("Ринго", "Старр", (byte) 83), us);

        List<User> users = us.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        us.cleanUsersTable();
        us.dropUsersTable();
    }

    public static void addUser(User user, UserService us) {
        String name = user.getName();
        String lastName = user.getLastName();
        int age = user.getAge();
        us.saveUser(name, lastName, (byte) age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }
}
