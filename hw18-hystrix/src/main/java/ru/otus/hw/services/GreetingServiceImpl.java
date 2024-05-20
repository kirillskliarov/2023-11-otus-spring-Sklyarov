package ru.otus.hw.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.exceptions.RandomException;
import ru.otus.hw.models.Greeting;
import ru.otus.hw.models.dto.GreetingDto;
import ru.otus.hw.repositories.GreetingRepository;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class GreetingServiceImpl implements GreetingService {
    private final GreetingRepository greetingRepository;

    private final Random random;

    @Override
    @CircuitBreaker(name = "greeting", fallbackMethod = "getRecoverGreeting")
    public GreetingDto getGreeting(Long id) throws Exception {
        randomException();
        Greeting greeting = this.greetingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Greeting.class, id));
        return new GreetingDto(
                greeting.getGreeting(),
                greeting.getLang()
        );
    }

    public GreetingDto getRecoverGreeting(Throwable e) {
        System.out.println(e.getMessage());
        return new GreetingDto("Hello", "en");
    }

    private void randomException() throws RandomException {
        long chanceNumerator = 1L;
        long chanceDenominator = 5L;
        long randomNum = random.nextLong(chanceDenominator);
        boolean chance = randomNum < chanceNumerator;
        if (chance) {
            throw new RandomException(chanceNumerator, chanceDenominator);
        }
    }
}
