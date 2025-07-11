package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.KafkaProducerService;
import com.example.demo.service.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KafkaController {
    @Autowired
    Utils utils;

    private final KafkaProducerService kafkaProducerService;
    private static int counter = 0;
    Map<Long, String> messageMap = new HashMap<>();

    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        Message lastMessage = utils.findMessage();
        long lastSerialNumber = lastMessage != null ? lastMessage.getSerialNumber() : 0L;
        messageMap.put(lastSerialNumber + 1L, message);
        counter++;
        kafkaProducerService.sendMessage(message);
        if (counter % 3 == 0) {
            utils.saveMessages(messageMap);
            messageMap.clear();
            return "Message sent successfully and saved in database";
        }
        return "Message sent successfully";
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return utils.findAllMessages();
    }

}