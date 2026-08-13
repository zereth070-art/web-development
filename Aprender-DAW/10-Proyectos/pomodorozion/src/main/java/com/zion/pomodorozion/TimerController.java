package com.zion.pomodorozion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timer")
public class TimerController {

    private final TimerService timerService;

    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }

    //GET STATE
    @GetMapping
    public ResponseEntity<TimerState> getState() {
        return ResponseEntity.ok(timerService.getState());
    }

    //START
    @PostMapping("/start")
    public ResponseEntity<TimerState> start() {
        return ResponseEntity.ok(timerService.start());
    }

    //PAUSE
    @PostMapping("/pause")
    public ResponseEntity<TimerState> pause() {
        return ResponseEntity.ok(timerService.pause());
    }

    //RESET
    @PostMapping("/reset")
    public ResponseEntity<TimerState> reset() {
        return ResponseEntity.ok(timerService.reset());
    }

    //FINISH (transición de fase)
    @PostMapping("/finish")
    public ResponseEntity<TimerState> finish() {
        return ResponseEntity.ok(timerService.finish());
    }

    //SELECT TASK (o deseleccionar con taskId 0)
    @PostMapping("/task/{taskId}")
    public ResponseEntity<TimerState> selectTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(timerService.selectTask(taskId));
    }
}
