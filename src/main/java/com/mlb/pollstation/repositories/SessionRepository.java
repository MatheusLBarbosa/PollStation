package com.mlb.pollstation.repositories;

import com.mlb.pollstation.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT CASE WHEN COUNT(*) >= 1 THEN true ELSE false END FROM Session s WHERE issueId.id = ?1")
    boolean findSessionByIssueId(Long issueId);
}
