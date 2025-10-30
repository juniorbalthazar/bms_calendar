package ht.bms.calendar.service.handles;

import ht.bms.calendar.exception.CalendarException;
import ht.bms.calendar.model.*;
import ht.bms.calendar.service.facade.CalendarService;
import ht.bms.calendar.service.validator.RequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
`
@Service
public class CalendarHandler {

    private CalendarService calendarService;

    public CalendarHandler(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public Mono<ServerResponse> allAvailableDayFrom30Day(ServerRequest request) {
        var officeId = request.queryParam("officeId").orElse(null);
        var serviceId = request.queryParam("serviceId").orElse(null);
        var dateOfficeCapacity = request.queryParam("dateOfficeCapacity").orElse(null);

        return this.calendarService.allAvailableDayFrom30Day(new BigDecimal(officeId),new BigDecimal(serviceId),dateOfficeCapacity)
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> isAvailableDay(ServerRequest request) {
        var officeId = request.queryParam("officeId").orElse(null);
        var serviceId = request.queryParam("serviceId").orElse(null);
        var dateOfficeCapacity = request.queryParam("dateOfficeCapacity").orElse(null);
        var day = request.queryParam("day").orElse(null);
        return this.calendarService.isAvailableDay(day,new BigDecimal(officeId),new BigDecimal(serviceId),dateOfficeCapacity)
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> checkAvailableDay(ServerRequest request) {
        var officeId = request.queryParam("officeId").orElse(null);
        var serviceId = request.queryParam("serviceId").orElse(null);
        var dateOfficeCapacity = request.queryParam("dateOfficeCapacity").orElse(null);

        return this.calendarService.checkAvailableDay(new BigDecimal(officeId),new BigDecimal(serviceId),dateOfficeCapacity)
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> setCalendar(ServerRequest request){
        var officeId = request.queryParam("year").orElse(null);
        var serviceId = request.queryParam("numberOfYear").orElse(null);
        return this.calendarService.setCalendar(Integer.valueOf(officeId),Integer.valueOf(serviceId))
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> addServiceInInstitution(ServerRequest request) {
        return request.bodyToMono(OfficeBean.class)
                .transform(RequestValidator.validateOffice())
                .as(calendarService::addOfficeInInstitution)
                .flatMap(ServerResponse.ok()::bodyValue);
    }
    public Mono<ServerResponse> addInstitution(ServerRequest request) {
        return request.bodyToMono(InstitutionBean.class)
                .transform(RequestValidator.validateInstitution())
                .as(calendarService::addInstitution)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> setHoliday(ServerRequest request) {
        var date = request.queryParam("date").orElse(null);
        var holidayType = request.queryParam("holidayType").orElse(null);
        var name = request.queryParam("name").orElse(null);
        var note = request.queryParam("note").orElse(null);
        return this.calendarService.setHoliday(date, holidayType, name, note)
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getDate(ServerRequest request) {
        var date = request.queryParam("date").orElse(null);
        return calendarService.getDate(date)
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getMonthOfYear(ServerRequest request) {
        var month = request.queryParam("month").orElse(null);
        var year = request.queryParam("year").orElse(null);
        return calendarService.getMonthOfYear(new BigDecimal(month),new BigDecimal(year))
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addSetting(ServerRequest request) {
        return request.bodyToMono(SettingBean.class)
                .transform(RequestValidator.validateSetting())
                .as(calendarService::addSetting)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> getSetting(ServerRequest request) {
        var institutionId = request.queryParam("institutionId").orElse(null);
        return calendarService.getSetting(new BigDecimal(institutionId))
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
