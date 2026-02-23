package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.data.SleepingSession;
import ru.yandex.practicum.sleeptracker.types.AssessmentType;

import java.util.List;
import java.util.function.Function;

public class NumberOfBadQualitySleep implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    private static final String DESCRIPTION = "Количество сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        int count = (int) sleepingSessions.stream()
                .filter(sleeping -> sleeping.getAssessmentType() == AssessmentType.BAD)
                .count();

        return new SleepAnalysisResult<>(DESCRIPTION, count);
    }
}
