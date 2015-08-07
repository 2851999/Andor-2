/*****************************************************************************
 *
 *   Copyright 2015 Joel Davies
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *****************************************************************************/

package org.andor.utils;

import java.util.GregorianCalendar;

public class Time {
	
	/* The different hour formats */
	public static final int FORMAT_12_HOUR = 1;
	public static final int FORMAT_24_HOUR = 2;
	
	/* The static method used to get the current time as a string,
	 * in the format, HH:MM:SS where the hour is in the 24 hour format */
	public static String getTimeHHMMSS() {
		//Return the time
		return getTimeHHMMSS(":", FORMAT_24_HOUR);
	}
	
	/* The static method used to get the current time as a string,
	 * in the format, HH:MM:SS where the hour is in the format specified */
	public static String getTimeHHMMSS(int hourFormat) {
		//Return the time
		return getTimeHHMMSS(":", hourFormat);
	}
	
	/* The static method used to get the current time as a string,
	 * in the format, HH MM SS where each part is separated by a
	 * certain string and the hour is in the format specified */
	public static String getTimeHHMMSS(String separator, int hourFormat) {
		//Get an instance of the gregorian calendar
		GregorianCalendar calendar = getCalendar();
		//Get each part of the time
		int hour = getHour(calendar, hourFormat);
		int minute = getMinute(calendar);
		int second = getSecond(calendar);
		//Return the time
		return "" + hour + separator + minute + separator + second;
	}
	
	/* The static method used to get the current hour (Given a Format)
	 * Supply a calendar, because in the case of getting the full
	 * time, the minute might change in between the time it takes
	 * to return a value, making it inaccurate */
	public static int getHour(GregorianCalendar calendar, int format) {
		//Check the given format
		if (format == FORMAT_12_HOUR)
			return getHour12(calendar);
		else if (format == FORMAT_24_HOUR)
			return getHour24(calendar);
		else {
			//Log an error under information
			Logger.log("Andor - Time", "The hour format " + format + " does not match any predefined format", Log.ERROR);
			//The format is undefined, so return 0
			return 0;
		}
	}
	
	/* The static method used to get the current hour (24 Hour Format)
	 * Supply a calendar, because in the case of getting the full
	 * time, the minute might change in between the time it takes
	 * to return a value, making it inaccurate */
	public static int getHour24(GregorianCalendar calendar) {
		return calendar.get(GregorianCalendar.HOUR_OF_DAY);
	}
	
	/* The static method used to get the current hour (12 Hour Format)
	 * Supply a calendar, because in the case of getting the full
	 * time, the minute might change in between the time it takes
	 * to return a value, making it inaccurate */
	public static int getHour12(GregorianCalendar calendar) {
		return calendar.get(GregorianCalendar.HOUR);
	}
	
	/* The static method used to get the current minutes
	 * Supply a calendar, because in the case of getting the full
	 * time, the minute might change in between the time it takes
	 * to return a value, making it inaccurate */
	public static int getMinute(GregorianCalendar calendar) {
		return calendar.get(GregorianCalendar.MINUTE);
	}
	
	/* The static method used to get the current second
	 * Supply a calendar, because in the case of getting the full
	 * time, the minute might change in between the time it takes
	 * to return a value, making it inaccurate */
	public static int getSecond(GregorianCalendar calendar) {
		return calendar.get(GregorianCalendar.SECOND);
	}
	
	/* The static method used to get the current time in milliseconds */
	public static long getMiliseconds() {
		return System.currentTimeMillis();
	}
	
	/* The static method used to get the current gregorian calendar */
	public static GregorianCalendar getCalendar() {
		//Return a new calendar
		return new GregorianCalendar();
	}
	
}