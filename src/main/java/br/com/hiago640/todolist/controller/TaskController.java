package br.com.hiago640.todolist.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiago640.todolist.model.Task;
import br.com.hiago640.todolist.repository.TaskRepository;
import br.com.hiago640.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@PostMapping("/")
	public ResponseEntity createTask(@RequestBody Task task, HttpServletRequest request) {
		task.setIdUser(UUID.fromString(request.getParameter("idUser")));

		LocalDateTime currentDate = LocalDateTime.now();
		if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
					.body("A Data de Inicio / Data de Término deve ser maior que a Data Atual.");
		}

		if (task.getStartAt().isAfter(task.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
					.body("A Data de Inicio deve ser menor que a Data de Término.");
		}

		Task save = taskRepository.save(task);

		System.out.println(save.getTitle());

		return ResponseEntity.status(HttpStatus.OK.value()).body(task);
	}

	@GetMapping("/")
	public List<Task> getTasksUsuario(HttpServletRequest request) {

		UUID idUser = UUID.fromString(request.getParameter("idUser"));

		return taskRepository.findByIdUser(idUser);
	}

	@PutMapping("/{id}")
	public Task updateTask(@RequestBody Task task, @PathVariable UUID id, HttpServletRequest request) {

		Task taskById = taskRepository.findById(id).orElse(null);

		Utils.copyNonNullProperties(task, taskById);

		return taskRepository.save(taskById);
	}

}
