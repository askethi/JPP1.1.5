package jm.task.core.jdbc.util;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/jmpreproject";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load class.");
            e.printStackTrace();
        }

        connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
        //Do with resources(?)
        /*
        try (Connection con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD)) {
            System.out.println("Connection OK!");
            return con;
        } catch (SQLException e) {
            System.out.println("Connection ERROR!");
            e.printStackTrace();
            return null;
        }
        */

     /*   try {
            connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
            System.out.println("Connection OK!");
        } catch (SQLException th) {
            System.out.println("Connection ERROR!");
            th.printStackTrace();
        }*/
        return connection;
    }


}
