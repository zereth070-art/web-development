package com.zion.pomodorozion;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PomodoroSessionService {

    private final PomodoroSessionsRepository sessionRepository;
    private final TaskService service;

    public PomodoroSessionService(PomodoroSessionsRepository sessionRepository, TaskService service) {
        this.sessionRepository = sessionRepository;
        this.service = service;
    }

    public void recordSession(TimerPhase phase, long taskId, Instant startedAt, long durationSeconds) {
        PomodoroSession session = new PomodoroSession();
        session.setPhase(phase);
        session.setStartedAt(startedAt);
        session.setDurationSeconds(durationSeconds);

        if (taskId > 0) {
            session.setTaskId(taskId);
            try {
                TaskDTO task = service.getTaskById(taskId);
                session.setTaskTitle(task.getTitle());
            } catch (Exception e) {
            }
        }

        sessionRepository.save(session); // ← FUERA del if
    }

    public List<PomodoroSessionDTO> getRecentSessions() {
        return sessionRepository.findTop20ByOrderByCompletedAtDesc()
        .stream()
        .map(this::mapToDTO)
        .toList();
    }


    public SessionsStatsDTO getTodayStats() {
        Instant startOfDay = LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant();

        long focusSeconds = safeSum(sessionRepository.sumDurationSecondsByCompletedAtAfter(startOfDay));

        long totalSessions = sessionRepository.countByCompletedAtAfter(startOfDay);

        List<PomodoroSession> todaySessions = sessionRepository.findTop20ByOrderByCompletedAtDesc()
        .stream()
        .filter(s -> s.getCompletedAt().isAfter(startOfDay))
        .toList();

        int focusCount = (int) todaySessions.stream()
        .filter((s -> s.getPhase() == TimerPhase.FOCUS))
        .count();

        int breakCounts = (int) todaySessions.stream()        
        .filter(s -> s.getPhase() != TimerPhase.FOCUS)
        .count();

        int breakSeconds = (int) todaySessions.stream()
        .filter(s -> s.getPhase() != TimerPhase.FOCUS)
        .mapToLong(PomodoroSession::getDurationSeconds)
        .sum();

        return new SessionsStatsDTO(focusSeconds, breakSeconds, focusCount, breakCounts);
    }

    private long safeSum(Long value){
        return value != null ? value : 0;
    }

    private PomodoroSessionDTO mapToDTO(PomodoroSession session){
        return new PomodoroSessionDTO(
            session.getId(),
            session.getPhase(),
            session.getTaskId(),
            session.getTaskTitle(),
            session.getDurationSeconds(),
            session.getCompletedAt());
    } 
    

}
