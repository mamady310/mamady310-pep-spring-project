package com.example.service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class AccountService {

  
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    
    public boolean checkCredentials(Account account) {
        Account checkUser = accountRepository.findByUsername(account.getUsername());
        boolean userNameBlank = account.getUsername().isBlank();
        boolean userPwLength = account.getPassword().length() < 4;
        
      if(checkUser == null || userNameBlank || userPwLength) {
        return true;
      }
      return false;  
    }

    public Account createNewAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account loginUser(Account account) {
      String user = account.getUsername();
      String pw = account.getPassword();
      
      return accountRepository.findByUsernameAndPassword(user,pw);
    }

}
