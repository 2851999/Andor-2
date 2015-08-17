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

import org.andor.core.Colour;

public class MathUtils {
	
	/* The static method used to interpolate between two values */
	public static float interpolate(float a, float b, float t) {
		return ((b * t) + ((1 - t) * a));
	}
	
	/* The static method used to clamp a value given the minimum and maximum values it can be */
	public static float clamp(float value, float min, float max) {
		//Check the value against the min/max
		if (value < min)
			value = min;
		else if (value > max)
			value = max;
		//Return the clamped value
		return value;
	}
	
	/* The static method used to clamp a colour */
	public static Colour clamp(Colour colour, float min, float max) {
		//Check the values
		if (colour.r < min)
			colour.r = min;
		else if (colour.r > max)
			colour.r = max;
		
		if (colour.g < min)
			colour.g = min;
		else if (colour.g > max)
			colour.g = max;
		
		if (colour.b < min)
			colour.b = min;
		else if (colour.b > max)
			colour.b = max;
		
		if (colour.a < min)
			colour.a = min;
		else if (colour.a > max)
			colour.a = max;
		//Return the colour
		return colour;
	}
	
	/* The static method used to clamp a colour */
	public static Colour clamp(Colour colour) {
		return clamp(colour, 0.0f, 1.0f);
	}
	
	/* Various maximum / minimum functions */
	public static int min(int v1, int v2) {
		if (v1 < v2)
			return v1;
		else
			return v2;
	}
	
	public static float min(float v1, float v2) {
		if (v1 < v2)
			return v1;
		else
			return v2;
	}
	
	public static int max(int v1, int v2) {
		if (v1 > v2)
			return v1;
		else
			return v2;
	}
	
	public static float max(float v1, float v2) {
		if (v1 > v2)
			return v1;
		else
			return v2;
	}
	
}