package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.otus.hw.domain.Student;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Should execute test for student")
@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
public class TestRunnerServiceImplTest {
    private final Student student = new Student("Ivan", "Ivanov");

    @MockBean
    private TestService testService;
    @SpyBean
    private StudentService studentService;
    @MockBean
    private ResultService resultService;
    @Autowired
    private TestRunnerServiceImpl testRunnerService;

    @Test
    public void run() {
        Mockito.when(studentService.getCurrentStudent()).thenReturn(student);
        testRunnerService.run();

        verify(testService, times(1)).executeTestFor(student);
    }
}
