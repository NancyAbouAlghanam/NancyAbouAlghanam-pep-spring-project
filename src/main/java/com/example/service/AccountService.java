package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepo;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired // Constructor-based dependency injection for the AccountRepository
    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account getAccountById(int accountId) { //This method retrieves an account by its ID.If the account does not exist, it returns null.
        return accountRepo.findById(accountId).orElse(null); // Returning null if account not found
    }

    public Optional<Account> authenticateAccount(Account account) { //This method authenticates an account by checking the username and password.It returns an Optional to safely handle cases where the account is not found.
        return accountRepo.findByUsernameAndPassword(account.getUsername(), account.getPassword()); // Using Optional to safely handle null responses
    }

    public Account createAccount(Account newAccount) throws DuplicateAccountException {
        // Validate the account data
        if (newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4) {
            return null;  // Return null if the username is blank or the password is too short
        }

        // Check if an account with the same username already exists
        Optional<Account> existingAccount = accountRepo.findByUsername(newAccount.getUsername());
        if (existingAccount.isPresent()) {
            throw new DuplicateAccountException();  // If account exists, throw an exception
        }
    
        return accountRepo.save(newAccount); // Save the new account to the database, Save and return the newly created account
    }

    public boolean doesAccountExist(int accountId) { //This method checks if an account exists based on the provided account ID.
        return accountRepository.existsById(accountId); 
    }
}