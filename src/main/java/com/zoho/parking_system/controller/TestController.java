package com.zoho.parking_system.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*")
public class TestController {

	@GetMapping("/healthcheck")
	public ResponseEntity<?> hello() {
		
		return new ResponseEntity<String>(new Date().toString(), HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public ResponseEntity<?> test(){
		return new ResponseEntity<String>(new Date().toString() +" Success", HttpStatus.OK);
	}
}
