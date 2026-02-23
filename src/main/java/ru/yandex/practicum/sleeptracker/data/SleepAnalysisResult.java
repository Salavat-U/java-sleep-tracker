package ru.yandex.practicum.sleeptracker.data;

public class SleepAnalysisResult<T> {
    private final String description;
    private final T result;

    public SleepAnalysisResult(String description, T result) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        if (result.toString().equals("0")) {
            return description + ": " + "результат не найден.";
        }
        return description + ": " + result + ".";
    }
}
