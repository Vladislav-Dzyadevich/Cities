package com.company.cities.controller;

import com.company.cities.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class GameController {

    @Autowired
    private CityService cityService;
    private String currentProgramCity;

    @GetMapping("/begin")
    public String beginMethod(Model model) throws IOException {
        cityService.addInitialCities();
        putProgramCity(cityService.getCities().get(0), model);
        return "index";
    }

    @GetMapping("/next")
    public String nextMethod(@RequestParam("city") String city, Model model) {
        city = city.toLowerCase();
        if (!cityService.existsUserWordInCities(city)) {
            model.addAttribute("city", currentProgramCity);
            model.addAttribute("notExistCity", "Город был уже выбран или не присутвует в БД.");
            return "index";
        }
        char lastProgramLetter = cityService.includeExceptionCharIfExists(currentProgramCity);
        char userFirstLetter = city.charAt(0);
        if (lastProgramLetter != userFirstLetter) {
            model.addAttribute("city", currentProgramCity);
            model.addAttribute("notMatchLetters",
                    "Пожалуйста, введите слово которое начинается на " + lastProgramLetter);
            return "index";
        }
        char lastUserLetter = cityService.includeExceptionCharIfExists(city);
        cityService.findByFirstLetter(lastUserLetter)
                .ifPresentOrElse(c -> putProgramCity(c, model), () -> {
                    model.addAttribute("notFoundCityError", "Город не найден. Вы победили!");
                });
        return "index";
    }

    @PostMapping("/end")
    public String endMethod() {
        return "end";
    }

    private void putProgramCity(String city, Model model) {
        cityService.getCities().remove(city);
        currentProgramCity = city;
        model.addAttribute("city", currentProgramCity);
    }

}
