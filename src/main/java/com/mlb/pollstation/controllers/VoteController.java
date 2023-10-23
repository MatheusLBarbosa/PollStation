package com.mlb.pollstation.controllers;

import com.mlb.pollstation.dto.request.VoteRequestDTO;
import com.mlb.pollstation.dto.response.VoteResponseDTO;
import com.mlb.pollstation.services.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/vote")
public class VoteController {

    private final VoteService service;
    @PostMapping
    public ResponseEntity<VoteResponseDTO> startSession(@RequestBody @Validated VoteRequestDTO request) {
        return new ResponseEntity<>(service.voteInIssue(request), HttpStatus.CREATED);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<Object> sessionDetails(@PathVariable(value = "sessionId", required = true) Long sessionId) {
        return new ResponseEntity<>(service.countingOfVotes(sessionId), HttpStatus.OK);
    }
	
}
