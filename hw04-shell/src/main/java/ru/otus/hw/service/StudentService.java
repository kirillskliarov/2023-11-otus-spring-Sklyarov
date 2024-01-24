package ru.otus.hw.service;

import ru.otus.hw.domain.Student;

public interface StudentService {

    Student createStudent(String firstName, String lastName);

    Student determineCurrentStudent();
}
