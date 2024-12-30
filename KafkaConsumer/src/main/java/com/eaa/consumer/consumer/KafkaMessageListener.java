package com.eaa.consumer.consumer;

import com.eaa.consumer.dto.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaMessageListener {

   @KafkaListener(topics = "Topic-2", groupId = "jt-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupJt(String message) {
       System.out.println("Received Message in group jt: "+message);
   }

    @KafkaListener(topics = "javatechie-demo", groupId = "jt-group", containerFactory = "greetingKafkaListenerContainerFactory")
    public void greetingListener(Greeting greeting) {
        System.out.println("Received Greeting Message in group jt: "+greeting.getMsg());
    }

}
