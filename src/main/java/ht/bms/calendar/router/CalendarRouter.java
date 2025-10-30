package ht.bms.calendar.router;

import ht.bms.calendar.service.handles.CalendarHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class CalendarRouter {

    private final CalendarHandler calendarHandler;

    public CalendarRouter(CalendarHandler calendarHandler) {
        this.calendarHandler = calendarHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> calendarRoutes(){
        return RouterFunctions.route()
                .path("/api/v1/calendar", builder -> builder
                        .add(baseRoute(RequestPredicates.GET("/allAvailableDayFrom30Day")
                                .and(queryParam("officeId", t -> true))
                                .and(queryParam("serviceId", t -> true))
                                .and(queryParam("dateOfficeCapacity", t -> true)),calendarHandler::allAvailableDayFrom30Day))
                        .add(baseRoute(RequestPredicates.GET("/isAvailableDay")
                                .and(queryParam("day", t -> true))
                                .and(queryParam("officeId", t -> true))
                                .and(queryParam("serviceId", t -> true))
                                .and(queryParam("dateOfficeCapacity", t -> true)),calendarHandler::isAvailableDay))
                        .add(baseRoute(RequestPredicates.GET("/checkAvailableDay")
                                .and(queryParam("officeId", t -> true))
                                .and(queryParam("serviceId", t -> true))
                                .and(queryParam("dateOfficeCapacity", t -> true)),calendarHandler::checkAvailableDay))
                        .add(baseRoute(RequestPredicates.GET("/setCalendar")
                                .and(queryParam("year", t -> true))
                                .and(queryParam("numberOfYear", t -> true)),calendarHandler::setCalendar))
                        .add(baseRoute(RequestPredicates.GET("/getDate")
                                .and(queryParam("date", t -> true)),calendarHandler::getDate))
                        .add(baseRoute(RequestPredicates.POST("/setHoliday")
                                .and(queryParam("date", t -> true))
                                .and(queryParam("holidayType", t -> true))
                                .and(queryParam("name", t -> true))
                                .and(queryParam("note", t -> true)),calendarHandler::setHoliday))
                        .add(baseRoute(RequestPredicates.GET("/getMonOfYear")
                                .and(queryParam("year", t -> true))
                                .and(queryParam("month", t -> true)),calendarHandler::getMonthOfYear))
                )//.onError(exceptionHandler::handleException)
                .path("/api/v1/setting", builder -> builder
                        .add(baseRoute(RequestPredicates.GET("/getSetting")
                        .and(queryParam("institutionId", t -> true)),calendarHandler::getSetting))
                        .add(baseRoute(RequestPredicates.POST("/addSetting"), calendarHandler::addSetting))
                        .add(baseRoute(RequestPredicates.POST("/addInstitution"),calendarHandler::addInstitution))
                     //   .add(baseRoute(RequestPredicates.POST("/addOfficeInInstitution"),calendarHandler::addOfficeInInstitution))
                    //    .add(baseRoute(RequestPredicates.POST("/addServiceInInstitution"),calendarHandler::addServiceInInstitution))
                )
                .build();
    }


    private RouterFunction<ServerResponse> baseRoute(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction) {
        return route(predicate, handlerFunction);
    }
}

