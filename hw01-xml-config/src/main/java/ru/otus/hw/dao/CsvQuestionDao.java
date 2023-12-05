package ru.otus.hw.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
//        var qDto = QuestionDto.class.getClassLoader().getResourceAsStream("questions.csv");
//        CSVReader reader = new CSVReaderBuilder(new FileReader("questions.csv")).withSkipLines(1).build();
//        List<String[]> myEntries = reader.readAll();

        InputStream in = getClass().getClassLoader().getResourceAsStream("questions.csv");
        System.out.println(in);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
//        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
//        System.out.println();

        List<Question> beans = new CsvToBeanBuilder(reader).withSkipLines(1).withType(QuestionDto.class).build().parse();
//        List<Question> beans = new CsvToBeanBuilder(reader).withSkipLines(0).withType(Question.class).build().parse();
        System.out.println(beans);

        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        return new ArrayList<>();
    }
}
