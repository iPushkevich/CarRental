package by.epamtc.pushkevich.entity;

import java.util.Objects;

public class User {
    private String name;
    private String surName;
    private String email;
    private String password;
    private String phoneNumber;
    private byte age;
    private String passport;
    private byte drivingExperience;
    private short roadAccidentCount;
    private short previousRentCount;
    private byte discount;

    public User() {
    }

    public User(String name, String surName, String email, String password, String phoneNumber, byte age, String passport, byte drivingExperience, short roadAccidentCount, short previousRentCount, byte discount) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.passport = passport;
        this.drivingExperience = drivingExperience;
        this.roadAccidentCount = roadAccidentCount;
        this.previousRentCount = previousRentCount;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public byte getDrivingExperience() {
        return drivingExperience;
    }

    public void setDrivingExperience(byte drivingExperience) {
        this.drivingExperience = drivingExperience;
    }

    public short getRoadAccidentCount() {
        return roadAccidentCount;
    }

    public void setRoadAccidentCount(short roadAccidentCount) {
        this.roadAccidentCount = roadAccidentCount;
    }

    public short getPreviousRentCount() {
        return previousRentCount;
    }

    public void setPreviousRentCount(short previousRentCount) {
        this.previousRentCount = previousRentCount;
    }

    public byte getDiscount() {
        return discount;
    }

    public void setDiscount(byte discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                drivingExperience == user.drivingExperience &&
                roadAccidentCount == user.roadAccidentCount &&
                previousRentCount == user.previousRentCount &&
                discount == user.discount &&
                Objects.equals(name, user.name) &&
                Objects.equals(surName, user.surName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(passport, user.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surName, email, password, phoneNumber, age, passport, drivingExperience, roadAccidentCount, previousRentCount, discount);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", passport='" + passport + '\'' +
                ", drivingExperience=" + drivingExperience +
                ", roadAccidentCount=" + roadAccidentCount +
                ", previousRentCount=" + previousRentCount +
                ", discount=" + discount +
                '}';
    }
}
