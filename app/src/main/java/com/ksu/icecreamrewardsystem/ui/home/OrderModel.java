package com.ksu.icecreamrewardsystem.ui.home;

public class OrderModel {
    private int id;
    private String item;
    private String date;
    private int quantity;
    private double price;

    public OrderModel() {

    }

    public OrderModel(int id, String item, int quantity, double price) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
