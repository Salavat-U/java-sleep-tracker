package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.data.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class NumberOfSleepSessions implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    private static final String DESCRIPTION = "Количество сессий сна";

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> session) {
        int countSleepSessions = session.size();
        return new SleepAnalysisResult<>(DESCRIPTION, countSleepSessions);
    }
}
