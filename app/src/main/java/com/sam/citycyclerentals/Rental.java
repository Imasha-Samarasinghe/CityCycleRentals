package com.sam.citycyclerentals;

public class Rental {
    private int rentalId;
    private int userId;
    private int bikeId;
    private String startDate;
    private String endDate;
    private String status;
    private String paymentMethod;
    private double totalPrice;

    public Rental(int rentalId, int userId, int bikeId, String startDate, String endDate, String status, String paymentMethod, double totalPrice) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.bikeId = bikeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

    public int getId() {return rentalId;}
    public int getRentalId() { return rentalId; }
    public int getUserId() { return userId; }
    public int getBikeId() { return bikeId; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getTotalPrice() { return totalPrice; }
    public void setStatus(String status) {
        this.status = status;
    }
}
