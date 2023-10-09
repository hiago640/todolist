package br.com.hiago640.todolist.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiago640.todolist.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

	@PostMapping("/")
	public void create(@RequestBody User user) {
		System.out.println(user.getName());
	}
	
}
