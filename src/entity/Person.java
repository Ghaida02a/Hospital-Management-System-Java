package entity;

import Interface.Displayable;
import Utils.HelperUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Person implements Displayable {
    String id;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String gender;
    String phoneNumber;
    String email;
    String address;

    public Person() {
        // Generate an ID with "P" prefix for Person
        this.id = HelperUtils.generateId("P");
    }

    public Person(String id, String firstName, String lastName,
                  LocalDate dateOfBirth, String gender, String phoneNumber,
                  String email, String address) {
        this.id = HelperUtils.isNull(id) ? HelperUtils.generateId("P") : id;

        this.firstName = HelperUtils.isValidString(firstName) ? firstName : "";
        this.lastName = HelperUtils.isValidString(lastName) ? lastName : "";
        this.dateOfBirth = dateOfBirth;
        this.gender = HelperUtils.isValidString(gender) ? gender : "";
        this.phoneNumber = HelperUtils.isValidString(phoneNumber) ? phoneNumber : "";
        this.email = HelperUtils.isValidEmail(email) ? email : "";
        this.address = HelperUtils.isValidString(address) ? address : "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = HelperUtils.isNull(id) ? HelperUtils.generateId("P") : id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && firstName.matches("[a-zA-Z\\s]+")) {
            this.firstName = firstName;
        } else {
            this.firstName = "";
            System.out.println("Invalid first name. Only letters allowed.");
        }    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && lastName.matches("[a-zA-Z\\s]+")) {
            this.lastName = lastName;
        } else {
            this.lastName = "";
            System.out.println("Invalid lastName name. Only letters allowed.");
        }    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth != null && HelperUtils.isValidAge(dateOfBirth)) {
            this.dateOfBirth = dateOfBirth;
        } else {
            this.dateOfBirth = null;
            System.out.println("⚠ Invalid date of birth.");
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender != null && gender.matches("(?i)male|female")) {
            this.gender = gender.substring(0,1).toUpperCase() + gender.substring(1).toLowerCase();
        } else {
            this.gender = "";
            System.out.println("⚠ Invalid gender. Must be 'Male' or 'Female'.");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = HelperUtils.isValidString(phoneNumber) ? phoneNumber : "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (HelperUtils.isValidEmail(email)) {
            this.email = email;
        } else {
            this.email = "";
            System.out.println("Invalid email format.");
        }    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (HelperUtils.isNotNull(address)) {
            this.address = address.trim();
        } else {
            this.address = "";
            System.out.println("Invalid address.");
        }    }


    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(gender, person.gender) && Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(email, person.email) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
    }

    @Override
    public String displayInfo(String str) {
        String info = "ID: " + id + System.lineSeparator()
                + "Name: " + firstName + " " + lastName + System.lineSeparator()
                + "Date of Birth: " + dateOfBirth + System.lineSeparator()
                + "Gender: " + gender + System.lineSeparator()
                + "Phone: " + phoneNumber + System.lineSeparator()
                + "Email: " + email + System.lineSeparator()
                + "Address: " + address;
        return info;
    }

    @Override
    public String displaySummary(String str) {
        return id + " - " + firstName + " " + lastName;
    }
}
