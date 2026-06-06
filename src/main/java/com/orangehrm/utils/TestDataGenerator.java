package com.orangehrm.utils;

import com.github.javafaker.Faker;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TestDataGenerator {

    private static final Faker faker=new Faker();


    private TestDataGenerator()
    {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final String Run_ID=new SimpleDateFormat("ddMMHHmmss").format(new Date());

    public static String generateFirstName()
    {
        return faker.name().firstName()+" "+Run_ID;
    }

    public static String generateLastName()
    {
        return faker.name().lastName();
    }

    public static String generateUserName()
    {
        return faker.name().username()+"_"+Run_ID;
    }

    public static String generateEmpID()
    {
        String empID = String.valueOf(UUID.randomUUID().hashCode() % 100000);
        empID = String.format("%05d", Math.abs(Integer.parseInt(empID)));
        System.out.println("Generated Employee ID: " + empID);
        return empID;
    }

}
