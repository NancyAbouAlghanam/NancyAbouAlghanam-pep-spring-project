package com.example.controller;

import com.example.exception.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.exception.InvalidAccountException;

import java.util.List;
import java.util.Optional;

/**
 * DONE: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            Account result = accountService.createAccount(account);
            return ResponseEntity.status(200).body(result); // Return status 200 with the created account
        } catch (DuplicateAccountException e) {
            return ResponseEntity.status(409).body(null); // Return 409 if account is duplicated
        } catch (InvalidAccountException e) {
            return ResponseEntity.status(400).body(null); // Return 400 for invalid account data
        }
    }

    // Get account -> Verify user login -> POST localhost:8080/login

    @PostMapping("/login")
    public ResponseEntity<Optional<Account>> loginAccount(@RequestBody Account account){
        Optional<Account> result = accountService.authenticateAccount(account);
        if(result.isEmpty()){
            return ResponseEntity.status(401).body(null); // Return 401 if account not found or credentials are invalid
        }else{
            return ResponseEntity.status(200).body(result); // Return 200 if login is successful
        }
    }

    // Create new message -> POST localhost:8080/messages

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(createdMessage); 
            } catch (InvalidMessageException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    }

    // Get all messages -> GET localhost:8080/messages

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages); // Return status 200 with all messages
    }

    // Get message by its ID -> GET localhost:8080/messages/{messageId}

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable("messageId") int messageId){
        Message message = messageService.getMessage(messageId);
        if(message == null){
            return ResponseEntity.status(HttpStatus.OK).body(null); // Return empty body if message not found
        }
        return ResponseEntity.status(HttpStatus.OK).body(message); // Return the found message
    }

    // Delete message by its ID -> DELETE localhost:8080/messages/{messageId}

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Integer messageId) {
        boolean isDeleted = messageService.deleteMessage(messageId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("1");  // Return 200 with "1" if message was deleted successfully
        }
        return ResponseEntity.status(HttpStatus.OK).body("");  // Return 200 with empty body if message wasn't found
    }

    // Update message text by its ID -> PATCH localhost:8080/messages/{messageId}

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable("messageId") int messageId, @RequestBody Message update){

        boolean isUpdated = messageService.updateMessage(messageId, update.getMessageText());
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(1);  // Return 1 if update is successful
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Return 400 if update fails due to invalid data
        }
    }

    // Get all messages from a particular user -> GET localhost:8080/accounts/{accountId}/messages

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromUser(@PathVariable("accountId") int accountId){
        List<Message> messages = messageService.getMessagesByUser(accountId);
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(messages); // Return empty list if no messages found
        }
        return ResponseEntity.status(HttpStatus.OK).body(messages);  // Return the list of messages if found
    }
}