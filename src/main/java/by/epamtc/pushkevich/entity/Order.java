package by.epamtc.pushkevich.entity;

import java.util.Date;
import java.util.Objects;

public class Order {
    private int id;
    private Date startRentDate;
    private Date endRentDate;
    private double cost;
    private double finalCost;
    private int userID;
    private int carID;
    private String status;
    private String declineReason;

    public Order() {
    }

    public Order(int id, Date startRentDate, Date endRentDate, double cost, double finalCost, int userID, int carID, String status, String declineReason) {
        this.id = id;
        this.startRentDate = startRentDate;
        this.endRentDate = endRentDate;
        this.cost = cost;
        this.finalCost = finalCost;
        this.userID = userID;
        this.carID = carID;
        this.status = status;
        this.declineReason = declineReason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartRentDate() {
        return startRentDate;
    }

    public void setStartRentDate(Date startRentDate) {
        this.startRentDate = startRentDate;
    }

    public Date getEndRentDate() {
        return endRentDate;
    }

    public void setEndRentDate(Date endRentDate) {
        this.endRentDate = endRentDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(String declineReason) {
        this.declineReason = declineReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Double.compare(order.cost, cost) == 0 &&
                Double.compare(order.finalCost, finalCost) == 0 &&
                userID == order.userID &&
                carID == order.carID &&
                Objects.equals(startRentDate, order.startRentDate) &&
                Objects.equals(endRentDate, order.endRentDate) &&
                Objects.equals(status, order.status) &&
                Objects.equals(declineReason, order.declineReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startRentDate, endRentDate, cost, finalCost, userID, carID, status, declineReason);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", startRentDate=" + startRentDate +
                ", endRentDate=" + endRentDate +
                ", cost=" + cost +
                ", finalCost=" + finalCost +
                ", userID=" + userID +
                ", carID=" + carID +
                ", status='" + status + '\'' +
                ", declineReason='" + declineReason + '\'' +
                '}';
    }
}
