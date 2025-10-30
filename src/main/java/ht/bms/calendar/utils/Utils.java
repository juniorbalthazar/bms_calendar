package ht.bms.calendar.utils;

import java.lang.reflect.Method;
import java.time.Period;
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


    public static void DisplayClass(Object obj) throws Exception {
        Class<?> objClass = obj.getClass();

        System.out.println("Object Class :"+obj.getClass());
        Method[] Listmethods = objClass.getMethods();
        for(Method method : Listmethods) {
            if(method.getName().startsWith("get") &&
                    method.getParameterTypes().length == 0 &&
                    !void.class.equals(method.getReturnType())&&
                    !method.getName().equals("getClass")){
                //String value= (String) objClass.getMethod( method.getName(), new Class[] {}).invoke(obj, new Object[] {});
                System.out.println("###  "+method.getName() + ": " + objClass.getMethod( method.getName(), new Class[] {}).invoke(obj, new Object[] {}));
            }
        }
    }


   public static long getDayDiff(Period period) {
        long years = period.getYears();
        long months = period.getMonths();
        long days = period.getDays();

        return (years*365)+(months*30)+days;
    }

    public static int getWeekId(int weekId) {
        if ((weekId+1) ==8) {
            return 1;
        }
        return (weekId+1);
    }

}
