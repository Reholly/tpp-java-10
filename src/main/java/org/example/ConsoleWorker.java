package org.example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class ConsoleWorker implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbit;
    @Autowired
    private Receiver receiver;

    @Override
    public void run(String... args) throws Exception {
        var reader = new Scanner(System.in);
        while (true) {
            System.out.println("Send message:");
            var message = reader.nextLine();
                rabbit.convertAndSend("spring-boot-exchange", "foo.bar.baz", message);
                receiver.Latch.await(10000, TimeUnit.MILLISECONDS);
        }
    }
}
