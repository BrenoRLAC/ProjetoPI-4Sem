package com.projeto.watchflix.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VIDEO_STATUS")
public class VideoStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "STATUS_NAME")
	private String statusName;

	@Column(name = "ACTIVE")
	private boolean active;

}