package ru.yandex.practicum.sleeptracker.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.app.SleepTrackerApp;
import ru.yandex.practicum.sleeptracker.exception.ListSleepSessionsIsEmpty;
import ru.yandex.practicum.sleeptracker.functions.AverageSessionDuration;
import ru.yandex.practicum.sleeptracker.functions.UserClassificationBySleep;
import ru.yandex.practicum.sleeptracker.types.TypesUsers;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepAnalysisResultTest {
    private static final String TEXT_SLEEPING_SESSION = "01.10.25 23:15;01.10.25 23:15;GOOD";
    private static final String EXPECTED_VALUE_ZERO = "Средняя продолжительность сна (в минутах): результат не найден.";
    private static final String EXPECTED_TYPE_USER = "Тип пользователя: Голубь.";
    private static final String FILE_NAME_TEST = "src/test/resources/sleep_log_test.txt";
    private static final String FILE_NAME_MAIN = "src/main/resources/sleep_log.txt";
    private SleepTrackerApp app;

    @BeforeEach
    public void beforeEach() {
        app = new SleepTrackerApp();
    }

    @Test
    @DisplayName("Правильная обработка результата равного 0")
    public void testShouldGetCorrectOutput() throws IOException, ListSleepSessionsIsEmpty {
        //Given
        AverageSessionDuration average = new AverageSessionDuration();
        Path path = Paths.get(FILE_NAME_TEST);
        try (Writer fileWriter = Files.newBufferedWriter(path)) {
            fileWriter.write(TEXT_SLEEPING_SESSION);
        }
        app.addSleepSessions(FILE_NAME_TEST, app);
        //When
        SleepAnalysisResult<Integer> sleepAnalysisResult = average.apply(app.getSleepList());
        String result = sleepAnalysisResult.toString();
        //Then
        assertEquals(EXPECTED_VALUE_ZERO, result);
    }

    @Test
    @DisplayName("Правильная обработка типа пользователя")
    public void testShouldGetCorrectTypesUsers() throws IOException, ListSleepSessionsIsEmpty {
        //Given
        UserClassificationBySleep userType = new UserClassificationBySleep();
        app.addSleepSessions(FILE_NAME_MAIN, app);
        //When
        SleepAnalysisResult<TypesUsers> sleepAnalysisResult = userType.apply(app.getSleepList());
        String result = sleepAnalysisResult.toString();
        //Then
        assertEquals(EXPECTED_TYPE_USER, result);
    }
}
