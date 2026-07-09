package com.orangehrm.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangehrm.api.models.Booking;
import io.restassured.response.Response;

import java.util.Map;

import static com.orangehrm.api.ApiClient.*;


public class BookingApiHelper {

    public Response getAllBookings() {
        return getRequestSpec()
                .when().get("/booking")
                .then()
                .extract().response();
    }

    public Response getBookingById(int bookingId) {
        return getRequestSpec()
                .when().get("/booking/" + bookingId)
                .then()
                .extract().response();
    }

    public Response createBooking(Booking booking) {
        Response response= getRequestSpec().body(booking)
                .when()
                .post("/booking")
                .then()
                .extract().response();
        return response;
    }

    public Response updateBooking(Booking booking, int bookingId) {
        return getAuthRequestSpec().body(booking)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .extract().response();
    }

    public Response partialUpdateBooking(Map<String,Object> fields, int bookingId) {
        return getAuthRequestSpec().body(fields)
                .when().patch("/booking/" + bookingId)
                .then()
                .extract().response();
    }

    public Response deleteBooking(int bookingId) {
        return getAuthRequestSpec()
                .when().delete("/booking/" + bookingId)
                .then()
                .extract().response();
    }

    //Negative testing -no authentication

    public Response updateBookingWithoutAuth(Booking booking, int bookingId) {
        return getRequestSpec().body(booking)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .extract().response();
    }

    public Response partialUpdateBookingWithoutAuth(Map<String,Object> fields, int bookingId) {
        return getRequestSpec().body(fields)
                .when().patch("/booking/" + bookingId)
                .then()
                .extract().response();
    }

    public Response deleteBookingWithoutAuth(int bookingId) {
        return getRequestSpec()
                .when().delete("/booking/" + bookingId)
                .then()
                .extract().response();
    }

}
