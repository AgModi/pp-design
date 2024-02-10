package com.example.ppdesign.service.queue;

import com.example.ppdesign.dto.Employee;
import com.example.ppdesign.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageQueueTest {

    @InjectMocks
    private MessageQueue messageQueue;

    //@Test
    public void testEnqueue() {
        for (int i = 0; i < 12; i++) {
            Employee employee = new Employee(i, "Rachit", 40000);
            System.out.println("Runnable 1 :" +messageQueue.enqueue(JsonUtil.getJson(employee)));
        }
    }

    //@Test
    public void testEnqueueWithThread() {
        //TODO failed this
        Runnable runnable1 = () -> {
            synchronized (messageQueue) {
                for (int i = 0; i < 12; i++) {
                    Employee employee = new Employee(i, "Rachit", 40000);
                    System.out.println("Runnable 1 run iteration "+i);
                    System.out.println("Runnable 1 :" +messageQueue.enqueue(JsonUtil.getJson(employee)));
                    try {
                        messageQueue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Runnable runnable2 = () -> {
            synchronized (messageQueue) {
                for (int i = 0; i < 12; i++) {
                    Employee employee = new Employee(i+100, "Rachit", 40000);
                    System.out.println("Runnable 2 run iteration "+i);
                    System.out.println("Runnable 2 :" + messageQueue.enqueue(JsonUtil.getJson(employee)));
                    messageQueue.notify();
                }
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        System.out.println("Threads started");

        t1.start();
        t2.start();
    }
}
