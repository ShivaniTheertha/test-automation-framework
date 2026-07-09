package com.orangehrm.api.models;

public class BookingDates {


    private String checkin;
    private String checkout;

    //need a default constructor by Jackson dependency for deserialization
    public BookingDates() {
    }

    private BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public static class Builder {

        private String checkin;
        private String checkout;


        public Builder checkin(String checkin) {
            this.checkin = checkin;
            return this;
        }

        public Builder checkout(String checkout) {
            this.checkout = checkout;
            return this;
        }

        public BookingDates build() {
            return new BookingDates(checkin, checkout);
        }

    }
}
