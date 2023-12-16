package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() throws QuestionReadException {
        var reader = getInputStreamReader();
        var csvToBean = getCsvToBean(reader);

        return getQuestionList(csvToBean);
    }

    private InputStreamReader getInputStreamReader() throws QuestionReadException {
        var testFileName = fileNameProvider.getTestFileName();
        InputStream resourceTestStream = getClass().getClassLoader()
                .getResourceAsStream(testFileName);
        if (resourceTestStream == null) {
            throw new QuestionReadException("Can not read question resource");
        }

        return new InputStreamReader(resourceTestStream, StandardCharsets.UTF_8);
    }

    private CsvToBean<QuestionDto> getCsvToBean(InputStreamReader reader) {
        return new CsvToBeanBuilder<QuestionDto>(reader).withSkipLines(1)
                .withType(QuestionDto.class)
                .withSeparator(';')
                .build();
    }

    private List<Question> getQuestionList(CsvToBean<QuestionDto> csvToBean) {
        List<QuestionDto> beans = csvToBean.parse();
        List<Question> questionList = beans.stream()
                .map(bean -> bean.toDomainObject())
                .toList();

        return new ArrayList<>(questionList);
    }
}
