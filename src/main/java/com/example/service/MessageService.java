package com.example.service;
import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountService accountService;

    public Message createMessage(Message message)  throws InvalidMessageException {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) { // Check if the message text is null or empty
            throw new InvalidMessageException("Message text cannot be blank");
        }

        if (message.getMessageText().length() > 255) { // Check if the message text exceeds the maximum allowed length (255 characters)
            throw new InvalidMessageException("Message text exceeds maximum length of 255 characters");
        }

        boolean accountExists = accountService.doesAccountExist(message.getPostedBy()); // Ensure the user who posted the message exists in the database
        if (!accountExists) {
            throw new InvalidMessageException("User not found in the database");
        }
        
        return messageRepository.save(message);  // Save the message if it passes validation
    }

    //return a list of all messages from the database.

    public List<Message> fetchMessages() {
        return messageRepository.findAll();  
    }

    public Message getMessage(int id){
        Optional<Message> optionalMessage = messageRepository.findById(id);  // Using findById to avoid null checks
        return optionalMessage.orElse(null);  // Return the message if it exists, or null if not
    }

    /**
     * This method updates an existing message.
     * It checks the validity of the new message text (non-empty and within a length range).
     * 
     * messageId the ID of the message to update.
     * newMessageText the new text to update the message with.
     * return true if the update was successful, false if the message is invalid or not found.
     */

    public boolean updateMessage(Integer messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.trim().isEmpty()) {
            return false;  
        }

        if (newMessageText.length() > 500) { // Check if the message text exceeds the maximum allowed length (500 characters)
            return false;  // If the message exceeds 500 characters, return false
        }

        if (newMessageText.length() > 255) { // Check if the message text is within the allowed range (255 characters)
            return false;  // If the message exceeds 255 characters, return false
        }

        Optional<Message> messageOptional = messageRepository.findById(messageId); // Retrieve the message from the repository using the provided ID
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setMessageText(newMessageText);  
            messageRepository.save(message);  
            return true;
        } else {
            return false;  
        }
    }

    /**
     * This method deletes a message by its ID.
     * It returns true if the message was successfully deleted, and false if the message wasn't found.
     * 
     * param messageId the ID of the message to delete.
     * return true if the message was deleted, false if the message was not found.
     */

    public boolean deleteMessage(Integer messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
        messageRepository.deleteById(messageId); // Delete the message if it exists
        return true; // Return true if the deletion was successful
     }
     return false;  // Return false if the message wasn't found
    }

    /**
     * This method retrieves all messages from the database.
     * 
     * return a list of all messages.
     */

    public List<Message> getAllMessages() {
        return messageRepository.findAll(); 
    }

    /**
     * This method retrieves all messages posted by a specific user (identified by their account ID).
     * If no messages are found, it returns an empty list.
     */

    public List<Message> getMessagesByUser(Integer accountId) {
        List<Message> messages = messageRepository.findByPostedBy(accountId);  // Retrieve messages by user account ID
        return messages != null ? messages : new ArrayList<>();  // Return the messages, or an empty list if none are found
    }

    // Ensure this sample message is in the database for testing purposes
    public void addSampleMessages() {
        Message message = new Message(999, 9999, "test message 1", 1669947792L);
        messageRepository.save(message); // Save the sample message
    }
}