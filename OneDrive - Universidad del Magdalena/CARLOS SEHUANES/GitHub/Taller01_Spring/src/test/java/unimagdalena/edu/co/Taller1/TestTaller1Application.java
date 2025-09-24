package unimagdalena.edu.co.Taller1;

import org.springframework.boot.SpringApplication;

public class TestTaller1Application {

	public static void main(String[] args) {
		SpringApplication.from(Taller1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
