package com.mlb.pollstation.entities;

import java.time.LocalDateTime;
import java.util.Collection;

import com.mlb.pollstation.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "session")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	private Long id;
	@OneToOne()
	@JoinColumn(name = "issue_id")
	private Issue issueId;
	@Column(name = "started_at", nullable = false)
	private LocalDateTime startAt = LocalDateTime.now();
	@Column(name = "finished_at", nullable = false)
	private LocalDateTime finishedAt = this.startAt.plusMinutes(1);
	@Enumerated(EnumType.STRING)
	@Column(name = "session_status", nullable = false)
	private SessionStatus sessionStatus;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
	private Collection<Vote> votes;
}
