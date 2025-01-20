package test;

import main.ArrayFilter;
import main.Filter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ApplyTest {
    private final Filter filter = new Filter() {
        @Override
        public Object apply(Object o) {
            return ((Number) o).intValue() * 2;
        }
    };


    @Test
    void apply() {

        Object[] inputArray = {1, 2, 3};
        Integer[] expected = {2, 4, 6};

        Object[] actual = ArrayFilter.filter(inputArray, filter);

        assertArrayEquals(expected, actual);


    }
}
