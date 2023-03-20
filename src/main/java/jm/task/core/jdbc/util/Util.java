package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "tolstogo1014";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory()   //метод который заполняет sessionFactory

    {
        if (sessionFactory == null) ;           // проверяем пусто или нет
        {
            try {
                Configuration config = new Configuration();         // создаём конфигурацию соеденения с бд
                Properties prop = new Properties();                // <-- //помещаем в обьект настройки для соедениния
                prop.put(Environment.DRIVER, "org.postgresql.Driver");                    ////
                prop.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");     //
                prop.put(Environment.USER, "postgres");                                     // настройки для соеденения с бд
                prop.put(Environment.PASS, "tolstogo1014");                                 //
                prop.put(Environment.SHOW_SQL, "true");                                     //
                prop.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");            ////
                config.addProperties(prop);                              // сложили сюда все настройки  соеденения
                config.addAnnotatedClass(User.class);                    // указали класс для создания сущности
                sessionFactory = config.buildSessionFactory();           //сложили всё в sessionFactory
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sessionFactory;
        }

    }
}


// реализуйте настройку соеденения с БД

