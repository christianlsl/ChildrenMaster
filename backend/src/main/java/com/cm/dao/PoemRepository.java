package com.cm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoemRepository extends JpaRepository<PoemExerciseScore, Long> {
    List<PoemExerciseScore> findTop10ByUserIdOrderByDateRecordedDesc(Long userId);
}
