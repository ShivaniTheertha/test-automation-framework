package com.orangehrm.models;

public class EmployeeDetailsUpdate {


    private personalDetails personalDetails;

    public personalDetails getPersonalDetails() {
        return personalDetails;
    }

    public static class personalDetails {

        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String gender;
        private String maritalStatus;
        private String nationality;

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getGender() {
            return gender;
        }

        public String getLastName() {
            return lastName;
        }

        public String getNationality() {
            return nationality;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }


    }
}