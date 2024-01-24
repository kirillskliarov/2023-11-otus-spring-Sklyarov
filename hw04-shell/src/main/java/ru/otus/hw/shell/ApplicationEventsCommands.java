package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {
    private final StudentService studentService;
    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Enter fist name and last name", key = {"l", "login"})
    public String login(
            @ShellOption(defaultValue = "anonymous") String firstName,
            @ShellOption(defaultValue = "anonymous") String lastName
    ) {
        var student = studentService.createStudent(firstName, lastName);
        return student.getFullName();
    }

    @ShellMethod(value = "Run testing", key = {"r", "run"})
    @ShellMethodAvailability("isRunCommandAvailable")
    public void run() {
        testRunnerService.run();
    }

    private Availability isRunCommandAvailable() {
        return studentService.getCurrentStudent() == null ? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }
}
