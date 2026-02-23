package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.data.SleepingSession;
import ru.yandex.practicum.sleeptracker.types.TypesUsers;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UserClassificationBySleep implements Function<List<SleepingSession>, SleepAnalysisResult<TypesUsers>> {
    private static final LocalTime OWL_TIME_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_TIME_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_TIME_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_TIME_END = LocalTime.of(7, 0);

    private static final TypesUsers OWL = TypesUsers.Owl;
    private static final TypesUsers LARK = TypesUsers.Lark;
    private static final TypesUsers DOVE = TypesUsers.Dove;

    private static final String DESCRIPTION = "Тип пользователя";
    private static final int DEFAULT_VALUE = 0;
    private static final int COUNTER = 1;

    @Override
    public SleepAnalysisResult<TypesUsers> apply(List<SleepingSession> sessions) {
        Map<TypesUsers, Integer> counts = new HashMap<>();
        sessions.forEach(sleeping -> {
            LocalTime startTime = sleeping.getStartTime().toLocalTime();
            LocalTime endTime = sleeping.getEndTime().toLocalTime();
            if (startTime.isAfter(OWL_TIME_START) && endTime.isAfter(OWL_TIME_END)) {
                counts.put(OWL, counts.getOrDefault(OWL, DEFAULT_VALUE) + COUNTER);
            } else if (startTime.isBefore(LARK_TIME_START) && endTime.isBefore(LARK_TIME_END)) {
                counts.put(LARK, counts.getOrDefault(LARK, DEFAULT_VALUE) + COUNTER);
            } else counts.put(DOVE, counts.getOrDefault(DOVE, DEFAULT_VALUE) + COUNTER);
        });

        TypesUsers type;
        int maxValue = counts.values().stream()
                .max(Integer::compareTo)
                .orElse(DEFAULT_VALUE);
        int numberRepeats = (int) counts.values().stream()
                .filter(max -> max == maxValue)
                .count();

        if (numberRepeats > 1 || counts.getOrDefault(DOVE, 0) == maxValue) {
            type = DOVE;
        } else {
            type = counts.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxValue)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(DOVE);
        }

        return new SleepAnalysisResult<>(DESCRIPTION, type);
    }
}
