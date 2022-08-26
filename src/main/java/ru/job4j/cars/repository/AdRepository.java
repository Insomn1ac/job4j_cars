package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Advertisement;
import ru.job4j.cars.model.Car;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class AdRepository implements IStore {
    private final SessionFactory sf;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Advertisement addFromList(Advertisement advertisement, List<String> carsId) {
        tx(session -> {
                    for (String id : carsId) {
                        Car car = session.get(Car.class, Integer.parseInt(id));
                        advertisement.setCar(car);
                    }
                    return session.save(advertisement);
                },
                sf);
        return advertisement;
    }

    public Advertisement addNew(Advertisement advertisement) {
        tx(session -> session.save(advertisement), sf);
        return advertisement;
    }

    public List<Advertisement> findAll() {
        return tx(session -> session.createQuery("select distinct ad from Advertisement ad order by date desc, price desc", Advertisement.class)
                .getResultList(), sf);
    }

    public Advertisement findById(int id) {
        return tx(session -> session.get(Advertisement.class, id), sf);
    }

    public List<Advertisement> findByUserId(int userId) {
        return tx(session -> session.createQuery("from Advertisement ad where ad.user.id = :userId order by date desc, price desc")
                        .setParameter("userId", userId)
                        .getResultList(),
                sf);
    }

    public List<Advertisement> findYesterdayAds() {
        return tx(session -> session.createQuery("from Advertisement ad where ad.date >= :adDate", Advertisement.class)
                .setParameter("adDate", LocalDateTime.now().minusDays(1).format(formatter))
                .getResultList(), sf);
    }

    public List<Advertisement> findAdsWithPhoto() {
        return tx(session -> session.createQuery("from Advertisement ad "
                        + "where length(ad.photo) > 0 order by date desc, price desc", Advertisement.class)
                .getResultList(), sf);
    }

    public List<Advertisement> findAdsWithCertainBrand(String brand) {
        return tx(session -> session.createQuery("from Advertisement ad "
                        + "join fetch ad.car c "
                        + "where lower(c.carBrand) like lower(:fBrand)", Advertisement.class)
                .setParameter("fBrand", '%' + brand + '%', StringType.INSTANCE)
                .getResultList(), sf);
    }

    public boolean update(Advertisement advertisement, int id) {
        return tx(session -> session.createQuery("update Advertisement ad "
                                + "set ad.date = :date, ad.description = :description, ad.isSold = :isSold "
                                + "where ad.id = :id")
                        .setParameter("date", LocalDateTime.now().format(formatter))
                        .setParameter("description", advertisement.getDescription())
                        .setParameter("isSold", advertisement.isSold())
                        .setParameter("id", id)
                        .executeUpdate(),
                sf) > 0;
    }

    public boolean updateSoldState(int id) {
        return tx(session -> session.createQuery("update Advertisement ad "
                                + "set ad.isSold = true "
                                + "where ad.id = :id")
                        .setParameter("id", id)
                        .executeUpdate(),
                sf) > 0;
    }

    public boolean delete(int id) {
        return tx(session -> session.createQuery("delete from Advertisement ad where ad.id = :id")
                        .setParameter("id", id)
                        .executeUpdate(),
                sf) > 0;
    }
}
