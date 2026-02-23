package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.data.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    private static final String DESCRIPTION = "Средняя продолжительность сна (в минутах)";

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        int duration = (int) sleepingSessions.stream()
                .mapToLong(sleeping ->
                        Duration.between(sleeping.getStartTime(), sleeping.getEndTime()).toMinutes())
                .average()
                .orElse(0);

        return new SleepAnalysisResult<>(DESCRIPTION, duration);
    }
}
