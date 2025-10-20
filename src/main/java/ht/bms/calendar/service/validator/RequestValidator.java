package ht.bms.calendar.service.validator;

import ht.bms.calendar.exception.ApplicationExceptions;
import ht.bms.calendar.model.InstitutionBean;
import ht.bms.calendar.model.OfficeBean;
import ht.bms.calendar.model.ServiceBean;
import ht.bms.calendar.model.SettingBean;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<SettingBean>> validateSetting(){
        return dto-> dto.filter(e-> Objects.nonNull(e.getEndHours()) && !e.getEndHours().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("End Hours is missing "))
                .filter(e-> Objects.nonNull(e.getInstitutionId()))
                .switchIfEmpty(ApplicationExceptions.missingField("Institution Id is missing"))
                .filter(e-> Objects.nonNull(e.getExpirationCertificat()))
                .switchIfEmpty(ApplicationExceptions.missingField("ExpirationCertificat date is missing"))
                .filter(e-> Objects.nonNull(e.getMaxTimeTxBeforePay()))
                .switchIfEmpty(ApplicationExceptions.missingField("MaxTimeTxBefore is missing"))
                .filter(e-> Objects.nonNull(e.getNbreTransPerApplicant()))
                .switchIfEmpty(ApplicationExceptions.missingField("NbreTransPerApplicant"))
                .filter(e-> Objects.nonNull(e.getOfficeCapacity()) && !e.getStartHours().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("OfficeCapacity"))
                .filter(e-> Objects.nonNull(e.getSendEmailTransaction()))
                .switchIfEmpty(ApplicationExceptions.missingField("SendEmailTransaction"))
                .filter(e-> Objects.nonNull(e.getSmsMesageBody()) && !e.getSmsMesageBody().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("SmsMesageBody")
                        );
    }

    public static UnaryOperator<Mono<OfficeBean>> validateOffice() {
        return dto -> dto.filter(e -> Objects.nonNull(e.getOfficeName()) && !e.getOfficeName().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("Name is missing "))
                .filter(e -> Objects.nonNull(e.getBmsInstitutionBean()) && Objects.nonNull(e.getBmsInstitutionBean().getInstitutionId()))
                .switchIfEmpty(ApplicationExceptions.missingField("Institution Id is missing "))
                .filter(e-> Objects.nonNull(e.getIsCentral()))
                .switchIfEmpty(ApplicationExceptions.missingField("IsCentral is missing, 0==true and 1=false"));
    }

    public static UnaryOperator<Mono<InstitutionBean>> validateInstitution() {
        return dto -> dto.filter(e -> Objects.nonNull(e.getFullName()) && !e.getFullName().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("FullName is missing "))
                .filter(e -> Objects.nonNull(e.getEmail()) && !e.getEmail().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("Email is missing "))
                .filter(e -> Objects.nonNull(e.getPhone()) && !e.getPhone().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("Phone is missing "))
                .filter(e -> Objects.nonNull(e.getShortName()) && !e.getShortName().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("ShortName is missing "));
    }

    public static UnaryOperator<Mono<ServiceBean>> validateService() {
        return dto -> dto.filter(e -> Objects.nonNull(e.getFullName()) && !e.getFullName().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("FullName is missing "))
                .filter(e -> Objects.nonNull(e.getShortName()) && !e.getShortName().isEmpty())
                .switchIfEmpty(ApplicationExceptions.missingField("ShortName is missing "));
    }
}
