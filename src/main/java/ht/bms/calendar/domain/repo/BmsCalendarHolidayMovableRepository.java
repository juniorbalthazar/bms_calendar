package ht.bms.calendar.domain.repo;

import ht.bms.calendar.domain.BmsCalendarHolidayMovable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface BmsCalendarHolidayMovableRepository  extends CrudRepository<BmsCalendarHolidayMovable, Date>{
	
	@Query("select c from BmsCalendarHolidayMovable c where c.holidayStr = ?1 and c.isAvailable=0")
	public Optional<BmsCalendarHolidayMovable> getHolidayStr(String start);
	
	@Query("select c from BmsCalendarHolidayMovable c where c.holidayStr = ?1")
	public Optional<BmsCalendarHolidayMovable> getHolidayStrInactive(String start);
}
