package ru.otus.hw.services;

import reactor.core.publisher.Mono;

public interface SequenceGeneratorService {
    Mono<Long> getNext(String sequenceId);
}
