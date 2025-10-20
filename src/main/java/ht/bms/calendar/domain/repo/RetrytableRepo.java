package ht.bms.calendar.domain.repo;

import ht.bms.calendar.domain.BmsCalendar;
import ht.bms.calendar.domain.BmsCalendarHolidayFixed;
import ht.bms.calendar.domain.BmsCalendarHolidayMovable;
import ht.bms.calendar.domain.BmsSetting;
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
    private SettingRepository settingRepo;

    public RetrytableRepo(BmsCalendarRepository calendarRepo,
                           BmsCalendarHolidayFixedRepository holidayFixedRepo,
                           BmsCalendarHolidayMovableRepository holidayMovableRepo,
                           SettingRepository settingRepo) {
        this.calendarRepo = calendarRepo;
        this.holidayFixedRepo = holidayFixedRepo;
        this.holidayMovableRepo = holidayMovableRepo;
        this.settingRepo = settingRepo;
    }



    /**
     * find Calendar by year and month
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public List<BmsCalendar> getCalendar(BigDecimal year, BigDecimal month){
        return calendarRepo.getCalendar(year,month);
    }


    /**
     * Save  Calendar
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public BmsCalendar saveCalendar(BmsCalendar calendar){
        return calendarRepo.save(calendar);
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
    public Optional<BmsCalendarHolidayFixed> getHolidayFixedStr(String date){
        return holidayFixedRepo.getHolidayStr(date);
    }


    /**
     * Save  CalendarHolidayFixed
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public BmsCalendarHolidayFixed saveCalendarHolidayFixed(BmsCalendarHolidayFixed holidayFixed){
        return holidayFixedRepo.save(holidayFixed);
    }

    /**
     * find Calendar Holiday Movable by date String
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public Optional<BmsCalendarHolidayMovable> getHolidayMovableStr(String date){
        return holidayMovableRepo.getHolidayStr(date);
    }


    /**
     * Save  CalendarHolidayFixed
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public BmsCalendarHolidayMovable saveCalendarHolidayMovable(BmsCalendarHolidayMovable holidayMovable){
        return holidayMovableRepo.save(holidayMovable);
    }


    /**
     * find Calendar by date String
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public Optional<BmsCalendar> getCalendarDateStr(String date){
        return calendarRepo.getCalendarDateStr(date);
    }



    /**
     * find  Setting by Institution Id
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public Optional<BmsSetting> getSetting(BigDecimal institutionId) {
        return settingRepo.findByIsntituion(institutionId);
    }


    /**
     * Save  Setting
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public BmsSetting saveSetting(BmsSetting setting){
        return settingRepo.save(setting);
    }

}
