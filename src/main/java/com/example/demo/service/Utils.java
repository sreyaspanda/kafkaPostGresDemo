package com.example.demo.service;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Utils {

    @Autowired
    MessageRepository messageRepository;

    public void saveMessages(@RequestParam Map<Long, String> messageMap) {
        List<Message> messages = messageMap.entrySet().stream()
                .map(entry -> new Message(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        messageRepository.saveAll(messages);
    }

    public Message findMessage() {
        return messageRepository.findTopByOrderBySerialNumberDesc();
    }

    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }
}
