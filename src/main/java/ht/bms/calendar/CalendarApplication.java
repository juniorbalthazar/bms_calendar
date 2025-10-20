package ht.bms.calendar;

import ht.bms.calendar.domain.repo.BmsCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableRetry
@SpringBootApplication
public class CalendarApplication {
	public static void main(String[] args) {
		SpringApplication.run(CalendarApplication.class, args);

	}


}
