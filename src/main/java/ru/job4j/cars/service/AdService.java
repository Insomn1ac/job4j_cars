package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Advertisement;
import ru.job4j.cars.repository.AdRepository;

import java.util.List;

@Service
public class AdService {
    private final AdRepository store;

    public AdService(AdRepository store) {
        this.store = store;
    }

    public Advertisement add(Advertisement advertisement) {
        return store.add(advertisement);
    }

    public List<Advertisement> findAll() {
        return store.findAll();
    }

    public Advertisement findById(int id) {
        return store.findById(id);
    }

    public List<Advertisement> findByUserId(int userId) {
        return store.findByUserId(userId);
    }

    public List<Advertisement> findYesterdayAds() {
        return store.findYesterdayAds();
    }

    public List<Advertisement> findAdsWithPhoto() {
        return store.findAdsWithPhoto();
    }

    public List<Advertisement> findAdsWithCertainBrand(String brand) {
        return store.findAdsWithCertainBrand(brand);
    }

    public boolean update(Advertisement advertisement, int id) {
        return store.update(advertisement, id);
    }

    public boolean updateSoldState(int id) {
        return store.updateSoldState(id);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }
}
