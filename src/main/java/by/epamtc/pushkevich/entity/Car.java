package by.epamtc.pushkevich.entity;

import java.util.Objects;

public class Car {
    private int id;
    private String brand;
    private String model;
    private short year;
    private int mileage;
    private String engineType;
    private double engineSize;
    private String transmissionType;
    private String wheelsDrive;
    private String type;

    public Car() {
    }

    public Car(int id, String brand, String model, short year, int mileage, String engineType, double engineSize, String transmissionType, String wheelsDrive, String type) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.engineType = engineType;
        this.engineSize = engineSize;
        this.transmissionType = transmissionType;
        this.wheelsDrive = wheelsDrive;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getWheelsDrive() {
        return wheelsDrive;
    }

    public void setWheelsDrive(String wheelsDrive) {
        this.wheelsDrive = wheelsDrive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                year == car.year &&
                mileage == car.mileage &&
                Double.compare(car.engineSize, engineSize) == 0 &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model) &&
                Objects.equals(engineType, car.engineType) &&
                Objects.equals(transmissionType, car.transmissionType) &&
                Objects.equals(wheelsDrive, car.wheelsDrive) &&
                Objects.equals(type, car.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, year, mileage, engineType, engineSize, transmissionType, wheelsDrive, type);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                ", engineType='" + engineType + '\'' +
                ", engineSize=" + engineSize +
                ", transmissionType='" + transmissionType + '\'' +
                ", wheelsDrive='" + wheelsDrive + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
