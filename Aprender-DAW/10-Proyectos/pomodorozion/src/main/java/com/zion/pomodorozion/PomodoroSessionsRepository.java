package com.zion.pomodorozion;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PomodoroSessionsRepository extends JpaRepository<PomodoroSession, Long> {

    List<PomodoroSession> findTop20ByOrderByCompletedAtDesc();

  @Query("""
        select coalesce(sum(s.durationSeconds), 0)
        from PomodoroSession s
        where s.completedAt > :date
        """)
long sumDurationSecondsByCompletedAtAfter(@Param("date") Instant date);

    long countByCompletedAtAfter(Instant date);
}
