package ru.otus.hw.service;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public interface TestService {

    void executeTest() throws IOException, CsvException;
}
