package com.zion.pomodorozion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
public class PomodoroSessionsController {
    
    private final PomodoroSessionService sessionService;

    public PomodoroSessionsController(PomodoroSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PomodoroSessionDTO>> getRecentSessions(){
        return ResponseEntity.ok(sessionService.getRecentSessions());
    }

    @GetMapping("/today")
    public ResponseEntity<SessionsStatsDTO> getTodayStats() {
        return ResponseEntity.ok(sessionService.getTodayStats());
    }
}
