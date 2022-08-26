package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;

@Service
public class CarService {
    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car add(Car car) {
        return repository.add(car);
    }

    public List<Car> findAll() {
        return repository.findAll();
    }

    public List<Car> findCarsByBrand(String brand) {
        return repository.findCarsByBrand(brand);
    }

    public List<Car> findCarsByBodyType(String bodyType) {
        return repository.findCarsByBodyType(bodyType);
    }

    public Engine findEngineById(int id) {
        return repository.findEngineById(id);
    }

    public List<Engine> findAllEngines() {
        return repository.findAllEngines();
    }
}
