package com.mlb.pollstation.repositories;

import com.mlb.pollstation.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT CASE WHEN COUNT(*) > 1 THEN true ELSE false END FROM Vote v WHERE cpf = ?1 AND v.session.issueId.id = ?2")
    boolean verifyIfUserVoted(String cpf, Long issueId);
}
