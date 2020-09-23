package com.company.cities.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CityService {

    void addInitialCities() throws IOException;

    boolean existsUserWordInCities(String userCity);

    char includeExceptionCharIfExists(String city);

    List<String> getCities();

    Optional<String> findByFirstLetter(Character letter);

}
