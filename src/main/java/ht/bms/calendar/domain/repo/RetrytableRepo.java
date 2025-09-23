package ht.bms.calendar.domain.repo;

import ht.bms.calendar.domain.BmsCalendar;
import ht.bms.calendar.domain.BmsCalendarHolidayFixed;
import jakarta.transaction.Transactional;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class RetrytableRepo {

    private BmsCalendarRepository calendarRepo;
    private BmsCalendarHolidayFixedRepository holidayFixedRepo;
    private BmsCalendarHolidayMovableRepository holidayMovableRepo;

    public RetrytableRepo(BmsCalendarRepository calendarRepo,
                           BmsCalendarHolidayFixedRepository holidayFixedRepo,
                           BmsCalendarHolidayMovableRepository holidayMovableRepo) {
        this.calendarRepo = calendarRepo;
        this.holidayFixedRepo = holidayFixedRepo;
        this.holidayMovableRepo = holidayMovableRepo;
    }


    /**********************************************************************************************
     * find Calendar between two dates
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public List<BmsCalendar> getCalendarBetwenDate(Date tomorrow, Date dateplus30){
        return calendarRepo.getCalendarBetwenDate(tomorrow,dateplus30);
    }

    /**********************************************************************************************
     * find Calendar between two dates in Long format parameter
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public List<BmsCalendar> getCalendarLong(BigDecimal tomorrow, BigDecimal dateplus30){
        return calendarRepo.getCalendarLong(tomorrow,dateplus30);
    }



    /**
     * find Calendar Holiday Fixed by date String
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public Optional<BmsCalendarHolidayFixed> getHolidayStr(String date){
        return holidayFixedRepo.getHolidayStr(date);
    }

    /**
     * find Calendar Holiday Fixed by date String
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public Optional<BmsCalendar> getCalendarDateStr(String date){
        return calendarRepo.getCalendarDateStr(date);
    }

}
