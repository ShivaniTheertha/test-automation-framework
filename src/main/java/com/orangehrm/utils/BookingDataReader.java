package com.orangehrm.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangehrm.api.models.Booking;
import com.orangehrm.api.models.BookingDates;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookingDataReader {

    private static final String FILE_PATH =
            "src/test/resources/Test Data/booking_data.json";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Read JSON file ONCE at class level — reused across all methods
    private static final JsonNode root = loadJson();

    private BookingDataReader() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static JsonNode loadJson() {
        try {
            return objectMapper.readTree(new File(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read booking data from: " + FILE_PATH, e
            );
        }
    }

    private static Booking buildBooking(String section) {
                    JsonNode node = root.get(section);

            if (section.equals("createBooking") || section.equals("updateBooking")) {
                BookingDates bookingDates = new BookingDates.Builder().checkin(TestDataGenerator.generateCheckInDate()).checkout(TestDataGenerator.generateCheckOutDate()).build();

                return new Booking.Builder().firstname(TestDataGenerator.generateApiFirstName()).lastname(TestDataGenerator.generateApiLastName())
                        .totalprice(TestDataGenerator.generateTotalPrice()).bookingdates(bookingDates).depositpaid(node.get("depositpaid").asBoolean())
                        .additionalneeds(node.get("additionalneeds").asText()).build();
            }else {
                // If the section is not recognized, throw an exception
                throw new IllegalArgumentException("Unknown booking section: " + section);
            }
        }

    public static Map<String, Object> getPartialUpdateBooking() {
        JsonNode node = root.get("partialUpdateBooking");

        Map<String, Object> partialUpdate = new HashMap<>();
        if (node.has("firstname")) {
            partialUpdate.put("firstname", node.get("firstname").asText());
        }
        if (node.has("lastname")) {
            partialUpdate.put("lastname", node.get("lastname").asText());
        }
        System.out.println("Partial update data: " + partialUpdate);
        return partialUpdate;
    }

    public static Booking getCreateBooking() {
        return buildBooking("createBooking");
    }

    public static Booking getUpdateBooking() {
        return buildBooking("updateBooking");
    }


}
