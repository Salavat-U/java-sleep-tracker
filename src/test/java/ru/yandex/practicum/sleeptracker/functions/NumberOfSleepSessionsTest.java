package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.app.SleepTrackerApp;
import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.exception.ListSleepSessionsIsEmpty;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberOfSleepSessionsTest {
    private static final int NUMBER_OF_SLEEP_SESSIONS = 13;
    private static final String DESCRIPTION = "Количество сессий сна";
    private static final String FILE_NAME_MAIN = "src/main/resources/sleep_log.txt";
    private NumberOfSleepSessions numberOfSleepSessions;
    private SleepTrackerApp app;

    @BeforeEach
    public void beforeEach() throws IOException, ListSleepSessionsIsEmpty {
        app = new SleepTrackerApp();
        numberOfSleepSessions = new NumberOfSleepSessions();
        app.addSleepSessions(FILE_NAME_MAIN, app);
    }

    @Test
    @DisplayName("Метод возвращает тип переменной Integer")
    public void testShouldGetIntegerTypeResult() {
        //Given
        boolean checkType = false;
        SleepAnalysisResult<?> result = numberOfSleepSessions.apply(app.getSleepList());
        //When
        if (result.getResult() instanceof Integer) {
            checkType = true;
        }
        //Then
        assertTrue(checkType);
    }

    @Test
    @DisplayName("Метод возвращает правильное описание и результат")
    public void testShouldGetDescriptionAndResult() {
        //Given
        SleepAnalysisResult<Integer> result = numberOfSleepSessions.apply(app.getSleepList());
        //When
        String resultDescription = result.getDescription();
        int resultValue = result.getResult();
        //Then
        assertEquals(DESCRIPTION, resultDescription);
        assertEquals(NUMBER_OF_SLEEP_SESSIONS, resultValue);
    }
}
