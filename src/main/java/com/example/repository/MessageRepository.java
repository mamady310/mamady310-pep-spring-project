package com.example.repository;

import com.example.entity.Message;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{

    @Query("SELECT u FROM Message u where u.posted_by = :account_id")
    List<Message> findByPostedBy(@Param("account_id") Integer account_id);

 
    
    
   
}
