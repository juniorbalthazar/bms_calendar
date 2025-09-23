package ht.bms.calendar.exception;

public class CalendarException extends RuntimeException{
    private static final String  MESSAGE = "Calendar [msg=%s] not found";

    public CalendarException(String msg){
        super(String.format(MESSAGE, msg));
    }
}
