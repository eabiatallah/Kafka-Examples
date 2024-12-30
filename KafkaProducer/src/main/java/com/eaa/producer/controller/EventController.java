package com.eaa.producer.controller;

import com.eaa.producer.dto.Customer;
import com.eaa.producer.dto.Greeting;
import com.eaa.producer.publisher.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;

//    @GetMapping("/publish/{message}")
//    public ResponseEntity<?> publishMessage(@PathVariable String message) {
//        try {
//            // Thread.sleep(5000); // if you want to check how lap works. While publishing messages stop this server
//            for (int i = 0; i <= 10; i++) {
//                publisher.sendEvents(message + " : " + i);
//            }
//            return ResponseEntity.ok("message published successfully ...");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/send/{message}")
    public ResponseEntity<?> sendMessage(@PathVariable String message) {
        try {
            publisher.sendMessage(message);
            return ResponseEntity.ok("message published successfully ...");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/publish")
    public void sendEvents(@RequestBody Greeting greeting) {
        publisher.sendEventsToTopic(greeting);
    }


}
