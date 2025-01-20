package main;

import java.util.HashMap;
import java.util.Map;

public class ElementCounter {

    public static <T> Map<T, Integer> count(T[] array) {
        Map<T, Integer> map = new HashMap<>();

        for (T element : array) {
            map.put(element, map.getOrDefault(element, 0) + 1);
        }

        return map;
    }
}
