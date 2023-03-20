package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sess = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sess.openSession()) {                            //открыли сессию
            session.beginTransaction();                                        // открыли транзакцию
            String createTableSQL = """
                    create table if not exists users(id serial primary key,
                                                     name varchar(100),
                                                     lastName varchar(100),
                                                     age smallint)""";
            Query query = session.createNativeQuery(createTableSQL);            //cоздали обьект в который поместили SQL запрос
            query.executeUpdate();                                             // обрабатывает  SQL запрос (добавление удалени изменение и тд)
            session.getTransaction().commit();                                //закрываем транзакцию
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sess.openSession()) {
            session.beginTransaction();
            String dropTableSQL = " drop table if exists users ";
            Query query = session.createNativeQuery(dropTableSQL);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sess.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sess.openSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);     // загружаем юзера из таблица найденого по йди
            session.delete(user);                        // удаляем юзера
            session.flush();                             //очищаем таблицу
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sess.openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();         // возвращаем всех юзеров
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sess.openSession()) {
            session.beginTransaction();
            String dropTableSQL = "TRUNCATE TABLE users";
            Query query = session.createNativeQuery(dropTableSQL);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

}

