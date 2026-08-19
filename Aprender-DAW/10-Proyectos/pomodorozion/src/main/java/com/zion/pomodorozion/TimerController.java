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
    private final TimerBroadcaster broadcaster;

    public TimerController(TimerService timerService, TimerBroadcaster broadcaster) {
        this.timerService = timerService;
        this.broadcaster = broadcaster;
    }

    //GET STATE
    @GetMapping
    public ResponseEntity<TimerState> getState() {
        return ResponseEntity.ok(timerService.getState());
    }

    //START
    @PostMapping("/start")
    public ResponseEntity<TimerState> start() {
        TimerState state = timerService.start();
        broadcaster.broadcast(state);
        return ResponseEntity.ok(state);
    }

    //PAUSE
    @PostMapping("/pause")
    public ResponseEntity<TimerState> pause() {
        TimerState state = timerService.pause();
        broadcaster.broadcast(state);
        return ResponseEntity.ok(state);
    }

    //RESET
    @PostMapping("/reset")
    public ResponseEntity<TimerState> reset() {
        TimerState state = timerService.reset();
        broadcaster.broadcast(state);
        return ResponseEntity.ok(state);
    }

    //FINISH (transición de fase)
    @PostMapping("/finish")
    public ResponseEntity<TimerState> finish() {
        TimerState state = timerService.finish();
        broadcaster.broadcast(state);
        return ResponseEntity.ok(state);
    }

    //SELECT TASK (o deseleccionar con taskId 0)
    @PostMapping("/task/{taskId}")
    public ResponseEntity<TimerState> selectTask(@PathVariable Long taskId) {
        TimerState state = timerService.selectTask(taskId);
        broadcaster.broadcast(state);
        return ResponseEntity.ok(state);
    }
}
