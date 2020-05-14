package com.salezshark.emailtemplate.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtility {

	public static final String DEFAULT_DATE_FORMAT_REVERSE = "yyyy-MM-dd";
	public static final String DEFAULT_HOUR_MINUTE_SECOND_FORMAT_AM_PM = "hh:mm:ss a";
	public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
	public static final String DATE_FORMAT_DD_MMM_YYYY = "dd MMM yyyy";
	public static final String DEFAULT_DATE_FORMAT_REVERSE_AM_PM = String.format("%s %s", DATE_FORMAT_DD_MMM_YYYY, DEFAULT_HOUR_MINUTE_SECOND_FORMAT_AM_PM);
	
	
	/**
	 * convertFormTimstampToDD_MMM_YYYYFormat
	 * @param timestamp
	 * @return
	 */
	public static String convertFormTimstampToDD_MMM_YYYYFormat(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT_REVERSE_AM_PM));
	}

}
