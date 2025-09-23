package ht.bms.calendar.domain.repo;

import ht.bms.calendar.domain.BmsCalendarHolidayFixed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface BmsCalendarHolidayFixedRepository   extends CrudRepository<BmsCalendarHolidayFixed, Date>{

	
	@Query("select c from BmsCalendarHolidayFixed c where c.holidayStr = ?1 and c.isAvailable=0")
	public Optional<BmsCalendarHolidayFixed> getHolidayStr(String start);
	
	@Query("select c from BmsCalendarHolidayFixed c where c.holidayStr = ?1")
	public Optional<BmsCalendarHolidayFixed> getHolidayStrInactive(String start);
}
