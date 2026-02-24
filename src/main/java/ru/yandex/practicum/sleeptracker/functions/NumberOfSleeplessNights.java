package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.data.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.data.SleepingSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NumberOfSleeplessNights implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    private static final String DESCRIPTION = "Количество дней бессонных ночей";
    private static final LocalTime SLEEP_TIME_START = LocalTime.of(0, 0);
    private static final LocalTime SLEEP_TIME_END = LocalTime.of(6, 0);
    private static final LocalTime DAY_END = LocalTime.of(12, 0);
    private static final int ONE_DAY = 1;

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepSessions) {
        Set<LocalDate> nightsSessions = sleepSessions.stream()
                .filter(this::isNightsSessions)
                .map(session -> {
                    LocalTime startTime = session.getStartTime().toLocalTime();
                    LocalDate startDate = session.getStartTime().toLocalDate();
                    if (isMidnight(startTime)) {
                        return startDate.minusDays(ONE_DAY);
                    } else return startDate;
                })
                .collect(Collectors.toSet());

        int numberSleeplessNights = getTotalNumberNights(sleepSessions) - nightsSessions.size();

        return new SleepAnalysisResult<>(DESCRIPTION, numberSleeplessNights);
    }

    public Integer getTotalNumberNights(List<SleepingSession> sleepSessions) {
        LocalDate leftBorderDate = sleepSessions.getFirst().getStartTime().toLocalDate();
        LocalDate rightBorderDate = sleepSessions.getLast().getEndTime().toLocalDate();
        LocalTime startTime = sleepSessions.getFirst().getStartTime().toLocalTime();
        if (startTime.isBefore(DAY_END)) leftBorderDate = leftBorderDate.minusDays(ONE_DAY);

        return (int) (rightBorderDate.toEpochDay() - leftBorderDate.toEpochDay());
    }

    public boolean isNightsSessions(SleepingSession session) {
        LocalDate startDate = session.getStartTime().toLocalDate();
        LocalDate endDate = session.getEndTime().toLocalDate();
        LocalTime startTime = session.getStartTime().toLocalTime();
        Period period = Period.between(startDate, endDate);

        return period.getDays() == ONE_DAY || (isMidnight(startTime));
    }

    public boolean isMidnight(LocalTime startTime) {
        return startTime.isAfter(SLEEP_TIME_START) && startTime.isBefore(SLEEP_TIME_END);
    }
}
