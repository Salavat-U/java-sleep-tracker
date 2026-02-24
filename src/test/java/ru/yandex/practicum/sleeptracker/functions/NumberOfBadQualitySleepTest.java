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

public class NumberOfBadQualitySleepTest {
    private static final int NUMBER_OF_BAD_SLEEP_SESSIONS = 2;
    private static final String DESCRIPTION = "Количество сессий с плохим качеством сна";
    private static final String FILE_NAME_MAIN = "src/main/resources/sleep_log.txt";
    private NumberOfBadQualitySleep badQualitySleep;
    private SleepTrackerApp app;

    @BeforeEach
    public void beforeEach() throws IOException, ListSleepSessionsIsEmpty {
        app = new SleepTrackerApp();
        badQualitySleep = new NumberOfBadQualitySleep();
        app.addSleepSessions(FILE_NAME_MAIN, app);
    }

    @Test
    @DisplayName("Метод возвращает тип переменной Integer")
    public void testShouldGetIntegerTypeResult() {
        //Given
        boolean checkType = false;
        SleepAnalysisResult<?> result = badQualitySleep.apply(app.getSleepList());
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
        SleepAnalysisResult<Integer> result = badQualitySleep.apply(app.getSleepList());
        //When
        String resultDescription = result.getDescription();
        int resultValue = result.getResult();
        //Then
        assertEquals(DESCRIPTION, resultDescription);
        assertEquals(NUMBER_OF_BAD_SLEEP_SESSIONS, resultValue);
    }
}
