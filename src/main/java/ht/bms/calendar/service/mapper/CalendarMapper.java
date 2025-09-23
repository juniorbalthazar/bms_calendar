package ht.bms.calendar.service.mapper;

import ht.bms.calendar.domain.BmsCalendar;
import ht.bms.calendar.model.CalendarBean;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CalendarMapper {

    CalendarMapper INSTANCE = Mappers.getMapper( CalendarMapper.class );
    @Mapping(source = "dayofmonth",target = "dayOfMonth",defaultValue ="")
    @Mapping(source = "isbusinessday",target = "isBusinessDay",defaultValue ="")
    @Mapping(source = "isdavailable",target = "isDateAvailable",defaultValue ="")
    @Mapping(source = "ishalfday",target = "isHalfDay",defaultValue ="")
    @Mapping(source = "isholiday",target = "isHolyDay",defaultValue ="")
    @Mapping(source = "bmsDayofweek.dayofweekName",target ="dayOfWeek" )
    @Mapping(source = "bmsMonthofyear.monthofyearName",target ="monthOfYear" )
    CalendarBean toCalendarDto(BmsCalendar calendar);
}

