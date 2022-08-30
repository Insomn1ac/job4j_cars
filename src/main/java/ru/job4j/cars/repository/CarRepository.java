package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;

@Repository
public class CarRepository implements IStore {
    private final SessionFactory sf;

    public CarRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Car add(Car car) {
        return (Car) tx(session -> session.merge(car), sf);
    }

    public List<Car> findCarsByBrand(String brand) {
        return tx(session -> session.createQuery("from Car where carBrand = :brand")
                        .setParameter("brand", brand)
                        .getResultList(),
                sf);
    }

    public List<Car> findDistinctBrands() {
        return tx(session -> session.createQuery("select distinct carBrand from Car").getResultList(), sf);
    }

    public List<Car> findCarsByBodyType(String bodyType) {
        return tx(session -> session.createQuery("from Car where bodyType = :bodyType")
                        .setParameter("bodyType", bodyType)
                        .getResultList(),
                sf);
    }

    public List<Car> findAll() {
        return tx(session -> session.createQuery("from Car")
                .getResultList(), sf);
    }

    public Engine findEngineById(int id) {
        return (Engine) tx(session -> session.createQuery("from Engine where id = :id ")
                .setParameter("id", id)
                .getSingleResult(), sf);
    }

    public List<Engine> findAllEngines() {
        return tx(session -> session.createQuery("from Engine ").getResultList(), sf);
    }

    public List<Car> findAllBodyTypes() {
        return tx(session -> session.createQuery("select distinct bodyType from Car ").getResultList(), sf);
    }
}
