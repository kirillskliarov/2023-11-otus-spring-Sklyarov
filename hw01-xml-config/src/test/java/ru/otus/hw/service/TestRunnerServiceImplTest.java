package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RequiredArgsConstructor
public class TestRunnerServiceImplTest {
    private final TestService testService = mock(TestService.class);

    private TestRunnerService testRunnerService;

    @BeforeEach
    public void init() {
        testRunnerService = new TestRunnerServiceImpl(testService);
    }

    @Test
    public void run() {
        testRunnerService.run();

        verify(testService, times(1)).executeTest();
    }
}
