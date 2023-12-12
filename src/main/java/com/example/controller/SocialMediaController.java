package com.example.controller;

import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

import javax.security.auth.login.FailedLoginException;

import com.example.entity.Account;
import com.example.entity.Message;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
@Component
public class SocialMediaController {

   
    private AccountService accountService;
    private MessageService messageService;
 
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
      this.accountService = accountService;
      this.messageService = messageService;
    }

   @PostMapping("/register")
   public ResponseEntity <Account> createNewAccount(@RequestBody Account account) {

     if(accountService.checkCredentials(account)) {
       Account newAccount = accountService.createNewAccount(account);
       return ResponseEntity.status(HttpStatus.OK).body(newAccount);   
     } else if(!accountService.checkCredentials(account))  {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
     } else {
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        throw new RuntimeException();
     }
   
  }

   @PostMapping("/login")
    public ResponseEntity <Account> loginUser(@RequestBody Account account) throws FailedLoginException {
    if(accountService.loginUser(account) !=null) {
      Account newLogin = accountService.loginUser(account);
      return ResponseEntity.status(HttpStatus.OK).body(newLogin);
    }
    throw new FailedLoginException();
  }

    @PostMapping("/messages")
    public ResponseEntity <Message> createNewMessage(@RequestBody Message message) {
      System.out.println("inside create new message" + message);

      Message newMessage = messageService.createMessage(message);
        if(newMessage !=null) {
            return ResponseEntity.status(HttpStatus.OK).body(newMessage);   
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newMessage);   
        // throw new RuntimeException();

   }


   @GetMapping("/messages")
   public ResponseEntity <List<Message>>getAllMessages() {
    List<Message> allMessages =  messageService.getAllMessages();
      if(allMessages != null) {
        return ResponseEntity.status(HttpStatus.OK).body(allMessages);  
      }
      // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allMessages); 
      throw new RuntimeException();
   }


   @RequestMapping("/messages/{message_id}")
   public ResponseEntity <Message> getMessageById(@PathVariable Integer message_id ) {   
    Message existingMessage = messageService.getMessageById(message_id);
    if(existingMessage != null) {
      return ResponseEntity.status(HttpStatus.OK).body(existingMessage);  
    } 
    return ResponseEntity.status(HttpStatus.OK).body(null);  
   }

   @DeleteMapping("/messages/{message_id}")
   public ResponseEntity <Integer> deleteMessageById(@PathVariable Integer message_id) {

      if(messageService.deleteMessageById(message_id) == 1){
        return ResponseEntity.status(HttpStatus.OK).body(1);  
      }
      return ResponseEntity.status(HttpStatus.OK).body(null);  
   }

   @PatchMapping("/messages/{message_id}")
   public ResponseEntity <Integer> updateMessageById(@PathVariable Integer message_id, @RequestBody Message message){
    if(messageService.updateMessageById(message_id, message) == 1){
      return ResponseEntity.status(HttpStatus.OK).body(1);  
    } 
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);  
    // throw new RuntimeException();
  }
  
 
  @GetMapping("/accounts/{account_id}/messages")
  public ResponseEntity <List <Message>> getAllMessagesAccountID(@PathVariable Integer account_id) {
      List<Message> messageByUser =   messageService.getAllMessagesAccountID(account_id);
    if(messageByUser != null) {
      return ResponseEntity.status(HttpStatus.OK).body(messageByUser); 
    }
    return ResponseEntity.status(HttpStatus.OK).body(messageByUser); 
}


@ExceptionHandler({FailedLoginException.class})
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public ResponseEntity <Account>  invalidCredentials(FailedLoginException ex) {
  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
}

@ExceptionHandler({RuntimeException.class})
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ResponseEntity <Account>  badRequest(BadRequestException ex) {
  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
}

}