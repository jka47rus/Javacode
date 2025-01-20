package main;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String[] inputArray = {"apple", "banana", "apple", "orange", "banana", "banana"};

        Map<String, Integer> result = ElementCounter.count(inputArray);

        result.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
