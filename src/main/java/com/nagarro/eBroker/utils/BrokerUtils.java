package com.nagarro.eBroker.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class BrokerUtils {
	final static String startTime = "09:00:00";
	final static String endTime = "17:00:00";

	private BrokerUtils() {
	}

	public static boolean timeAndDayCheck() {
		LocalDate today = LocalDate.now();
		LocalTime target = LocalTime.now();
		Boolean targetInZone = (target.isAfter(LocalTime.parse(startTime))
				&& target.isBefore(LocalTime.parse(endTime)));
		return isWeekday(today) && targetInZone;
	}

	public static boolean isWeekday(final LocalDate ld) {
		DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
		boolean weekend = (day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY);
		return !weekend;
	}
}
