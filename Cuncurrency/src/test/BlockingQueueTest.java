package test;

import main.BlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BlockingQueueTest {
    private BlockingQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new BlockingQueue<>(5);
    }

    @Test
    void testEnqueueAndDequeue() throws InterruptedException {

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(3, queue.size());

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());

        assertEquals(0, queue.size());
    }

    @Test
    void testEnqueueWhenFull() throws InterruptedException {
        // Заполняем очередь до предела
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        Thread producerThread = new Thread(() -> {
            try {
                queue.enqueue(6);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();

        Thread.sleep(100);

        assertEquals(5, queue.size());

        for (int i = 1; i <= 5; i++) {
            queue.dequeue();
        }

        producerThread.join();

        assertEquals(1, queue.size());
    }

    @Test
    void testDequeueWhenEmpty() throws InterruptedException {
        Thread consumerThread = new Thread(() -> {
            try {
                queue.dequeue();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumerThread.start();

        Thread.sleep(100);

        assertEquals(0, queue.size());

        queue.enqueue(1);

        consumerThread.join();

        assertEquals(0, queue.size());
    }
}


