package com.example.service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class MessageService {
     
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        boolean messageBlank = message.getMessage_text().isBlank();
        boolean messageLength = message.getMessage_text().length() > 255;
       
        Optional<Message> messageOptional = messageRepository.findById(message.getPosted_by());
        System.out.println("This is messageOptional " + messageOptional);
        
        if(messageBlank || messageLength || messageOptional.isEmpty()) {
            return null;
        }
         return messageRepository.save(message);  
    }

    
    public List<Message> getAllMessages() {
       return (List<Message>) messageRepository.findAll();
    }

    public Message getMessageById(Integer message_id) {
        Optional<Message> messageOptional = messageRepository.findById(message_id);
        if(messageOptional.isPresent()){
            Message message = messageOptional.get();
            return message;
        }
        return null;
    }

    public int deleteMessageById(Integer message_id) {
        int rowsAffected = 0;
        Optional<Message> messageOptional = messageRepository.findById(message_id);
        if(messageOptional.isPresent()){
          messageRepository.deleteById(message_id);
          return rowsAffected = 1;
        }
        return rowsAffected;
    }
   public int updateMessageById(Integer message_id, Message message) {
  
    boolean messageBlank = message.getMessage_text().isBlank();
    boolean messageLength = message.getMessage_text().length() > 255;
    Optional<Message> messageOptional = messageRepository.findById(message_id);

    if(messageOptional.isEmpty() || messageBlank || messageLength) {
        return 0;
    }
    messageRepository.save(message);
     return 1;
   }
 
   public List<Message> getAllMessagesAccountID(Integer account_id) { 
    return (List<Message>) messageRepository.findByPostedBy(account_id);
   }
}

