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

import java.util.List;

public class ParserUtils {
	
	/* The static method used to remove any whitespace from the beginning and end of parameters */
	public static void removeWhitespace(String[] parameters) {
		//Remove the whitespace
		for (int a = 0; a < parameters.length; a++)
			//Clean up the current line
			parameters[a] = parameters[a].trim();
	}
	
	/* The static method for calculating a string */
	public static String getString(String s) {
		//Return the string
		return s.substring(1, s.length() - 1);
	}
	
	/* The static method used to clean up some code */
	public static String[] cleanUp(String[] code) {
		//Convert the array to a list
		List<String> list = ArrayUtils.toStringList(code);
		//Go through each line
		for (int a= 0; a < list.size(); a++) {
			//Clean up the current line
			list.set(a, list.get(a).trim());
			//Check the current line to make sure it isn't empty
			if (list.get(a).equals(""))
				//Remove the current line
				list.remove(a);
		}
		//Return the cleaned up code
		return ArrayUtils.toStringArray(list);
	}
	
}