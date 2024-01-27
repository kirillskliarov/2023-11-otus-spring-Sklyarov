package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private Student currentStudent;

    public Student createStudent(String firstName, String lastName) {
        currentStudent = new Student(firstName, lastName);
        return currentStudent;
    }

    @Override
    public Student getCurrentStudent() {
        return currentStudent;
    }
}
