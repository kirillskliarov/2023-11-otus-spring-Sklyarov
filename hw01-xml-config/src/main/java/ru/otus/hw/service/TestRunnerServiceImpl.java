package ru.otus.hw.service;

import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    @Override
    public void run() {
        testService.executeTest();
    }
}
