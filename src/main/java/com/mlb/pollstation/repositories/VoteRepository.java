package com.mlb.pollstation.repositories;

import com.mlb.pollstation.dto.response.TotalVotesResponseDTO;
import com.mlb.pollstation.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT CASE WHEN COUNT(*) >= 1 THEN true ELSE false END FROM Vote v WHERE cpf = ?1 AND v.sessionId.issueId.id = ?2")
    boolean verifyIfUserVoted(String cpf, Long issueId);

    @Query(value = "SELECT iss.title, v.session_id as sessionId, COUNT(*) as totalVotes, " +
            "SUM(CASE WHEN v.vote_choice = 0 THEN 1 ELSE 0 END) as yesVotes, SUM(CASE WHEN v.vote_choice = 1 THEN 1 ELSE 0 END) as noVotes " +
            "FROM vote v " +
            "JOIN session s ON s.id = v.session_id " +
            "JOIN issue iss ON iss.id = s.issue_id " +
            "WHERE v.session_id = ?1", nativeQuery = true)
    TotalVotesResponseDTO getSessionVoteDetails(Long sessionId);
}
