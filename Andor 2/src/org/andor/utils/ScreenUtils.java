/* *********************************************
 * ANDOR
 * 
 * USE - EDUCATIONAL PURPOSES ONLY
 *
 * COPYRIGHT @ 2014-2015
 **********************************************/

package org.andor.utils;

import java.awt.Toolkit;

public class ScreenUtils {
	
	/* The static  method to get the current screens width */
	public static int getScreenWidth() {
		//Return the displays width
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	/* The static method to get the current screens height */
	public static int getScreenHeight() {
		//Return the displays height
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}
	
}