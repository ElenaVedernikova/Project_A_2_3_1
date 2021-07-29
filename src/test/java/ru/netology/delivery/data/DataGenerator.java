package ru.netology.delivery.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {

    public DataGenerator() {
    }

    private static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(int day) {
        LocalDate localDate = LocalDate.now();
        LocalDate newDate = localDate.plusDays(day);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = newDate.format(dateFormat);
        return date;
    }

    public static String generateCity(String locale) {
        return faker.address().city();
    }

    public static String generateName(String locale) {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(String locale) {
        return faker.phoneNumber().phoneNumber();
    }
}
