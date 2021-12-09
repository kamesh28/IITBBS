package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.smart.dao.ModuleRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Module;
import com.smart.entities.User;

@RestController
public class SearchController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModuleRepository moduleRepository;

	
	//search handler  //Principal is used for current user
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query ,Principal principal)
	{
		System.out.println(query);
		
		User user = userRepository.getUserByUserName(principal.getName());
		List<Module> modules =moduleRepository.findByNameContainingAndUser(query, user);
		
		return ResponseEntity.ok(modules);
	}
}
