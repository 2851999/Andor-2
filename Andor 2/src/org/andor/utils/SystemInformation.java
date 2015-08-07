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

public class SystemInformation {
	
	/* The static method to get the number of available processors */
	public static int getAvailableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}
	
	/* The static method to get the total memory */
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}
	
	/* The static method to get the free memory */
	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}
	
	/* The static method to get the maximum memory that can be used */
	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}
	
	/* The static method used to get the current user's username */
	public static String getUsername() {
		return System.getenv("USERNAME");
	}
	
	/* The static method used to get the java class path */
	public static String getJavaClassPath() {
		return System.getProperty("java.class.path");
	}
	
	/* The static method used to get the java home */
	public static String getJavaHome() {
		return System.getProperty("java.home");
	}
	
	/* The static method used to get the java vendor */
	public static String getJavaVendor() {
		return System.getProperty("java.vendor");
	}
	
	/* The static method used to get the java vendor url */
	public static String getJavaVendorURL() {
		return System.getProperty("java.vendor.url");
	}
	
	/* The static method used to get the java version */
	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}
	
	/* The static method used to get the os architecture */
	public static String getOSArchitecture() {
		return System.getProperty("os.arch");
	}
	
	/* The static method used to get the os name */
	public static String getOSName() {
		return System.getProperty("os.name");
	}
	
	/* The static method used to get the os version */
	public static String getOSVersion() {
		return System.getProperty("os.version");
	}
	
	/* The static method used to get the users working directory */
	public static String getUserDirectory() {
		return System.getProperty("user.dir");
	}
	
	/* The static method used to get the users home directory */
	public static String getUserHome() {
		return System.getProperty("user.home");
	}
	
	/* The static method used to get the users home directory */
	public static String getUserAccountName() {
		return System.getProperty("user.name");
	}
	
	/* The static method used to get the users home directory */
	public static String getUserAppData() {
		return System.getenv("AppData");
	}
	
}