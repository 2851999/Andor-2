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

public class MaskUtils {
	
	/* The static method used to mask a string given the string and the mask */
	public static String mask(String string, String mask) {
		//The masked string
		String maskedString = "";
		//Get the length of the string
		int stringLength = string.length();
		//Iterate the same number as there are letters in the string
		for (int a = 0; a < stringLength; a++)
			//Add on the mask to the masked string
			maskedString += mask;
		//Return the masked string
		return maskedString;
	}
	
}