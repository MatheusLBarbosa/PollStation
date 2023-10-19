package com.mlb.pollstation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlb.pollstation.entities.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>{

}
