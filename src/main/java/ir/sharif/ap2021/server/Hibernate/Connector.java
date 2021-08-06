package ir.sharif.ap2021.server.Hibernate;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Notification;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Util.Loop;
import ir.sharif.ap2021.shared.Util.SaveAble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Connector {
    private final static Object staticLock = new Object();
    private Throwable lastThrowable;


    private final SessionFactory sessionFactory;
    private final Set<SaveAble> save, delete;
    private final Object lock;
    private final Loop worker;

    private static final Logger logger = LogManager.getLogger(Connector.class);


    public Connector() throws DatabaseDisconnectException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("gatheringDB");
        EntityManager manager = entityManagerFactory.createEntityManager();
        Session mySession = manager.unwrap(org.hibernate.Session.class);
        sessionFactory = mySession.getSessionFactory();
        save = new HashSet<>();
        delete = new HashSet<>();
        lock = new Object();
        worker = new Loop(10, this::persist);
        worker.start();

    }


    private void ensureOpen() throws DatabaseDisconnectException {
        if (lastThrowable != null)
            throw new DatabaseDisconnectException(lastThrowable);
    }

    private void persist() {
        Set<SaveAble> tempDelete;
        Set<SaveAble> tempSave;
        synchronized (lock) {
            tempSave = new HashSet<>(save);
            this.save.clear();
            tempDelete = new HashSet<>(delete);
            this.delete.clear();
        }
        if (tempSave.size() > 0 || tempDelete.size() > 0)
            synchronized (staticLock) {
                Session session = sessionFactory.openSession();
                try {
                    session.beginTransaction();
                } catch (Throwable e) {
                    lastThrowable = e;
                    e.printStackTrace();
                    logger.error("An error start transaction in Database: " + e.getMessage());
                    return;
                }
                for (SaveAble saveAble : tempDelete) {
                    try {
                        session.delete(saveAble);
                    } catch (Throwable e) {
                        lastThrowable = e;
                        e.printStackTrace();
                        logger.error("An error with Delete in Database: " + e.getMessage());
                        System.err.println("instance not deleted: " + saveAble);
                        return;
                    }
                }
                for (SaveAble saveAble : tempSave) {
                    try {
                        session.saveOrUpdate(saveAble);
                    } catch (Throwable e) {
                        lastThrowable = e;
                        e.printStackTrace();
                        System.err.println("instance not saved :" + saveAble);
                        logger.error("An error with Save or Update in Database: " + e.getMessage());
                        return;
                    }
                }
                try {
                    session.getTransaction().commit();
                } catch (Throwable e) {
                    lastThrowable = e;
                    e.printStackTrace();
                    System.err.println("instances not saved :" + tempSave);
                    System.err.println("instances not deleted :" + tempDelete);
                    logger.error("An error with commit in Database: " + e.getMessage());
                }
                tempSave.clear();
                tempDelete.clear();
                session.close();
            }
    }

    public void close() {
        worker.stop();
        sessionFactory.close();
    }

    public void save(SaveAble saveAble) throws DatabaseDisconnectException {
        if (saveAble != null)
            synchronized (lock) {
                ensureOpen();
                save.add(saveAble);
            }
    }

    public void delete(SaveAble saveAble) throws DatabaseDisconnectException {
        if (saveAble != null)
            synchronized (lock) {
                ensureOpen();
                delete.add(saveAble);
            }
    }

    public <E extends Serializable> E fetch(Class<E> entity, Serializable id) throws DatabaseDisconnectException {
        synchronized (staticLock) {
            ensureOpen();
            Session session = sessionFactory.openSession();
            E result;
            try {
                result = session.get(entity, id);
            } catch (Throwable e) {
                lastThrowable = e;
                e.printStackTrace();
                logger.error("An error with Fetch in Database: " + e.getMessage());
                throw new DatabaseDisconnectException(e);
            }
            session.close();
            return result;
        }
    }

    public <E extends Serializable> List<E> fetchAll(Class<E> entity) throws DatabaseDisconnectException {
        String hql = "FROM " + entity.getName();
        return executeHQL(hql, entity);
    }

    public <E extends Serializable> List<E> executeHQL(String hql, Class<E> entity) throws DatabaseDisconnectException {
        synchronized (staticLock) {
            ensureOpen();
            Session session = sessionFactory.openSession();
            List<E> result;
            try {
                result = session.createQuery(hql, entity).getResultList();
            } catch (Throwable e) {
                lastThrowable = e;
                e.printStackTrace();
                throw new DatabaseDisconnectException(e);
            }
            session.close();
            return result;
        }
    }

    public <E extends Serializable> List<E> executeSQLQuery(String sql, Class<E> entity) throws DatabaseDisconnectException {
        synchronized (staticLock) {
            ensureOpen();
            Session session = sessionFactory.openSession();
            List<E> result;
            try {
                result = session.createNativeQuery(sql, entity).getResultList();
            } catch (Exception e) {
                lastThrowable = e;
                e.printStackTrace();
                throw new DatabaseDisconnectException(e);
            }
            session.close();
            return result;
        }
    }

    public List<User> fetchUserWithEmail(String email) throws DatabaseDisconnectException {
        List<User> all = fetchAll(User.class);
        List<User> selected = new ArrayList<>();

        for (User user : all) {
            if (user.getEmail().equals(email)) {
                selected.add(user);
            }
        }

        return selected;

    }

    public List<User> fetchUserWithUsername(String username) throws DatabaseDisconnectException {
        List<User> all = fetchAll(User.class);
        List<User> selected = new ArrayList<>();

        for (User user : all) {
            if (user.getUserName().equals(username)) {
                selected.add(user);
            }
        }

        return selected;
    }

    public List<Thought> fetchThoughtWithUserId(int id) throws DatabaseDisconnectException {
        List<Thought> all = fetchAll(Thought.class);
        List<Thought> selected = new ArrayList<>();

        for (Thought thought : all) {
            if (thought.getUserId() == (id)) {
                selected.add(thought);
            }
        }

        return selected;

    }

    public Thought fetchThought(Thought thought) throws DatabaseDisconnectException {

        List<Thought> all = fetchAll(Thought.class);

        for (Thought t : all) {
            if (t.getUsername().equals(thought.getUsername()) && t.getText().equals(thought.getText())) {
                return t;
            }
        }

        return null;
    }

    public Notification fetchNotification(Notification notification) throws DatabaseDisconnectException {
        List<Notification> all = fetchAll(Notification.class);

        for (Notification n : all) {
            if (n.getSender().equals(notification.getSender()) && n.getText().equals(notification.getText()))
                return n;
        }


        return null;
    }

    public Chat fetchChat(Chat chat) throws DatabaseDisconnectException {
        List<Chat> all = fetchAll(Chat.class);

        for (Chat c : all) {
            if (c.getName().equals(chat.getName())) {
                return c;
            }
        }


        return null;
    }

    public User fetchUser(User user) throws DatabaseDisconnectException {
        List<User> all = fetchAll(User.class);

        for (User u : all) {
            if (u.getUserName().equals(user.getUserName())) {
                return u;
            }
        }


        return null;
    }

}