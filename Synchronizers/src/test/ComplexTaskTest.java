package test;

import main.ComplexTask;
import main.ComplexTaskExecutor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexTaskExecutorTest {


    @Test
    void executeTest() throws InterruptedException {
        int numberOfTasks = 5;
        ComplexTaskExecutor executor = new ComplexTaskExecutor(numberOfTasks);

        // Запускаем задачи
        executor.executeTasks(numberOfTasks);

        // Ожидаем, что результат будет равен сумме 1 + 2 + 3 + 4 + 5 = 15
        int expectedResult = 15;

        // Проверяем, что финальный результат соответствует ожидаемому значению
        assertEquals(expectedResult, ComplexTask.getResult());
    }
}
