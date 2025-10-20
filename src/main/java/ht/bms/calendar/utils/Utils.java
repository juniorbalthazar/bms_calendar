package ht.bms.calendar.utils;

import java.util.Optional;

public class Utils {

    public static void isNullOrEmpty(Object str, Exception exception){
        if ( Optional.ofNullable(str).isEmpty() )
            try {
                throw new Exception(exception.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

}
