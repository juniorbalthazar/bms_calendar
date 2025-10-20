package ht.bms.calendar.service.mapper;

import ht.bms.calendar.domain.BmsCalendar;
import ht.bms.calendar.domain.BmsOffice;
import ht.bms.calendar.domain.BmsService;
import ht.bms.calendar.domain.BmsSetting;
import ht.bms.calendar.model.CalendarBean;
import ht.bms.calendar.model.OfficeBean;
import ht.bms.calendar.model.ServiceBean;
import ht.bms.calendar.model.SettingBean;
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



    @Mapping(source = "osId",target = "serviceId",defaultValue ="")
    @Mapping(source = "isServiceAvailable",target = "isAvailable",defaultValue ="")
    @Mapping(source = "serviceFullName",target = "fullName",defaultValue ="")
    @Mapping(source = "serviceShortName",target = "shortName",defaultValue ="")
    ServiceBean toServiceDto(BmsService service);


    @Mapping(source = "institutionId",target = "bmsInstitutionBean.institutionId",defaultValue ="")
    OfficeBean toOfficeDto(BmsOffice office);



 /*   @Mapping(source = "nbreTransPerApplicant",target = "nbreTransPerApplicant",defaultValue ="")
    @Mapping(source = "officeCapacity",target = "officeCapacity",defaultValue ="")
    @Mapping(source = "startHours",target = "startHours",defaultValue ="")
    @Mapping(source = "endHours",target = "endHours",defaultValue ="")
    @Mapping(source = "expirationCertificat",target ="expirationCertificat" )
    @Mapping(source = "sendEmailTransaction",target ="sendEmailTransaction" )
    @Mapping(source = "maxTimeTxBeforePay",target ="maxTimeTxBeforePay" )
    @Mapping(source = "smsMesageBody",target ="smsMesageBody" )
    @Mapping(source = "institutionId",target ="institutionId" )*/
    @Mapping(source = "id",target = "settingId",defaultValue ="")
    SettingBean toSettingDto(BmsSetting setting);

    BmsSetting toSetting(SettingBean setting);


}

