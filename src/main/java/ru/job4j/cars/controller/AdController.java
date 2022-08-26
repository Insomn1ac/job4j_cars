package ru.job4j.cars.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cars.model.Advertisement;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.AdService;
import ru.job4j.cars.service.CarService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AdController {
    private final AdService adService;
    private final CarService carService;

    public AdController(AdService adService, CarService carService) {
        this.adService = adService;
        this.carService = carService;
    }

    private void setAccountToModel(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }

    @GetMapping("/ads")
    public String ads(Model model, HttpSession session) {
        model.addAttribute("ads", adService.findAll());
        setAccountToModel(model, session);
        return "ads";
    }

    @GetMapping("/userAds")
    public String userAds(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("userAds", adService.findByUserId(user.getId()));
        setAccountToModel(model, session);
        return "userAds";
    }

    @GetMapping("/addAdvertisement")
    public String addAdvertisement(Model model, HttpSession session) {
        setAccountToModel(model, session);
        model.addAttribute("cars", carService.findAll());
        return "addAdvertisement";
    }

    @PostMapping("/createAdvertisement")
    public String createAdvertisement(@ModelAttribute Advertisement ad,
                                      @RequestParam("file") MultipartFile file,
                                      @RequestParam("cars") List<String> carsId,
                                      HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        ad.setPhoto(file.getBytes());
        ad.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        adService.addFromList(ad, carsId);
        return "redirect:/ads";
    }

    @GetMapping("/addNewCarAd")
    public String addNewCarAd(Model model, HttpSession session) {
        setAccountToModel(model, session);
        model.addAttribute("engines", carService.findAllEngines());
        model.addAttribute("bodyTypes", carService.findAll());
        return "addNewCarAd";
    }

    @PostMapping("/createNewCarAd")
    public String createNewCarAd(@ModelAttribute Advertisement ad,
                                 @ModelAttribute Car car,
                                 @RequestParam("engine") int engineId,
                                 @RequestParam("file") MultipartFile file,
                                 HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        car.setEngine(carService.findEngineById(engineId));
        ad.setUser(user);
        ad.setCar(carService.add(car));
        ad.setPhoto(file.getBytes());
        ad.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        adService.addNew(ad);
        return "redirect:/ads";
    }

    @GetMapping("/advertisement/{adId}")
    public String adInfo(Model model, @PathVariable("adId") int id, HttpSession session) {
        model.addAttribute("advertisement", adService.findById(id));
        setAccountToModel(model, session);
        return "advertisement";
    }

    @GetMapping("/updateState/{id}")
    public String updateState(Model model, @PathVariable int id) {
        model.addAttribute("advertisement", adService.updateSoldState(id));
        return "redirect:/ads";
    }

    @GetMapping("/adsWithPhoto")
    public String adsWithPhoto(Model model, HttpSession session) {
        model.addAttribute("withPhoto", adService.findAdsWithPhoto());
        setAccountToModel(model, session);
        return "adsWithPhoto";
    }

    @GetMapping("/yesterdayAds")
    public String yesterdayAds(Model model, HttpSession session) {
        model.addAttribute("yesterdayAds", adService.findYesterdayAds());
        setAccountToModel(model, session);
        return "yesterdayAds";
    }

    @PostMapping("/searchByBrand")
    public String searchByBrand(@RequestParam String carBrand, RedirectAttributes redirected) {
        redirected.addAttribute("brand", carBrand);
        return "redirect:/search";
    }

    @GetMapping("/search")
    public String search(Model model, HttpSession session, @RequestParam String brand) {
        setAccountToModel(model, session);
        model.addAttribute("request", brand);
        model.addAttribute("brand", adService.findAdsWithCertainBrand(brand));
        return "search";
    }

    @GetMapping("/carPhoto/{advertisementId}")
    public ResponseEntity<Resource> download(@PathVariable("advertisementId") int adId) {
        Advertisement ad = adService.findById(adId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(ad.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(ad.getPhoto()));
    }
}
