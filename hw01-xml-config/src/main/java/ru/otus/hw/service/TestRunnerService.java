package ru.otus.hw.service;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public interface TestRunnerService {
    void run() throws IOException, CsvException;
}
