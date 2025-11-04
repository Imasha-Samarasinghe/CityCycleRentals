package com.sam.citycyclerentals;

public class Bike {
    private int id;
    private String bikeName;
    private double bikePrice;
    private int bikeImage;

    public Bike(int id, String bikeName, double bikePrice, int bikeImage) {
        this.id = id;
        this.bikeName = bikeName;
        this.bikePrice = bikePrice;
        this.bikeImage = bikeImage;
    }

    public int getId() {
        return id;
    }

    public String getBikeName() {
        return bikeName;
    }

    public double getBikePrice() {
        return bikePrice;
    }

    public int getBikeImage() {
        return bikeImage;
    }


}
