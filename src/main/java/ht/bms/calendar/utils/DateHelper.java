package ht.bms.calendar.utils;


import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateHelper 
{
	
	
	
	private static final Locale currentLocale = new Locale("fr","FR");
	private static TimeZone utc = TimeZone.getTimeZone("UTC");
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd",currentLocale);

	public static Date toDayPlus30(){
		Calendar cal = Calendar.getInstance(utc);
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 30);
		return cal.getTime();
	}

	public static Date tomorrowDay(){
		Calendar cal = Calendar.getInstance(utc);
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
}
