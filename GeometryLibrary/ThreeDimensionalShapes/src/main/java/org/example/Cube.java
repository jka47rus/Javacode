package org.example;

public class Cube {
    private double side;

    public Cube(double side) {
        this.side = side;
    }

    public double volume() {
        return Math.pow(side, 3);
    }

    public double surfaceArea() {
        return 6 * Math.pow(side, 2);
    }
}
