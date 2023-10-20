package com.mlb.pollstation.repositories;

import com.mlb.pollstation.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long>{

}
