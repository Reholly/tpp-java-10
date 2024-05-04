package org.example;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    public final CountDownLatch Latch = new CountDownLatch(1);

    public void receive(String message) {
        System.out.println("Receive message");
        System.out.println(message);
        Latch.countDown();
    }
}
