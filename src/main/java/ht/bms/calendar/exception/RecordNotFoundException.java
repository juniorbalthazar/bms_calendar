package ht.bms.calendar.exception;

public class RecordNotFoundException extends RuntimeException {
    private static final String  MESSAGE = "Record [msg=%s] not found";

    public RecordNotFoundException(String msg){
        super(String.format(MESSAGE, msg));
    }
}
