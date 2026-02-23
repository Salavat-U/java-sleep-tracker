package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.app.SleepTrackerApp;
import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.data.SleepingSession;
import ru.yandex.practicum.sleeptracker.exception.ListSleepSessionsIsEmpty;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberOfSleeplessNightsTest {
    private static final int NUMBER_SLEEPLESS_NIGHTS = 20;
    private static final int TOTAL_NUMBER_OF_NIGHTS = 30;
    private static final String TEXT_SLEEPING_SESSION = "01.10.25 23:15;02.10.25 07:15;GOOD";
    private static final String DESCRIPTION = "Количество дней бессонных ночей";
    private static final String FILE_NAME_MAIN = "src/main/resources/sleep_log.txt";
    private static final String MIDNIGHT = "01:00";
    private NumberOfSleeplessNights sleeplessNights;
    private SleepTrackerApp app;

    @BeforeEach
    public void beforeEach() throws IOException, ListSleepSessionsIsEmpty {
        app = new SleepTrackerApp();
        sleeplessNights = new NumberOfSleeplessNights();
        app.addSleepSessions(FILE_NAME_MAIN, app);
    }

    @Test
    @DisplayName("Метод возвращает тип переменной Integer")
    public void testShouldGetIntegerTypeResult() {
        //Given
        boolean checkType = false;
        SleepAnalysisResult<?> result = sleeplessNights.apply(app.getSleepList());
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
        SleepAnalysisResult<Integer> result = sleeplessNights.apply(app.getSleepList());
        //When
        String resultDescription = result.getDescription();
        int resultValue = result.getResult();
        //Then
        assertEquals(DESCRIPTION, resultDescription);
        assertEquals(NUMBER_SLEEPLESS_NIGHTS, resultValue);
    }

    @Test
    @DisplayName("Правильно определяет время после полночи и до 6 утра")
    public void testShouldGetMidnight() {
        //Given
        LocalTime midnightTime = LocalTime.parse(MIDNIGHT);
        //When
        boolean checkMidnight = sleeplessNights.isMidnight(midnightTime);
        //Then
        assertTrue(checkMidnight);
    }

    @Test
    @DisplayName("Правильно возвращает общее количество ночей")
    public void testShouldGetTotalNumberOfNights() {
        //Given
        List<SleepingSession> sleepSessions = app.getSleepList();
        //When
        int totalNumberOfNights = sleeplessNights.getTotalNumberNights(sleepSessions);
        //Then
        assertEquals(TOTAL_NUMBER_OF_NIGHTS, totalNumberOfNights);
    }

    @Test
    @DisplayName("Правильно определяет сон ночью")
    public void testGetNightsSessions(){
        //Given
        SleepingSession nightTime = new SleepingSession(TEXT_SLEEPING_SESSION);
        //When
        boolean checkMidnight = sleeplessNights.isNightsSessions(nightTime);
        //Then
        assertTrue(checkMidnight);
    }
}
