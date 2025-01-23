package main;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        Map<String, Double> totalCostByProduct = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct,
                        Collectors.summingDouble(Order::getCost)));

        List<Map.Entry<String, Double>> topProducts = totalCostByProduct.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Три самых дорогих продукта и их общая стоимость:");
        for (Map.Entry<String, Double> entry : topProducts) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
