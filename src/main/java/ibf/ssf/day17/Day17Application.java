package ibf.ssf.day17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf.ssf.day17.Service.WeatherService;

@SpringBootApplication
public class Day17Application implements CommandLineRunner {

	// @Autowired
	// private httpBinService svc;

	@Autowired
	private WeatherService weatherSvc;

	public static void main(String[] args) {
		SpringApplication.run(Day17Application.class, args);
	}

	@Override
	public void run(String... args) {
		weatherSvc.getWeatherData("Singapore");
	}

}
