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

import java.util.ArrayList;
import java.util.List;

public class Logger {
	
	/* The booleans that state whether each type of
	 * log should be enabled */
	public static boolean debugEnabled = true;
	public static boolean informationEnabled = true;
	public static boolean warningEnabled = true;
	public static boolean errorEnabled = true;
	
	/* The booleans that state whether each type of
	 * log should be recorded */
	public static boolean recordDebug = false;
	public static boolean recordInformation = false;
	public static boolean recordWarning = false;
	public static boolean recordError = false;
	
	/* The booleans that state whether each part of a
	 * log should be enabled */
	public static boolean timeEnabled = true;
	public static boolean sourceEnabled = true;
	public static boolean typeEnabled = true;
	
	/* The lists that record each type of log */
	private static List<String> logs = new ArrayList<String>();
	
	/* The static method used to quickly print a debug message */
	public static void debug(String message) {
		//Log the log
		log(new Log("DEBUG MESSAGE", message));
	}
	
	/* The static methods used to log a certain type message */
	public static void error(String source, String message) {
		//Log the log
		log(new Log(source, message, Log.ERROR));
	}
	
	/* The static methods used to log a certain type message */
	public static void warning(String source, String message) {
		//Log the log
		log(new Log(source, message, Log.WARNING));
	}
	
	/* The static method used to log a message
	 * assumes the log is supposed to be a debug */
	public static void log(String source, String message) {
		//Log the log
		log(new Log(source, message));
	}
	
	/* The static method used to log something */
	public static void log(String source, String message, int logType) {
		//Log the log
		log(new Log(source, message, logType));
	}
	
	/* The static method used to log something */
	public static void log(Log log) {
		//Get the logs message
		String message = log.getStringMessage();
		//Check to see whether the log type is enabled
		if (isEnabled(log.type))
			//Print the message
			System.out.println(message);
		//Check to see whether the log should be recorded
		if (shouldRecord(log.type))
			//Add the message to the logs
			logs.add(message);
	}
	
	/* The static method used to check if a log type is enabled */
	public static boolean isEnabled(int type) {
		//Check the log type
		if (type == Log.DEBUG)
			return debugEnabled;
		else if (type == Log.INFORMATION)
			return informationEnabled;
		else if (type == Log.WARNING)
			return warningEnabled;
		else if (type == Log.ERROR)
			return errorEnabled;
		else
			return false;
	}
	
	/* The static method used to check if a log type should be recorded enabled */
	public static boolean shouldRecord(int type) {
		//Check the log type
		if (type == Log.DEBUG)
			return recordDebug;
		else if (type == Log.INFORMATION)
			return recordInformation;
		else if (type == Log.WARNING)
			return recordWarning;
		else if (type == Log.ERROR)
			return recordError;
		else
			return false;
	}
	
	/* The static method used to clear the logs */
	public static void clearLogs() {
		logs.clear();
	}
	
	/* The static method used to enable the recording of all logs */
	public static void recordLogs() {
		recordDebug = true;
		recordInformation = true;
		recordWarning = true;
		recordError = true;
	}
	
	/* The static method used to disable the recording of all logs */
	public static void stopRecordingLogs() {
		recordDebug = false;
		recordInformation = false;
		recordWarning = false;
		recordError = false;
	}
	
}