package ru.yandex.practicum.sleeptracker.app;

import ru.yandex.practicum.sleeptracker.exception.ListSleepSessionsIsEmpty;
import ru.yandex.practicum.sleeptracker.functions.*;
import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.data.SleepingSession;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class SleepTrackerApp {
    private static Scanner scanner;
    private final List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> functionList =
            new ArrayList<>();
    private final List<SleepingSession> sleepList = new LinkedList<>();

    public List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> getFunctionList() {
        return new ArrayList<>(functionList);
    }

    public void addFunctions() {
        functionList.add(new NumberOfSleepSessions());
        functionList.add(new MinimumSessionDuration());
        functionList.add(new MaximumSessionDuration());
        functionList.add(new AverageSessionDuration());
        functionList.add(new NumberOfBadQualitySleep());
        functionList.add(new UserClassificationBySleep());
        functionList.add(new NumberOfSleeplessNights());
    }

    public List<SleepingSession> getSleepList() {
        return new LinkedList<>(sleepList);
    }

    public void addSleepSessions(String fileName, SleepTrackerApp app)
            throws ListSleepSessionsIsEmpty, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            br.lines()
                    .filter(line -> !line.isBlank())
                    .map(String::trim)
                    .map(SleepingSession::new)
                    .forEach(app.sleepList::add);
            if (app.sleepList.isEmpty()) {
                throw new ListSleepSessionsIsEmpty("Список сессий сна " + fileName + " пуст");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл " + fileName + " не найден");
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла " + fileName);
        }
    }

    public static void main(String[] args) {
        try {
            SleepTrackerApp app = new SleepTrackerApp();
            scanner = new Scanner(System.in);
            System.out.println("Введите путь к файлу с данными о сне:");
            //Путь для проверки - src/main/resources/sleep_log.txt
            String fileName = scanner.nextLine();
            app.addSleepSessions(fileName, app);
            app.addFunctions();
            System.out.println("\nРезультат:\n");

            List<? extends SleepAnalysisResult<?>> resultSleepSessions = app.functionList.stream()
                    .map(count -> count.apply(app.sleepList))
                    .toList();

            resultSleepSessions.forEach(System.out::println);
        } catch (ListSleepSessionsIsEmpty | IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка - " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}