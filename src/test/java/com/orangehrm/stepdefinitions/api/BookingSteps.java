package com.orangehrm.stepdefinitions.api;

import com.orangehrm.api.BookingApiHelper;
import com.orangehrm.api.models.Booking;
import com.orangehrm.utils.BookingDataReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookingSteps {

    private final BookingApiHelper bookingApiHelper = new BookingApiHelper();
    private Response response;
    private int bookingId;
    private Booking createbooking;
    private Booking updateBooking;
    private Map<String, Object> partialupdatemap;

    // GET ALL BOOKINGS SCENARIO

    @When("the user requests all bookings")
    public void the_user_requests_all_bookings() {
        response = bookingApiHelper.getAllBookings();
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode(), "Expected status code: " + expectedStatusCode + " but got: " + response.getStatusCode());
    }

    @Then("the booking list should not be empty")
    public void the_booking_list_should_not_be_empty() {
        Assert.assertFalse(response.jsonPath().getList("$").isEmpty(), "Expected booking list to be not empty but it was empty");
    }

    // GET BOOKING BY ID SCENARIO

    @Given("a new booking has been created")
    public void a_new_booking_has_been_created() {
        createbooking = BookingDataReader.getCreateBooking();
        response = bookingApiHelper.createBooking(createbooking);
        // Extract the booking ID from the response and store it for later use
        bookingId = response.jsonPath().getInt("bookingid");
    }

    @When("the user retrieves the booking by ID")
    public void the_user_retrieves_the_booking_by_id() {
        System.out.println("Retrieving booking with ID in @When: " + bookingId);
        response = bookingApiHelper.getBookingById(bookingId);
        System.out.println(response.then().extract().asPrettyString());
    }

    @And("the get booking response should match the schema")
    public void the_get_booking_response_should_match_the_schema() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/get_booking_schema.json"));
    }

    @And("the retrieved firstname should match the created booking firstname")
    public void the_retrieved_firstname_should_match_the_created_booking_firstname() {
        Assert.assertEquals(response.jsonPath().getString("firstname"), createbooking.getFirstname());

    }

    @And("the retrieved lastname should match the created booking lastname")
    public void the_retrieved_lastname_should_match_the_created_booking_lastname() {
        Assert.assertEquals(response.jsonPath().getString("lastname"), createbooking.getLastname());
    }

    // CREATE BOOKING SCENARIO

    @When("the user creates a new booking with valid data")
    public void the_user_creates_a_new_booking_with_valid_data() {
        createbooking = BookingDataReader.getCreateBooking();
        response = bookingApiHelper.createBooking(createbooking);

        // Extract the booking ID from the response and store it for later use
        bookingId = response.jsonPath().getInt("bookingid");
    }

    @And("the create booking response should match the schema")
    public void the_create_booking_response_should_match_the_schema() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/create_booking_schema.json"));
    }

    @And("the response should contain a valid booking ID")
    public void the_response_should_contain_a_valid_booking_id() {
        Assert.assertTrue(bookingId > 0, "Booking ID should be a positive integer but was: " + bookingId);
    }

    @And("the created booking firstname should match the request")
    public void the_created_booking_firstname_should_match_the_request() {
        Assert.assertEquals(response.jsonPath().getString("booking.firstname"), createbooking.getFirstname(), "FirstName mismatch");
    }

    @And("the created booking lastname should match the request")
    public void the_created_booking_lastname_should_match_the_request() {
        Assert.assertEquals(response.jsonPath().getString("booking.lastname"), createbooking.getLastname(), "LastName mismatch");
    }

    @And("the created booking totalprice should match the request")
    public void the_created_booking_totalprice_should_match_the_request() {
        Assert.assertEquals(response.jsonPath().getInt("booking.totalprice"), createbooking.getTotalprice(), "TotalPrice mismatch");
    }

    @And("the created booking depositpaid should match the request")
    public void the_created_booking_depositpaid_should_match_the_request() {
        Assert.assertEquals(response.jsonPath().getBoolean("booking.depositpaid"), createbooking.isDepositpaid(), "DepositPaid mismatch");
    }

    // UPDATE BOOKING SCENARIO

    @When("the user updates the booking with new data")
    public void the_user_updates_the_booking_with_new_data() {
        updateBooking = BookingDataReader.getUpdateBooking();
        response = bookingApiHelper.updateBooking(updateBooking, bookingId);
    }

    @And("the updated firstname should match the request")
    public void the_updated_firstname_should_match_the_request() {
        Assert.assertEquals(response.jsonPath().getString("firstname"),updateBooking.getFirstname(), "Updated firstName mismatch");
    }

    @And("the updated lastname should match the request")
    public void the_updated_lastname_should_match_the_request() {
        Assert.assertEquals(response.jsonPath().getString("lastname"),updateBooking.getLastname(), "Updated lastname mismatch");
    }

    @And("the updated checkin date should match the request")
    public void the_updated_checkin_date_should_match_the_request() {
        Assert.assertEquals( response.jsonPath().getString("bookingdates.checkin"),updateBooking.getBookingdates().getCheckin(), "Updated checkin date mismatch");
    }

    @And("the updated checkout date should match the request")
    public void the_updated_checkout_date_should_match_the_request() {
        Assert.assertEquals( response.jsonPath().getString("bookingdates.checkout"),updateBooking.getBookingdates().getCheckout(), "Updated checkout date mismatch");
    }

    // PARTIAL UPDATE BOOKING SCENARIO

    @When("the user partially updates the booking")
    public void the_user_partially_updates_the_booking() {
        partialupdatemap = BookingDataReader.getPartialUpdateBooking();
        response = bookingApiHelper.partialUpdateBooking(partialupdatemap, bookingId);
    }

    @And("the updated firstname should be reflected in the response")
    public void the_updated_firstname_should_be_reflected_in_the_response() {
        Assert.assertEquals(response.jsonPath().getString("firstname"),partialupdatemap.get("firstname").toString(), "Partial update firstname mismatch");
    }

    @And("the updated lastname should be reflected in the response")
    public void the_updated_lastname_should_be_reflected_in_the_response() {
        Assert.assertEquals( response.jsonPath().getString("lastname"), partialupdatemap.get("lastname").toString(),"Partial update lastname mismatch");
    }

    // DELETE BOOKING SCENARIO

    @When("the user deletes the booking by ID")
    public void the_user_deletes_the_booking_by_id() {
        response = bookingApiHelper.deleteBooking(bookingId);
    }

    // UPDATE BOOKING WITHOUT AUTH TOKEN SCENARIO

    @When("the user tries to update a booking without auth token")
    public void the_user_tries_to_update_a_booking_without_auth_token() {
        updateBooking = BookingDataReader.getUpdateBooking();
        response = bookingApiHelper.updateBookingWithoutAuth(updateBooking, bookingId);
    }

    // PARTIAL UPDATE BOOKING WITHOUT AUTH TOKEN SCENARIO

    @When("the user tries to partially update a booking without auth token")
    public void the_user_tries_to_partially_update_a_booking_without_auth_token() {
        Map<String,Object> partialupdatemap = BookingDataReader.getPartialUpdateBooking();
        response = bookingApiHelper.partialUpdateBookingWithoutAuth(partialupdatemap, bookingId);
    }

    // DELETE BOOKING WITHOUT AUTH TOKEN SCENARIO

    @When("the user tries to delete a booking without auth token")
    public void the_user_tries_to_delete_a_booking_without_auth_token() {
        response = bookingApiHelper.deleteBookingWithoutAuth(bookingId);
    }
}
