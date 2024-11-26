package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserClass;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("signup")
	public ResponseEntity<UserClass> addUser(@RequestBody UserClass user){
		return new ResponseEntity<UserClass>(userService.adduser(user),HttpStatus.ACCEPTED);
	}
	
//	@GetMapping
//	public ResponseEntity<List<UserClass>> getAllUsers(){
//		return new ResponseEntity<List<UserClass>>(userService.getAllUsers(),HttpStatus.OK);
//	}
	
	@PostMapping("signin")
	public ResponseEntity<UserClass> loginUser(@RequestBody UserClass userClass){
		return new ResponseEntity<UserClass>(userService.login(userClass),HttpStatus.OK);
	}
	
	@GetMapping("{uid}")
	public ResponseEntity<UserClass> getUserPerId(@PathVariable int uid){
//		System.out.println("From shells"+uid);
		return new ResponseEntity<UserClass>(userService.getUserPerId(uid),HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<UserClass> updateUser(@RequestBody UserClass user){
		return new ResponseEntity<UserClass>(userService.updateUser(user),HttpStatus.OK);
	}
	
}
