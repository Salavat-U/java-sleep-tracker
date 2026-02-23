package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.data.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MinimumSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    private static final String DESCRIPTION = "Минимальная продолжительность сна (в минутах)";

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        int duration = sleepingSessions.stream()
                .map(sleeping -> Duration.between(sleeping.getStartTime(), sleeping.getEndTime()))
                .min(Duration::compareTo)
                .map(minutes -> (int) minutes.toMinutes())
                .orElse(0);

        return new SleepAnalysisResult<>(DESCRIPTION, duration);
    }
}
