package com.mlb.pollstation.controllers;

import com.mlb.pollstation.dto.response.SessionResponseDTO;
import com.mlb.pollstation.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/session")
public class SessionController {
    private final SessionService service;
    @PostMapping("/{issueId}")
    public ResponseEntity<SessionResponseDTO> startSession(@PathVariable(value = "issueId", required = true) Long issueId) {
        return new ResponseEntity<>(service.startSession(issueId), HttpStatus.CREATED);
    }
	
}
