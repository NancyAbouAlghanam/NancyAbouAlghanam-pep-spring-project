package com.example.repository;

import com.example.entity.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional <Account> findByUsername(String username);

    Optional<Account> findByUsernameAndPassword(String username, String password);
}

/**
 * AccountRepository is an interface that extends JpaRepository,
 * providing methods for performing CRUD (Create, Read, Update, Delete) operations on Account entities.
 */
    /**
     * Finds an Account by its username and password.
     *
     * The username of the account to search for.
     * password The password associated with the account.
     * return an Optional containing the Account if the username and password match, or an empty Optional if not.
     */