package com.eaa.kafka_binder_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class KafkaBinderDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaBinderDemoApplication.class, args);
    }

    // (Publisher) Producer will send data to Topic, check yml config.
    // In cases where the Supplier should be triggered by an external event, the StreamBridge helper class can be used.
    // For example, if a msg should be published to the processor when Rest API is called.
    @Bean
    public Supplier<String> producerBinding() {
        return () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "new data";
        };
    }


    // (Destination) Processor will fetch data from one topic perform it is logic and then send new/modified data to other topic.
    @Bean
    public Function<String, String> processorBinding() {
        return s -> s + " :: " + System.currentTimeMillis();
    }

    // (Subscriber) will fetch data from topic
    @Bean
    public Consumer<String> consumerBinding() {
        return s -> System.out.println("Data Consumed :: " + s.toUpperCase());
    }

}
