package test;

import main.ElementCounter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountTest {

    @Test
    void count() {
        String[] inputArray = {"apple", "banana", "apple"};
        Map<String, Integer> expected = new HashMap<>() {{
            put("banana", 1);
            put("apple", 2);

        }};

        Map<String, Integer> actual = ElementCounter.count(inputArray);

        assertEquals(expected, actual);

    }

}
