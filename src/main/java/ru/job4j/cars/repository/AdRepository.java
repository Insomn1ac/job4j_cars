package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Advertisement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class AdRepository implements IStore {
    private final SessionFactory sf;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Advertisement> findYesterdayAds() {
        return tx(session -> session.createQuery("from Advertisement ad where ad.date >= :adDate", Advertisement.class)
                .setParameter("adDate", LocalDateTime.now().minusDays(1).format(formatter))
                .getResultList(), sf);
    }

    public List<Advertisement> findAdsWithPhoto() {
        return tx(session -> session.createQuery("from Advertisement ad where ad.photo != null", Advertisement.class)
                .getResultList(), sf);
    }

    public List<Advertisement> findAdsWithCertainBrand(String brand) {
        return tx(session -> session.createQuery("from Advertisement ad "
                        + "join fetch ad.car c "
                        + "where c.carBrand = :fBrand", Advertisement.class)
                .setParameter("fBrand", brand)
                .getResultList(), sf);
    }
}
