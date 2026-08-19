package com.zion.pomodorozion;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PomodoroSessionsRepository extends JpaRepository<PomodoroSession, Long> {

    List<PomodoroSession> findTop20ByOrderByCompletedAtDesc();

    Long sumDurationSecondsByCompletedAtAfter(Instant date);

    long countByCompletedAtAfter(Instant date);
}
