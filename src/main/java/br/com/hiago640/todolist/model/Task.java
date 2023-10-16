package br.com.hiago640.todolist.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "tb_tasks")
@Data
public class Task {

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	private String description;

	@Column(length = 50)
	private String title;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String priority;

	private UUID idUser;

	@CreationTimestamp
	private LocalDateTime createdAt;

	public void setTitle(String title) throws Exception {
		
		if(title.length() > 50)
			throw new Exception("Title must be at least 50 characters");

		this.title = title;
	}
}
