package com.orangehrm.api.models;

public class Booking {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    //need a default constructor by Jackson dependency for deserialization
    public Booking() {
    }

    private Booking(BookingDates bookingdates, boolean depositpaid, String firstname, String lastname, int totalprice, String additionalneeds) {
        this.bookingdates = bookingdates;
        this.depositpaid = depositpaid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.additionalneeds = additionalneeds;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public static class Builder {
        private String firstname;
        private String lastname;
        private int totalprice;
        private boolean depositpaid;
        private BookingDates bookingdates;
        private String additionalneeds;

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder totalprice(int totalprice) {
            this.totalprice = totalprice;
            return this;
        }

        public Builder depositpaid(boolean depositpaid) {
            this.depositpaid = depositpaid;
            return this;
        }

        public Builder bookingdates(BookingDates bookingdates) {
            this.bookingdates = bookingdates;
            return this;
        }

        public Builder additionalneeds(String additionalneeds) {
            this.additionalneeds = additionalneeds;
            return this;
        }

        public Booking build() {
            return new Booking(bookingdates, depositpaid, firstname, lastname, totalprice, additionalneeds);
        }

    }


}
