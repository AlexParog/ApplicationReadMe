package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Запускаемое прилодение
 */
@SpringBootApplication
public class CourseApplication {

    /**
     * Точка входа в программу
     * @param args String[]
     */
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }

}