package ru.otus.hw.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RequiredArgsConstructor
@Service
public class CsvQuestionDaoTest {
    private static final String correctQuestionsFile = "questions.csv";

    private final TestFileNameProvider fileNameProvider = mock(TestFileNameProvider.class);

    private CsvQuestionDao csvQuestionDao;


    @BeforeEach
    public void init() {
        csvQuestionDao = new CsvQuestionDao(fileNameProvider);
    }

    @Test
    public void run() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn(correctQuestionsFile);

        csvQuestionDao.findAll();

        verify(fileNameProvider, times(1)).getTestFileName();
    }

    @Test
    public void request() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn(correctQuestionsFile);

        var questions = csvQuestionDao.findAll();
        var firstQuestionText = questions.get(0).text();

        assertEquals(firstQuestionText, "First question");
    }
}
