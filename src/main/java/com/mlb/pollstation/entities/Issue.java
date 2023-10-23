package com.mlb.pollstation.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "issue")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	private Long id;
	@Column(name = "title")
	private String title;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "owner")
	private String owner;
	@Column(name = "total_votes")
	private Integer totalVotes;
	@OneToOne(mappedBy = "issueId")
	private Session session;

}
