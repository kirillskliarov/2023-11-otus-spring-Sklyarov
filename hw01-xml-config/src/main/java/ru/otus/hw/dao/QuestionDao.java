package ru.otus.hw.dao;

import com.opencsv.exceptions.CsvException;
import ru.otus.hw.domain.Question;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface QuestionDao {
    List<Question> findAll() throws IOException, CsvException;
}
