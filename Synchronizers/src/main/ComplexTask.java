package main;

import java.util.concurrent.atomic.AtomicInteger;

public class ComplexTask {
    private final int taskId;
    private static final AtomicInteger result = new AtomicInteger(0);

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public void execute() {

        result.addAndGet(taskId);
    }

    public static int getResult() {
        return result.get();
    }
}
