package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.domain.Student;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RequiredArgsConstructor
public class TestRunnerServiceImplTest {
    private final TestService testService = mock(TestService.class);

    private final StudentService studentService = mock(StudentService.class);

    private final ResultService resultService = mock(ResultService.class);

    private final Student student = new Student("Ivan", "Ivanov");

    private TestRunnerService testRunnerService;

    @BeforeEach
    public void init() {
        testRunnerService = new TestRunnerServiceImpl(testService, studentService, resultService);
    }

    @Test
    public void run() {
        Mockito.when(studentService.determineCurrentStudent()).thenReturn(student);
        testRunnerService.run();

        verify(testService, times(1)).executeTestFor(student);
    }
}
