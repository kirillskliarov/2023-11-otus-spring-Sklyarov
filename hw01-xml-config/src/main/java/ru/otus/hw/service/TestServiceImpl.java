package ru.otus.hw.service;

import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;

import java.io.IOException;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;
    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        questionDao.findAll().forEach(question -> {
            ioService.printFormattedLine(question.text());
        });
    }
}
