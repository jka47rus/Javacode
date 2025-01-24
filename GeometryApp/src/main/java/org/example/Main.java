package org.example;


public class Main {
    public static void main(String[] args) {
        Circle circle = new Circle(5);
        Rectangle rectangle = new Rectangle(4, 6);
        Triangle triangle = new Triangle(3, 4, 5);
        GeometryUtils geometryUtils = new GeometryUtils();
        Cube cube = new Cube(3);
        Sphere sphere = new Sphere(5);


        System.out.println("Circle Area: " + circle.area());
        System.out.println("Circle Perimeter: " + circle.perimeter());
        System.out.println("Diameter: " + circle.diameter());

        System.out.println("Rectangle Area: " + rectangle.area());
        System.out.println("Rectangle Perimeter: " + rectangle.perimeter());

        System.out.println("Triangle Area: " + triangle.area());
        System.out.println("Triangle Perimeter: " + triangle.perimeter());

        System.out.println("Cube volume " + cube.volume());
        System.out.println("Cube surfaceArea() " + cube.surfaceArea());

        System.out.println("GeometryUtils 1 акр в квадратных метрах: " + geometryUtils.convertToSquareMeters(1));
        System.out.println("GeometryUtils compareAreas: " + geometryUtils.compareAreas(circle.area(), rectangle.area()));
        geometryUtils.compareFigures(circle.area(), triangle.area());

        System.out.println("Sphere volume: " + sphere.volume());
        System.out.println("Sphere surfaceArea: " + sphere.surfaceArea());


    }


}