package org.example;


public class GeometryUtils {


    public double convertToSquareMeters(double areaInAcres) {
        return areaInAcres * 4046.86;
    }

    public boolean compareAreas(double circleArea, double rectangleArea) {
        return circleArea > rectangleArea;
    }

    public void compareFigures(double circleArea, double triangleArea) {

        if (circleArea > triangleArea) {
            System.out.println("Круг больше треугольника по площади.");
        } else {
            System.out.println("Треугольник больше или равен кругу по площади.");
        }
    }
}