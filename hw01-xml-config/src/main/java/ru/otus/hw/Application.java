package ru.otus.hw;

import com.opencsv.exceptions.CsvException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.service.TestRunnerService;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, CsvException {

        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}
