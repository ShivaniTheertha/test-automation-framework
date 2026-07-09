package com.orangehrm.utils;

import com.github.javafaker.Faker;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class TestDataGenerator {

    private static final Faker faker = new Faker();
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private TestDataGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Ui methods
    private static final String Run_ID = new SimpleDateFormat("ddMMHHmmss").format(new Date());

    public static String generateFirstName() {
        return faker.name().firstName() + " " + Run_ID;
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }

    public static String generateUserName() {
        return faker.name().username() + "_" + Run_ID;
    }

    public static String generateEmpID() {
        String empID = String.valueOf(UUID.randomUUID().hashCode() % 100000);
        empID = String.format("%05d", Math.abs(Integer.parseInt(empID)));
        System.out.println("Generated Employee ID: " + empID);
        return empID;
    }

    // Api methods
    public static String generateApiFirstName() {
        return faker.name().firstName();
    }

    public static String generateApiLastName() {
        return faker.name().lastName();
    }

    public static int generateTotalPrice() {
        return faker.number().numberBetween(100, 500);
    }

    public static String generateCheckInDate() {
        return LocalDate.now().plusDays(7).format(DATE_FORMAT);
    }

    public static String generateCheckOutDate() {
        return LocalDate.now().plusDays(10).format(DATE_FORMAT);
    }


}
