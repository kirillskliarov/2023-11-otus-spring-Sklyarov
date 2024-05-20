package ru.otus.hw.exceptions;

public class RandomException extends RuntimeException {
    public RandomException(Long numerator, Long denominator) {
        super(getMessage(numerator, denominator));
    }

    protected static String getMessage(Long numerator, Long denominator) {
        return String.format("Random Exception with chance %d/%d", numerator, denominator);
    }
}
