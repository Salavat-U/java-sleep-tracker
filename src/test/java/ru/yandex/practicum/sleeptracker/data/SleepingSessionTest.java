package ru.yandex.practicum.sleeptracker.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.app.SleepTrackerApp;
import ru.yandex.practicum.sleeptracker.exception.ListSleepSessionsIsEmpty;
import ru.yandex.practicum.sleeptracker.types.AssessmentType;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepingSessionTest {
    private static final String FILE_NAME_TEST = "src/test/resources/sleep_log_test.txt";
    private static final String LOCAL_DATE_TIME_START = "01.10.25 23:15";
    private static final String LOCAL_DATE_TIME_END = "02.10.25 07:30";
    private static final AssessmentType ASSESSMENT_TYPE = AssessmentType.GOOD;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private SleepTrackerApp app;

    @BeforeEach
    public void beforeEach() {
        app = new SleepTrackerApp();
    }

    @Test
    @DisplayName("Метод разделяет строку на переменные")
    public void testShouldSplitStringByVariables() throws IOException, ListSleepSessionsIsEmpty {
        //Given
        Path path = Paths.get(FILE_NAME_TEST);
        try (Writer fileWriter = Files.newBufferedWriter(path)) {
            fileWriter.write(LOCAL_DATE_TIME_START + ";" + LOCAL_DATE_TIME_END + ";" + ASSESSMENT_TYPE);
        }
        app.addSleepSessions(FILE_NAME_TEST, app);
        //When
        List<SleepingSession> sessionList = app.getSleepList();

        LocalDateTime timeStart = sessionList.getFirst().getStartTime();
        LocalDateTime timeEnd = sessionList.getFirst().getEndTime();
        AssessmentType assessmentType = sessionList.getFirst().getAssessmentType();
        //Then
        assertEquals(LOCAL_DATE_TIME_START, timeStart.format(FORMATTER));
        assertEquals(LOCAL_DATE_TIME_END, timeEnd.format(FORMATTER));
        assertEquals(ASSESSMENT_TYPE, assessmentType);
    }
}
