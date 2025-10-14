package unimagdalena.edu.co.Taller1.repositories;

import org.springframework.boot.SpringApplication;
import unimagdalena.edu.co.Taller1.Taller01SpringApplication;

public class TestTaller01SpringApplication {
    public static void main(String[] args) {
        SpringApplication.from(Taller01SpringApplication::main).with(TestcontainersConfiguration.class).run(args);
    }
}
