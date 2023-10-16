package br.com.hiago640.todolist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hiago640.todolist.model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {
	
}
