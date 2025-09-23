package ht.bms.calendar.service.facade;

import ht.bms.calendar.domain.BmsCalendar;
import ht.bms.calendar.domain.repo.RetrytableRepo;
import ht.bms.calendar.exception.CalendarException;
import ht.bms.calendar.model.CalendarBean;
import ht.bms.calendar.model.CalendarResponse;
import ht.bms.calendar.model.Response;
import ht.bms.calendar.service.Constants;
import ht.bms.calendar.service.mapper.CalendarMapper;
import ht.bms.calendar.utils.DateHelper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Service
public class CalendarService {

    private RetrytableRepo repo;
    private CalendarMapper mapper;
    private  final Logger log = LoggerFactory.getLogger(CalendarService.class);

    public CalendarService(RetrytableRepo repo, CalendarMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Mono<CalendarResponse> GetCalendarDate(Date date){

        return null;
    }

    public Mono<CalendarResponse> GetCalendarBetweenDate(Date today,Date date){

        return null;
    }

    public Mono<Response> setCalendar(Date today, Integer year){

        return null;
    }

    public Integer checkHoliday(String day){
        if (Optional.ofNullable(day).isEmpty())
            throw new IllegalArgumentException("Day is required");

            if(repo.getHolidayStr(day).isPresent()) {
                return Constants.IS_AVAILABLE;
            }

            if(repo.getHolidayStr(day).isPresent()) {

                return   Constants.IS_AVAILABLE;

            }
        return Constants.IS_NOT_AVAILABLE;
    }

    public boolean checkDayBetween(Date date){
        if (Optional.ofNullable(date).isEmpty())
            throw new IllegalArgumentException("Date is required");
            if(date.before(DateHelper.toDayPlus30())) {
                return true;
            }

        return false;
    }



    public Mono<CalendarResponse> allAvailableDayFrom30Day(BigDecimal officeId, BigDecimal serviceId,String avaibleDateForOfficeCapacity) {
        // TODO Auto-generated method stub

        if (officeId == null) {
            throw new IllegalArgumentException("OfficeId is required");
        }
        if (serviceId == null) {
            throw new IllegalArgumentException("serviceId is required");
        }
        if (avaibleDateForOfficeCapacity == null) {
            throw new IllegalArgumentException("Date for Office Capacity is required");
        }

        CalendarResponse response = new CalendarResponse();
        List<BmsCalendar> calendars = Optional.ofNullable(repo.getCalendarBetwenDate(DateHelper.tomorrowDay(), DateHelper.toDayPlus30()))
                .orElseThrow(() -> new CalendarException("No records found"));

        Predicate<BmsCalendar> isDayAvailable = e -> e.getIsdavailable().intValue() == Constants.IS_AVAILABLE;
        Predicate<BmsCalendar> isBusinessDay = e -> e.getIsbusinessday().intValue() == Constants.IS_AVAILABLE;
        Predicate<BmsCalendar> isHoliday = e -> e.getIsholiday().intValue() == checkHoliday(e.getDateStr());
        Predicate<BmsCalendar> isNumTranSac = e -> e.getDateStr().equals(avaibleDateForOfficeCapacity);

        List<CalendarBean> cal = calendars.stream()
                .filter(isDayAvailable.and(isBusinessDay).and(isHoliday).and(isNumTranSac))
                .map(mapper::toCalendarDto)
                .collect(Collectors.toList());

        if (cal.isEmpty()) {
            throw new CalendarException("No records found");
        }

        response.setIsSuccessfully(true);
        response.setCalendar(cal);
        return Mono.just(response);

    }


    public Mono<CalendarBean> isAvailableDay(String day,BigDecimal officeId, BigDecimal serviceId,String avaibleDateForOfficeCapacity){
        // TODO Auto-generated method stub

        if (officeId == null) {
            throw new IllegalArgumentException("OfficeId is required");
        }
        if (serviceId == null) {
            throw new IllegalArgumentException("serviceId is required");
        }
        if (avaibleDateForOfficeCapacity == null) {
            throw new IllegalArgumentException("Date for Office Capacity is required");
        }

        Optional<BmsCalendar> calendar = Optional.ofNullable(repo.getCalendarDateStr(day))
                .orElseThrow(() -> new CalendarException("No records found"));

                Predicate<BmsCalendar> isDayAvailable = e -> e.getIsdavailable().intValue() == Constants.IS_AVAILABLE;
                Predicate<BmsCalendar> isBusinessDay = e -> e.getIsbusinessday().intValue() == Constants.IS_AVAILABLE;
                Predicate<BmsCalendar> isHoliday = e -> e.getIsholiday().intValue() == checkHoliday(e.getDateStr());
                Predicate<BmsCalendar> isNumTranSac = e -> e.getDateStr().equals(avaibleDateForOfficeCapacity);
                return Mono.justOrEmpty(
                        Optional.ofNullable(calendar.stream().toList())
                                .stream()
                                .flatMap(List::stream)
                                .filter(isDayAvailable.and(isBusinessDay).and(isHoliday).and(isNumTranSac))
                                .map(mapper::toCalendarDto)
                                .findFirst()
                );
    }



    public Mono<CalendarBean> checkAvailableDay(BigDecimal officeId, BigDecimal serviceId, String avaibleDateForOfficeCapacity){
        // TODO Auto-generated method stub
        if (officeId == null) {
            throw new IllegalArgumentException("OfficeId is required");
        }
        if (serviceId == null) {
            throw new IllegalArgumentException("serviceId is required");
        }
        if (avaibleDateForOfficeCapacity == null) {
            throw new IllegalArgumentException("Date for Office Capacity is required");
        }

        CalendarResponse response = new CalendarResponse();
        List<BmsCalendar> calendars = Optional.ofNullable(repo. getCalendarLong(new BigDecimal(DateHelper.tomorrowDay().getTime()),new BigDecimal(DateHelper.toDayPlus30().getTime())))
                .orElseThrow(() -> new CalendarException("No records found"));

        Predicate<BmsCalendar> isDayAvailable = e -> e.getIsdavailable().intValue() == Constants.IS_AVAILABLE;
        Predicate<BmsCalendar> isBusinessDay = e -> e.getIsbusinessday().intValue() == Constants.IS_AVAILABLE;
        Predicate<BmsCalendar> isHoliday = e -> e.getIsholiday().intValue() == checkHoliday(e.getDateStr());
        Predicate<BmsCalendar> isNumTranSac = e -> e.getDateStr().equals(avaibleDateForOfficeCapacity);
        return Mono.justOrEmpty(
                Optional.ofNullable(calendars)
                        .stream()
                        .flatMap(List::stream)
                        .filter(isDayAvailable.and(isBusinessDay).and(isHoliday).and(isNumTranSac))
                        .map(mapper::toCalendarDto)
                        .findFirst()
        );

    }

}
