package com.orangehrm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties=new Properties();

    //Using static block to avoid loading of proprties file multiple times when the get() method is executed
    //By using static block,properties file will be loaded once when the class is loaded into memory and
    // it will be available for all subsequent calls to the get() method without reloading the file.

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("config.properties not found. ",e);
        }
    }

    private ConfigReader()
    {
        throw new IllegalStateException("Utility class");
    }

    public static String get(String key) {
        String value= properties.getProperty(key);
        if(value==null)
        {
            throw new RuntimeException("Key not found in config.properties: "+key);
        }
        return value.trim();
    }
}
