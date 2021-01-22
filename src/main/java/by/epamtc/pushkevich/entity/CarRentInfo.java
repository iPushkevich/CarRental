package by.epamtc.pushkevich.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarRentInfo {
    private String description;
    private short rentCost;
    private double carRating;
    private List<String> carReviews = new ArrayList<>();
    private double fuelLvl;

    public CarRentInfo() {
    }

    public CarRentInfo(String description, short rentCost, double carRating, List<String> carReviews, double fuelLvl) {
        this.description = description;
        this.rentCost = rentCost;
        this.carRating = carRating;
        this.carReviews = carReviews;
        this.fuelLvl = fuelLvl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getRentCost() {
        return rentCost;
    }

    public void setRentCost(short rentCost) {
        this.rentCost = rentCost;
    }

    public double getCarRating() {
        return carRating;
    }

    public void setCarRating(double carRating) {
        this.carRating = carRating;
    }

    public List<String> getCarReviews() {
        return carReviews;
    }

    public void setCarReviews(List<String> carReviews) {
        this.carReviews = carReviews;
    }

    public double getFuelLvl() {
        return fuelLvl;
    }

    public void setFuelLvl(double fuelLvl) {
        this.fuelLvl = fuelLvl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRentInfo that = (CarRentInfo) o;
        return rentCost == that.rentCost &&
                Double.compare(that.carRating, carRating) == 0 &&
                Double.compare(that.fuelLvl, fuelLvl) == 0 &&
                Objects.equals(description, that.description) &&
                Objects.equals(carReviews, that.carReviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, rentCost, carRating, carReviews, fuelLvl);
    }

    @Override
    public String toString() {
        return "CarRentInfo{" +
                "description='" + description + '\'' +
                ", rentCost=" + rentCost +
                ", carRating=" + carRating +
                ", carReviews=" + carReviews +
                ", fuelLvl=" + fuelLvl +
                '}';
    }
}
