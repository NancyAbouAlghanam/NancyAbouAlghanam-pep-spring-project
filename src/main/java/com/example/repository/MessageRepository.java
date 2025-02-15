package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<Message> findById(Integer id);
    List<Message> findByPostedBy(Integer accountId); 
}

/**
 * MessageRepository is an interface that extends JpaRepository,
 * providing methods for performing CRUD (Create, Read, Update, Delete) operations on Message entities.
 */
    /**
     * Finds a list of Messages posted by a specific account.
     *
     * The ID of the account who posted the messages.
     * return a List of Messages posted by the given account ID.
     */

    /**
     * Finds a Message by its ID.
     *
     * The ID of the message to search for.
     * return an Optional containing the Message if found, or an empty Optional if not.
     */