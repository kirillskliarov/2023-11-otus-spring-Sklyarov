package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine();

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = this.askQuestion(question);
            testResult.applyAnswer(question, isAnswerValid);
            ioService.printLine();
        }
        return testResult;
    }



    private boolean askQuestion(Question question) {
        var questionText = question.text();
        ioService.printFormattedLine(questionText);
        var answers = question.answers();
        for (int index = 0; index < answers.size(); index++) {
            var answer = answers.get(index);
            var answerText = answer.text();
            var humanReadableIndex = index + 1;
            ioService.printFormattedLine("%d. %s", humanReadableIndex, answerText);
        }
        var maxAnswer = answers.size();
        var humanReadableUserAnswerIndex = getUserAnswer(maxAnswer);
        var answerIndex = humanReadableUserAnswerIndex - 1;
        return answers.get(answerIndex).isCorrect();
    }

    private int getUserAnswer(int maxAnswerIndex) {
        int parsedAnswer = 0;

        do {
            try {
                var userAnswer = ioService
                        .readStringWithFormattedPromptLocalized("TestService.enter.the.answer", 1, maxAnswerIndex);
                parsedAnswer = Integer.parseInt(userAnswer);
                if (parsedAnswer <= 0 || parsedAnswer > maxAnswerIndex) {
                    showCaution();
                }
            } catch (NumberFormatException e) {
                showCaution();
            }
        } while (parsedAnswer <= 0 || parsedAnswer > maxAnswerIndex);

        return parsedAnswer;
    }

    private void showCaution() {
        ioService.printLineLocalized("TestService.answer.is.incorrect");
    }

}
