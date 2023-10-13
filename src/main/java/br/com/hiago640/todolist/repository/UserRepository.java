package br.com.hiago640.todolist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hiago640.todolist.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	public User findByUsername(String username);

}
