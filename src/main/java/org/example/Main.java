package org.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import java.util.UUID;

@SpringBootApplication
public class Main {
/*    private String fanoutExchangeName = "spring-boot-exchange";;*/
    private String queue =  "spring-boot"+ UUID.randomUUID();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args).close();
    }

    @Bean
    protected Queue registerQueue() {
            return new Queue(queue, false, true, true);
    }

    @Bean
    protected FanoutExchange registerExchange() {
        return new FanoutExchange("spring-boot-exchange");
    }

    @Bean
    protected Binding registerBinding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    protected MessageListenerAdapter registerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receive");
    }

    @Bean
    protected SimpleMessageListenerContainer registerContainer(ConnectionFactory factory, MessageListenerAdapter adapter) {
        var cont = new SimpleMessageListenerContainer(factory);
        cont.setQueueNames(queue);
        cont.setMessageListener(adapter);
        return cont;
    }
}