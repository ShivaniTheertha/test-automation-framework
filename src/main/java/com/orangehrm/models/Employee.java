package com.orangehrm.models;

public class Employee {

    private String firstName;
    private String lastName;
    private String userName;
    private String empID;

    private Employee(String firstName, String lastName, String userName, String empID)
     {
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.empID=empID;
     }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getUserNameName() {
        return userName;
    }
    public String getEmpID() {
        return empID;
    }

    public static class Builder{

        private String firstName;
        private String lastName;
        private String userName;
        private String empID;

        public Builder firstName(String firstName)
        {
            this.firstName=firstName;
            return this;
        }

        public Builder lastName(String lastName)
        {
            this.lastName=lastName;
            return this;
        }

        public Builder userName(String userName)
        {
            this.userName=userName;
            return this;
        }

        public Builder empID(String empID)
        {
            this.empID=empID;
            return this;
        }

        public Employee Build()
        {
            return new Employee(firstName, lastName, userName,empID);
        }
    }

}
