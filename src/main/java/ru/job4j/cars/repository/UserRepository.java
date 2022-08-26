package ru.job4j.cars.repository;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IStore {
    private final SessionFactory sf;

    public UserRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> add(User user) {
        try {
            return Optional.of((User) tx(session -> session.merge(user), sf));
        } catch (HibernateException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return tx(session -> session.createQuery("from User where email = :email and password = :pass")
                        .setParameter("email", email)
                        .setParameter("pass", password)
                        .uniqueResultOptional(),
                sf);
    }

    public Optional<User> findUserByName(String name) {
        return tx(session -> session.createQuery("from User where name = :name")
                        .setParameter("name", name)
                        .uniqueResultOptional(),
                sf);
    }

    public List<User> findAll() {
        return tx(session -> session.createQuery("from User ").getResultList(), sf);
    }
}
