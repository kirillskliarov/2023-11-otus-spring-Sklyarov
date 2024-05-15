package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        System.out.println("http://localhost:8080/api/greeting/1");
        System.out.println("http://localhost:8080/api/greeting/2");
        System.out.println("http://localhost:8080/api/greeting/3");
        System.out.println("http://localhost:8080/api/greeting/100500");
    }

}
