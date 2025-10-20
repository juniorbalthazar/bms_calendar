package ht.bms.calendar.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptions  {

    private static final String  MESSAGE_FIELD = "[name=%s]  is missing";
    public static <T>Mono<T>  recordNotFound(String message){
        return Mono.error(new RecordNotFoundException(message));
    }

    public static <T>Mono<T>  missingUsername(){
        return Mono.error(new InvalidInputException("Username is missing"));
    }

    public static <T>Mono<T>  missingPassword(){
        return Mono.error(new InvalidInputException("Password is missing"));
    }

    public static <T>Mono<T>  missingField(String name){
        return Mono.error(new InvalidInputException(String.format(MESSAGE_FIELD, name)));
    }
}
