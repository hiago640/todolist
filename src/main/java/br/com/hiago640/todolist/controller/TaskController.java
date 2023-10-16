package br.com.hiago640.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiago640.todolist.model.Task;
import br.com.hiago640.todolist.repository.TaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepository;

	@PostMapping("/")
	public void createTask(@RequestBody Task task) {
		Task save = taskRepository.save(task);

		System.out.println(save.getTitle());
	}

}
