package test;

import main.ComplexTask;
import main.ComplexTaskExecutor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexTaskTest {


    @Test
    void executeTest() throws InterruptedException {
        int numberOfTasks = 5;
        ComplexTaskExecutor executor = new ComplexTaskExecutor(numberOfTasks);

        executor.executeTasks(numberOfTasks);

        int expected = 15;

        Thread.sleep(200);

        assertEquals(expected, ComplexTask.getResult());
    }
}
