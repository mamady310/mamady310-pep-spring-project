package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Message;

import antlr.collections.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>  {
 
  
   @Query("SELECT u FROM Account u where u.username = :username")
    Account findByUsername(@Param("username") String username);
    

   @Query("SELECT u FROM Account u WHERE u.username= :username AND u.password = :password")
   Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
