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

		UUID idUser = (UUID) request.getAttribute("idUser");
		task.setIdUser(idUser);

		LocalDateTime currentDate = LocalDateTime.now();

		System.out.println(currentDate);
		System.out.println(task.getStartAt());
		System.out.println(task.getEndAt());

		if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A Data de Inicio / Data de Término deve ser maior que a Data Atual.");
		}

		if (task.getStartAt().isAfter(task.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A Data de Inicio deve ser menor que a Data de Término.");
		}

		Task save = taskRepository.save(task);

		System.out.println(save.getTitle());

		return ResponseEntity.status(HttpStatus.OK).body(task);
	}

	@GetMapping("/")
	public List<Task> getTasksUsuario(HttpServletRequest request) {

		UUID idUser = UUID.fromString(request.getParameter("idUser"));

		return taskRepository.findByIdUser(idUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity updateTask(@RequestBody Task task, @PathVariable UUID id, HttpServletRequest request) {

		Task taskById = taskRepository.findById(id).orElse(null);

		if (taskById == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Tarefa não encontrada");

		UUID userID = (UUID) request.getAttribute("idUser");

		if (userID.equals(taskById.getIdUser()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Usuário não tem permissão para alterar esta tarefa");

		Utils.copyNonNullProperties(task, taskById);

		Task taskUpdated = taskRepository.save(taskById);

		return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
	}

}
