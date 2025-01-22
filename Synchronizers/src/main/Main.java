package main;

public class Main {
    public static void main(String[] args) {
        ComplexTaskExecutor executor = new ComplexTaskExecutor(5); // Создаем Executor для 5 задач


        Runnable testRunnable = () -> {
            System.out.println(Thread.currentThread().getName() + " started the test.");

            executor.executeTasks(5);

            System.out.println(Thread.currentThread().getName() + " completed the test.");
        };

        Thread thread1 = new Thread(testRunnable, "TestThread-1");
        Thread thread2 = new Thread(testRunnable, "TestThread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


    }
}