package main;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {
    private final CyclicBarrier barrier;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.barrier = new CyclicBarrier(numberOfTasks, () -> {

            System.out.println("All tasks completed. Final result: " + ComplexTask.getResult());
        });
    }

    public void executeTasks(int numberOfTasks) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            final int taskId = i + 1;
            executor.submit(() -> {
                ComplexTask task = new ComplexTask(taskId);
                task.execute();

                try {
                    Thread.sleep(200);
                    barrier.await();

                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
