package com.app.toonme.filter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.toonme.filter.exception.UserNotFoundException;
import com.app.toonme.filter.model.App_User_Details;
import com.app.toonme.filter.repository.AppUserDetailsRepository;

@RestController
public class UserController {

	@Autowired
	private AppUserDetailsRepository appUserDetailsRepository;

	@PostMapping("/signup")
	public String processRegister(@RequestBody App_User_Details user) {
		System.out.println("user.getUsername() : " + user.getUsername());
		appUserDetailsRepository.save(user);
		return user.getUsername() + " registered";
	}
	
	@GetMapping("/login")
	public HttpStatus login(@RequestBody App_User_Details user) {
		App_User_Details tempUser = appUserDetailsRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        if (tempUser != null) {
        	return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.UNAUTHORIZED;
        }
	}

	@PutMapping("/resetpassword")
	public String updatePassword(@RequestBody App_User_Details user) {
		App_User_Details tempUser = appUserDetailsRepository.findByUsername(user.getUsername());
        if (tempUser != null) {
        	tempUser.setPassword(user.getPassword());
        	appUserDetailsRepository.save(tempUser);
        } else {
            throw new UserNotFoundException("Could not find any user with the username " + user.getUsername());
        }
        return "Password updated.";
	}

}
