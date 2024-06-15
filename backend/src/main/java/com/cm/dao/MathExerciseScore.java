package com.cm.dao;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import static jakarta.persistence.GenerationType.IDENTITY;
@Entity
@Table(name = "math_exercise_scores")
public class MathExerciseScore {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "correct_rate")
    private Float correctRate;

    @Column(name = "date_recorded")
    private LocalDateTime dateRecorded;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Float getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(Float correctRate) {
        this.correctRate = correctRate;
    }

    public LocalDateTime getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(LocalDateTime dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
}
