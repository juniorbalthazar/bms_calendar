package ht.bms.calendar.utils;


import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DateHelper 
{

	private static TimeZone utc = TimeZone.getTimeZone("UTC");


	public static Date toDatePlus30NextDay(){
		Calendar cal = Calendar.getInstance(utc);
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 30);
		return cal.getTime();
	}

	public static boolean  checkEarlyDate(Date date) {
		Date toDayPlus30 = toDatePlus30NextDay();
		if(date.compareTo(toDayPlus30)<=0) {
			//if the date is before or equal to today plus 30 days
			return true;
		}else {
			//the date is after today plus 30 days
			return false;
		}
	}

	public static boolean  checkDayPassDate(Date date) {
		Date toDay = toDate();
		if(date.compareTo(toDay)<0) {
			//if the date is before today
			return true;
		}else {
			//the date is today or after today
			return false;
		}
	}


	public static Date tomorrowDay(){
		Calendar cal = Calendar.getInstance(utc);
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}


	public static Date toDate(){
		Calendar cal = Calendar.getInstance(utc);
		cal.setTime(new Date());
		return cal.getTime();
	}

	public static String DateToString(Date date){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return myFormatObj.format(date.toInstant().atZone(utc.toZoneId()).toLocalDate());
	}

public static Date StringToDate(String date) {
    try {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.parse(date);
    } catch (ParseException e) {
        throw new IllegalArgumentException("Invalid date format. Expected format: dd-MM-yyyy", e);
    }
}
}
