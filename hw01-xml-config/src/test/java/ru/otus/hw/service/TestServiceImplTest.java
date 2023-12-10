package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestServiceImplTest {
    private final IOService ioService = mock(IOService.class);
    private final QuestionDao questionDao = mock(QuestionDao.class);
    public TestServiceImpl testService;

    @BeforeEach
    public void init() {
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    public void executeTest() {
        var question = "question 1";
        List<Question> questions = List.of(new Question(question, List.of()));
        Mockito.when(questionDao.findAll())
                .thenReturn(questions);

        testService.executeTest();

        verify(ioService, times(1)).printFormattedLine(question);
    }

}
