package ru.otus.hw.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> klass, Long id) {
        super(getMessage(klass, id));
    }

    protected static String getMessage(Class<?> klass, Long id) {
        return String.format("Entity '%s' with id %d not found", klass.getName(), id);
    }
}
