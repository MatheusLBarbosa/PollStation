package com.mlb.pollstation.entities;

import com.mlb.pollstation.enums.VoteChoice;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "vote")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;
    @Column(name = "cpf_associate", nullable = false)
    private String cpf;
    @Enumerated
    @Column(name = "vote_choice", nullable = false)
    private VoteChoice voteChoice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
}
