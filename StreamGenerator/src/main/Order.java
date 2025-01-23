package main;

public class Order {
    private String product;
    private double cost;

    public Order(String product, double price) {
        this.product = product;
        this.cost = price;
    }

    public String getProduct() {
        return product;
    }

    public double getCost() {
        return cost;
    }
}

