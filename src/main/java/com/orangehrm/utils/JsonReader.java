package com.orangehrm.utils;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonReader {

    private static final Gson gson =new Gson();

    private JsonReader() {

        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static  <T> T read(String filePath, Class<T> classType)
    {
        try {
            FileReader file=new FileReader(filePath);
            {
                return gson.fromJson(file,classType);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not read Json file"+ filePath+e);
        }

    }
}
