package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.app.SleepTrackerApp;
import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.exception.ListSleepSessionsIsEmpty;
import ru.yandex.practicum.sleeptracker.types.TypesUsers;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserClassificationBySleepTest {
    private static final String TEXT_SLEEPING_SESSION_OWL = "01.10.25 23:15;02.10.25 09:15;GOOD";
    private static final String TEXT_SLEEPING_SESSION_LARK = "02.10.25 21:30;03.10.25 06:30;GOOD";
    private static final String TEXT_SLEEPING_SESSION_DOVE = "02.10.25 23:30;03.10.25 06:30;GOOD";

    private static final String FILE_NAME_MAIN = "src/main/resources/sleep_log.txt";
    private static final String FILE_NAME_TEST = "src/test/resources/sleep_log_test.txt";
    private static final String DESCRIPTION = "Тип пользователя";
    private static final TypesUsers TYPE_OWL = TypesUsers.Owl;
    private static final TypesUsers TYPE_DOVE = TypesUsers.Dove;
    private UserClassificationBySleep userClassificationBySleep;
    private SleepTrackerApp app;
    Path path;

    @BeforeEach
    public void beforeEach() {
        path = Paths.get(FILE_NAME_TEST);
        app = new SleepTrackerApp();
        userClassificationBySleep = new UserClassificationBySleep();
    }

    @Test
    @DisplayName("Метод возвращает тип переменной TypesUsers")
    public void testShouldGetTypesUsersResult() throws IOException, ListSleepSessionsIsEmpty {
        //Given
        boolean checkType = false;
        app.addSleepSessions(FILE_NAME_MAIN, app);
        //When
        SleepAnalysisResult<?> result = userClassificationBySleep.apply(app.getSleepList());
        if (result.getResult() instanceof TypesUsers) {
            checkType = true;
        }
        //Then
        assertTrue(checkType);
    }

    @Test
    @DisplayName("Метод возвращает правильное описание и результат")
    public void testShouldGetDescriptionAndResult() throws IOException, ListSleepSessionsIsEmpty {
        //Given
        try (Writer fileWriter = Files.newBufferedWriter(path)) {
            fileWriter.write(TEXT_SLEEPING_SESSION_OWL + "\n");
        }
        app.addSleepSessions(FILE_NAME_TEST, app);
        //When
        SleepAnalysisResult<TypesUsers> result = userClassificationBySleep.apply(app.getSleepList());
        String resultDescription = result.getDescription();
        TypesUsers resultValue = result.getResult();
        //Then
        assertEquals(DESCRIPTION, resultDescription);
        assertEquals(TYPE_OWL, resultValue);
    }

    @Test
    @DisplayName("Если количество ночей двух типов совпадает вернет тип голубь")
    public void testShouldGetTypeDoveIfOtherTypesEqual() throws IOException, ListSleepSessionsIsEmpty {
        //Given
        try (Writer fileWriter = Files.newBufferedWriter(path)) {
            fileWriter.write(TEXT_SLEEPING_SESSION_OWL + "\n");
            fileWriter.write(TEXT_SLEEPING_SESSION_LARK);
        }
        app.addSleepSessions(FILE_NAME_TEST, app);
        //When
        SleepAnalysisResult<TypesUsers> result = userClassificationBySleep.apply(app.getSleepList());
        TypesUsers resultValue = result.getResult();
        //Then
        assertEquals(TYPE_DOVE, resultValue);
    }

    @Test
    @DisplayName("Если количество ночей всех типов совпадает вернет тип голубь")
    public void testShouldGetTypeDoveIfAllTypesEqual() throws IOException, ListSleepSessionsIsEmpty {
        //Given
        try (Writer fileWriter = Files.newBufferedWriter(path)) {
            fileWriter.write(TEXT_SLEEPING_SESSION_OWL + "\n");
            fileWriter.write(TEXT_SLEEPING_SESSION_LARK + "\n");
            fileWriter.write(TEXT_SLEEPING_SESSION_DOVE);
        }
        app.addSleepSessions(FILE_NAME_TEST, app);
        //When
        SleepAnalysisResult<TypesUsers> result = userClassificationBySleep.apply(app.getSleepList());
        TypesUsers resultValue = result.getResult();
        //Then
        assertEquals(TYPE_DOVE, resultValue);
    }
}
