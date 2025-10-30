package ht.bms.calendar.service.facade;

import ht.bms.calendar.domain.*;
import ht.bms.calendar.domain.repo.RetrytableRepo;
import ht.bms.calendar.exception.CalendarException;
import ht.bms.calendar.exception.InvalidInputException;
import ht.bms.calendar.model.*;
import ht.bms.calendar.service.Constants;
import ht.bms.calendar.service.mapper.CalendarMapper;
import ht.bms.calendar.utils.DateHelper;
import ht.bms.calendar.utils.Utils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

     public Integer checkHoliday(String date){
        if (Optional.ofNullable(date).isEmpty())
            throw new IllegalArgumentException("Day is required");

            if(repo.getHolidayFixedStr(date).isPresent()) {
                return Constants.IS_AVAILABLE;
            }

            if(repo.getHolidayFixedStr(date).isPresent()) {

                return   Constants.IS_AVAILABLE;

            }
        return Constants.IS_NOT_AVAILABLE;
    }



    public Mono<CalendarResponse> allAvailableDayFrom30Day(BigDecimal officeId, BigDecimal serviceId,String avaibleDateForOfficeCapacity) {

        Utils.isNullOrEmpty(officeId, new InvalidInputException("OfficeId is required"));
        Utils.isNullOrEmpty(serviceId, new InvalidInputException("serviceId is required"));
        Utils.isNullOrEmpty(avaibleDateForOfficeCapacity, new InvalidInputException("Date for Office Capacity is required"));

        CalendarResponse response = new CalendarResponse();
        List<BmsCalendar> calendars = Optional.ofNullable(repo.getCalendarBetwenDate(DateHelper.tomorrowDay(), DateHelper.toDatePlus30NextDay()))
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


    public Mono<CalendarBean> isAvailableDay(String date,BigDecimal officeId, BigDecimal serviceId,String avaibleDateForOfficeCapacity){

        Utils.isNullOrEmpty(date, new InvalidInputException("Day is required"));
        Utils.isNullOrEmpty(officeId, new InvalidInputException("OfficeId is required"));
        Utils.isNullOrEmpty(serviceId, new InvalidInputException("serviceId is required"));
        Utils.isNullOrEmpty(avaibleDateForOfficeCapacity, new InvalidInputException("Date for Office Capacity is required"));

        if (DateHelper.checkEarlyDate(DateHelper.StringToDate(date)) ||
                DateHelper.checkDayPassDate(DateHelper.StringToDate(date)))
            throw new CalendarException("The date must be between today and the next 30 days");


            Optional<BmsCalendar> calendar = Optional.ofNullable(repo.getCalendarDateStr(date))
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
        Utils.isNullOrEmpty(officeId, new InvalidInputException("OfficeId is required"));
        Utils.isNullOrEmpty(serviceId, new InvalidInputException("serviceId is required"));
        Utils.isNullOrEmpty(avaibleDateForOfficeCapacity, new InvalidInputException("Date for Office Capacity is required"));

        CalendarResponse response = new CalendarResponse();
        List<BmsCalendar> calendars = Optional.ofNullable(repo. getCalendarLong(new BigDecimal(DateHelper.tomorrowDay().getTime()),new BigDecimal(DateHelper.toDatePlus30NextDay().getTime())))
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

    ////////////////////////////

    public Mono<CalendarResponse> setCalendar(int year, int numberOfYear) throws CalendarException{

        Utils.isNullOrEmpty(year, new InvalidInputException("Day is required"));
        Utils.isNullOrEmpty(numberOfYear, new InvalidInputException("Day is required"));

        if (numberOfYear < 1) {
            throw new CalendarException("Le nombre d'année doit etre specifier...");
        }

        List<CalendarBean> calendar = new ArrayList<>();
        LocalDate startDate = LocalDate.of(year, 1, 1);
        long totalDays = Utils.getDayDiff( Period.between(startDate, startDate.plusYears(numberOfYear)));

        for (int i = 0; i < totalDays; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            BmsCalendar newDate = new BmsCalendar();

            newDate.setCalendarDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newDate.setDateStr(DateHelper.DateToString(Date.from(currentDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            newDate.setDateLong(new BigDecimal(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));

            newDate.setYear(new BigDecimal(currentDate.getYear()));
            newDate.setDayofmonth(new BigDecimal(currentDate.getDayOfMonth()));
            int dayOfWeekId =Utils.getWeekId(currentDate.getDayOfWeek().getValue()) ;

           // log.info("@@@  Setting week Name: {} for Week Id: {} date {}",  currentDate.getDayOfWeek().name(), dayOfWeekId,newDate.getDateStr());
            BmsDayofweek dayOfWeek = new BmsDayofweek();
            dayOfWeek.setDayofweekId(new BigDecimal(dayOfWeekId));
            newDate.setBmsDayofweek(dayOfWeek);

            if (dayOfWeekId == 6 || dayOfWeekId == 7) {
                newDate.setIsdavailable(BigDecimal.ONE);
                newDate.setIsbusinessday(BigDecimal.ONE);
            } else {
                newDate.setIsdavailable(BigDecimal.ZERO);
                newDate.setIsbusinessday(BigDecimal.ZERO);
            }

            BmsMonthofyear monthOfYear = new BmsMonthofyear();
            monthOfYear.setMonthofyearId(new BigDecimal(currentDate.getMonth().getValue()));
            newDate.setBmsMonthofyear(monthOfYear);

            newDate.setIsholiday(BigDecimal.ONE);
            newDate.setIshalfday(BigDecimal.ONE);
            newDate.setCreatedDate(DateHelper.toDate());

            newDate = repo.saveCalendar(newDate);
            calendar.add(mapper.toCalendarDto(newDate));
        }
        CalendarResponse response = new CalendarResponse();
        response.setIsSuccessfully(true);
        response.setCalendar(calendar);
        return Mono.just(response);
    }



    public Mono<CalendarBean> getDate(String date) throws CalendarException{
        Utils.isNullOrEmpty(date, new InvalidInputException("Day is required"));
        return Mono.just(repo.getCalendarDateStr(date)
                .map(mapper::toCalendarDto)
                .orElseThrow(()->new CalendarException("No record found for the day "+date)));
    }


    public Mono<Response> setHoliday(String date, String holidayType, String name, String note)
            throws CalendarException   {

        Utils.isNullOrEmpty(date, new InvalidInputException("Day is required"));
        Utils.isNullOrEmpty(holidayType, new InvalidInputException("Holiday Type is required"));
        Utils.isNullOrEmpty(name, new InvalidInputException("name is required"));
        Utils.isNullOrEmpty(note, new InvalidInputException("Note is required"));
        Response response=new Response();
        Optional<BmsCalendar>calendar = repo.getCalendarDateStr(date);
        if(calendar.isPresent()) {

            if(holidayType.equals(Constants.HOLIDAY_TYPE[0])) {
                BmsCalendarHolidayFixed fixe=new BmsCalendarHolidayFixed();
                fixe.setHolidayId(calendar.get().getCalendarDate());
                fixe.setHolidayName(name);
                fixe.setHolidayStr(calendar.get().getDateStr());
                fixe.setIsAvailable(new BigDecimal(Constants.IS_AVAILABLE));
                BmsCalendarHolidayFixed fixeSaved=repo.saveCalendarHolidayFixed(fixe);
                if(fixeSaved!=null) {
                    BmsCalendar cal=calendar.get();
                    cal.setHolidayName(name);
                    cal.setHolidayNote(note);
                    cal.setIsdavailable(new BigDecimal(Constants.IS_NOT_AVAILABLE));
                    cal.setIsholiday(new BigDecimal(Constants.IS_AVAILABLE));
                    BmsCalendar calSaved=repo.saveCalendar(cal);
                    if(calSaved!=null) {
                        response.setIsSuccessfully(true);
                        return Mono.just(response);
                    }
                }

            }
            else if(holidayType.equals(Constants.HOLIDAY_TYPE[1])) {
                BmsCalendarHolidayMovable move=new BmsCalendarHolidayMovable();
                move.setHolidayId(calendar.get().getCalendarDate());
                move.setHolidayName(name);
                move.setHolidayStr(calendar.get().getDateStr());
                move.setIsAvailable(new BigDecimal(Constants.IS_AVAILABLE));
                BmsCalendarHolidayMovable moveSaved=repo.saveCalendarHolidayMovable(move);
                if(moveSaved!=null) {
                    BmsCalendar cal=calendar.get();
                    cal.setHolidayName(name);
                    cal.setHolidayNote(note);
                    cal.setIsdavailable(new BigDecimal(Constants.IS_NOT_AVAILABLE));
                    cal.setIsholiday(new BigDecimal(Constants.IS_AVAILABLE));
                    BmsCalendar calSaved=repo.saveCalendar(cal);
                    if(calSaved!=null) {
                        response.setIsSuccessfully(true);
                        return Mono.just(response);
                    }
                }
            }

        }
        return null;
    }



    public Mono<List<CalendarBean>> getMonthOfYear(BigDecimal month, BigDecimal year) throws CalendarException {
        Utils.isNullOrEmpty(month, new InvalidInputException("Month is required"));
        Utils.isNullOrEmpty(year, new InvalidInputException("Year is required"));
        return Mono.justOrEmpty( repo.getCalendar(year, month).stream()
                .map(c->mapper.toCalendarDto(c)).collect(Collectors.toList()));
    }


    public Mono<Response> addOfficeInInstitution(Mono<OfficeBean> request) throws CalendarException {

        return null;
    }


    public Mono<Response> addInstitution(Mono<InstitutionBean> request) throws CalendarException {

        return null;
    }


    public Mono<Response> addServiceInInstitution(Mono<ServiceBean> request) throws CalendarException {
        // TODO Auto-generated method stub
        return null;
    }


    public Mono<Response> addSetting(Mono<SettingBean> request){
        return request.map(mapper::toSetting)
                .flatMap(s->{
                    BmsSetting saved=repo.saveSetting(s);
                    Response response = new Response();
                    response.isSuccessfully(saved!=null);
                    return Mono.just(response);
                });
    }


    public Mono<SettingBean> getSetting(BigDecimal institutionId){
            Utils.isNullOrEmpty(institutionId, new InvalidInputException("InstitutionId is required"));
        return Mono.justOrEmpty(
                repo.getSetting(institutionId)
                        .map(mapper::toSettingDto)
                        .orElseThrow(()->new CalendarException("No record found for the institution "+institutionId))
        );
    }

}
