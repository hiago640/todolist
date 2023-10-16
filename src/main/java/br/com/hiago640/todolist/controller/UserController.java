package br.com.hiago640.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.hiago640.todolist.model.User;
import br.com.hiago640.todolist.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/")
	public ResponseEntity create(@RequestBody User user) {

		if (userRepository.findByUsername(user.getUsername()) == null) {
			System.out.println(user.getUsername());

			String passwordHashred = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
			user.setPassword(passwordHashred);

			User save = userRepository.save(user);

			return ResponseEntity.status(HttpStatus.CREATED).body(save);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
		}

	}

}
