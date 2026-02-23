package ru.yandex.practicum.sleeptracker.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.exception.ListSleepSessionsIsEmpty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SleepTrackerAppTest {
    private static final String FILE_NAME_MAIN = "src/main/resources/sleep_log.txt";
    private static final String FILE_NAME_TEST = "src/test/resources/sleep_log_test.txt";
    private static final int COUNT_OF_SLEEPING_SESSIONS = 13;
    private static final int COUNT_OF_FUNCTIONS = 7;
    private static final int ZERO = 0;
    private SleepTrackerApp app;

    @BeforeEach
    public void beforeEach() {
        app = new SleepTrackerApp();
    }

    @Test
    @DisplayName("После старта программы функции добавляются")
    public void testShouldAddAllFunctionsToListOnStart() {
        //Given
        int sizeCurrent = app.getFunctionList().size();
        //When
        app.addFunctions();
        int sizeAfterAdd = app.getFunctionList().size();
        //Then
        assertEquals(ZERO, sizeCurrent);
        assertEquals(COUNT_OF_FUNCTIONS, sizeAfterAdd);
    }

    @Test
    @DisplayName("После старта программы список сессий сна добавляется")
    public void testShouldAddAllSleepingSessionsToListOnStart() throws ListSleepSessionsIsEmpty, IOException {
        //Given
        int sizeCurrent = app.getSleepList().size();
        //When
        app.addSleepSessions(FILE_NAME_MAIN, app);
        int sizeAfterAdd = app.getSleepList().size();
        //Then
        assertEquals(ZERO, sizeCurrent);
        assertEquals(COUNT_OF_SLEEPING_SESSIONS, sizeAfterAdd);
    }

    @Test
    @DisplayName("Ошибка - список сессий сна пуст")
    public void testExceptionFileNotFoundException() throws IOException {
        //Given
        boolean checkException = false;
        Path path = Paths.get(FILE_NAME_TEST);
        try (Writer fileWriter = Files.newBufferedWriter(path)) {
            fileWriter.write(" ");
        }
        //When
        try {
            app.addSleepSessions(FILE_NAME_TEST, app);
        } catch (ListSleepSessionsIsEmpty e) {
            checkException = true;
        }
        //Then
        assertTrue(checkException);
    }

    @Test
    @DisplayName("Ошибка - файл не найден")
    public void testExceptionFileNotFound() throws IOException, ListSleepSessionsIsEmpty {
        //Given
        boolean checkException = false;
        //When
        try {
            app.addSleepSessions("Выдуманный путь к файлу", app);
        } catch (FileNotFoundException e) {
            checkException = true;
        }
        //Then
        assertTrue(checkException);
    }
}
