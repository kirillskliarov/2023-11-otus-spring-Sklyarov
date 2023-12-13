package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine();
        ioService.printFormattedLine("Please answer the questions below%n");
        questionDao.findAll()
                .forEach(question -> {
                    showQuestion(question);
                    showAnswers(question);
                    ioService.printLine();
                });
    }

    private void showQuestion(Question question) {
        ioService.printFormattedLine(question.text());
    }

    private void showAnswers(Question question) {
        question.answers()
                .forEach(answer -> {
                    showAnswer(answer);
                });
    }

    private void showAnswer(Answer answer) {
        ioService.printFormattedLine(answer.text());
    }
}
