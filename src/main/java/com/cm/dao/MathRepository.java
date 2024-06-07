package com.cm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MathRepository extends JpaRepository<MathExerciseScore, Long> {
    List<MathExerciseScore> findTop10ByUserIdOrderByDateRecordedDesc(Long userId);
}
