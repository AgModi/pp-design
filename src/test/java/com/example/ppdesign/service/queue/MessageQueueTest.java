package com.example.ppdesign.service.queue;

import com.example.ppdesign.dto.EmployeeDto;
import com.example.ppdesign.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageQueueTest {

    @InjectMocks
    private MessageQueue messageQueue;

    //@Test
    public void testEnqueue() {
        for (int i = 0; i < 12; i++) {
            EmployeeDto employeeDto = new EmployeeDto(i, "Rachit", 40000);
            messageQueue.enqueue(JsonUtil.getJson(employeeDto), "employee", "10000" );
        }
    }

    @Test
    public void testEnqueueWithThread() throws InterruptedException {

        Runnable runnable1 = () -> {
            for (int i = 0; i < 12; i++) {
                EmployeeDto employeeDto = new EmployeeDto(i+100, "Rachit", 40000);
                System.out.println("Runnable 1 run iteration "+i);
                synchronized (messageQueue) {
                    messageQueue.enqueue(JsonUtil.getJson(employeeDto), "employee", "10000");
                }
            }
        };

        Runnable runnable2 = () -> {
            for (int i = 0; i < 12; i++) {
                EmployeeDto employeeDto = new EmployeeDto(i+100, "Rachit", 40000);
                System.out.println("Runnable 2 run iteration "+i);
                synchronized (messageQueue) {
                    messageQueue.enqueue(JsonUtil.getJson(employeeDto), "employee", "10000");
                }
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        System.out.println("Threads started");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
