package com.projeto.watchflix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "ID")
	private long commentId;
	@Column(name = "TEXT")
	private String text;
	@ManyToOne
	@JoinColumn(name = "VIDEO_ID", referencedColumnName = "ID")
	private Video video;
	@Column(name = "LIKE_COUNT")
	private Integer likeCount;
	@Column(name = "DISLIKE_COUNT")
	private Integer dislikeCount;

}
