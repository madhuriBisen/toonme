package com.app.toonme.filter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.toonme.filter.model.App_User_Details;

public interface AppUserDetailsRepository extends JpaRepository<App_User_Details, Long>{
	 
    @Query("SELECT c FROM App_User_Details c WHERE c.username = :username")
      App_User_Details findByUsername(String username); 
    
    @Query("SELECT c FROM App_User_Details c WHERE c.username = :username and c.password = :password")
    App_User_Details findByUsernameAndPassword(String username,String password); 
}
