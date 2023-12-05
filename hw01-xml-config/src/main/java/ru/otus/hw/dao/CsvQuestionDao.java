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
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/
        InputStream in = getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());
        try {
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withSkipLines(1)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .build();
            List<QuestionDto> beans = csvToBean.parse();
            List<Question> qu = beans.stream().map(QuestionDto::toDomainObject).toList();
//            System.out.println(qu.get(0).answers());
            return new ArrayList<>(qu);
        } catch (NullPointerException e) {
            throw new QuestionReadException("Can not read questions", e);
        }
    }
}
