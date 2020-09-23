package com.company.cities.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Value("classpath:cities.txt")
    private Resource initialCities;

    private List<String> cities = new ArrayList<>();
    private static final List<Character> exceptions = new ArrayList<>();

    static {
        exceptions.add('ь');
        exceptions.add('ы');
        exceptions.add('ъ');
        exceptions.add('э');
    }

    @Override
    public void addInitialCities() throws IOException {
        File initialScript = new File(initialCities.getURI());
        try (FileReader fr = new FileReader(initialScript);
             BufferedReader reader = new BufferedReader(fr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                cities.add(line.toLowerCase());
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public Optional<String> findByFirstLetter(Character letter) {
        return cities.stream()
                .filter(el -> el.startsWith(String.valueOf(letter)))
                .findFirst();
    }

    @Override
    public boolean existsUserWordInCities(String userCity) {
        Optional<String> selectedCity = cities.stream()
                .filter(el -> el.equalsIgnoreCase(userCity))
                .findAny();
        selectedCity.ifPresent(selected -> cities.remove(selected));
        return selectedCity.isPresent();
    }

    @Override
    public char includeExceptionCharIfExists(String city) {
        int lastCharIndex = city.length() - 1;
        char lastChar = city.charAt(lastCharIndex);
        if (exceptions.contains(lastChar)) {
            lastChar = city.charAt(--lastCharIndex);
        }
        return lastChar;
    }

    @Override
    public List<String> getCities() {
        return cities;
    }

}
