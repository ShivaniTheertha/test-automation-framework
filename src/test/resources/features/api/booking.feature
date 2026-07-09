@api
Feature: Booking API - Restful Booker
  As a user of the Restful Booker API
  I want to create, read, update and delete bookings
  So that I can manage hotel reservations

  @api @smoke @regression @api-regression
  Scenario: Get all Bookings returns a non empty list
    When the user requests all bookings
    Then the response status code should be 200
    And the booking list should not be empty

  @api @regression @api-regression
  Scenario: Get booking details using booking id successfully
    Given a new booking has been created
    When the user retrieves the booking by ID
    Then the response status code should be 200
    And the get booking response should match the schema
    And the retrieved firstname should match the created booking firstname
    And the retrieved lastname should match the created booking lastname

  @api @smoke @regression @api-regression
  Scenario: Create a new booking successfully
    When the user creates a new booking with valid data
    Then the response status code should be 200
    And the create booking response should match the schema
    And the response should contain a valid booking ID
    And the created booking firstname should match the request
    And the created booking lastname should match the request
    And the created booking totalprice should match the request
    And the created booking depositpaid should match the request

  @api @regression @api-regression
  Scenario: Update a booking successfully with PUT
    Given a new booking has been created
    When the user updates the booking with new data
    Then the response status code should be 200
    And the updated firstname should match the request
    And the updated lastname should match the request
    And the updated checkin date should match the request
    And the updated checkout date should match the request

  @api @regression @api-regression
  Scenario: Partially update a booking successfully with PATCH
    Given a new booking has been created
    When the user partially updates the booking
    Then the response status code should be 200
    And the updated firstname should be reflected in the response
    And the updated lastname should be reflected in the response


  @api @regression @api-regression
  Scenario: Delete a booking successfully
    Given a new booking has been created
    When the user deletes the booking by ID
    Then the response status code should be 201

  @api @regression @api-regression
  Scenario: Deleted booking should no longer be retrievable
    Given a new booking has been created
    When the user deletes the booking by ID
    And the user retrieves the booking by ID
    Then the response status code should be 404

    #-----------Negative Scenarios----------------

  @api @regression @api-regression @negative
  Scenario: Update a booking without auth token
    Given a new booking has been created
    When the user tries to update a booking without auth token
    Then the response status code should be 403

  @api @regression @api-regression @negative
  Scenario: Partially Update a booking without auth token
    Given a new booking has been created
    When the user tries to partially update a booking without auth token
    Then the response status code should be 403

  @api @regression @api-regression @negative
  Scenario: Delete a booking without auth token
    Given a new booking has been created
    When the user tries to delete a booking without auth token
    Then the response status code should be 403