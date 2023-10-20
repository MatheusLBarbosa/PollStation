package com.mlb.pollstation.repositories;

import com.mlb.pollstation.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
