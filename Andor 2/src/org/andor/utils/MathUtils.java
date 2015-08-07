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

public class MathUtils {
	
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