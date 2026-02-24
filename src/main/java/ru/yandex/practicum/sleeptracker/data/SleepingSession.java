package ru.yandex.practicum.sleeptracker.data;

import ru.yandex.practicum.sleeptracker.types.AssessmentType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SleepingSession {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final AssessmentType assessmentType;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepingSession(String line) {
        String[] arrayLine = line.split(";");
        this.startTime = LocalDateTime.parse(arrayLine[0], FORMATTER);
        this.endTime = LocalDateTime.parse(arrayLine[1], FORMATTER);
        this.assessmentType = AssessmentType.valueOf(arrayLine[2]);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SleepingSession that = (SleepingSession) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                assessmentType == that.assessmentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, assessmentType);
    }
}
