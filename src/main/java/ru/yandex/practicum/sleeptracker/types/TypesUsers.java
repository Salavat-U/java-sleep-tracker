package ru.yandex.practicum.sleeptracker.types;

public enum TypesUsers {
    Owl("Сова"),
    Lark("Жаворонок"),
    Dove("Голубь");

    private final String type;

    TypesUsers(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
