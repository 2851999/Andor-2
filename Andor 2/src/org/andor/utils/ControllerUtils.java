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

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class ControllerUtils {
	
	/* The static method used to check whether a specific joystick is present */
	public static boolean isPresent(int controller) {
		return GLFW.glfwJoystickPresent(controller) == GL11.GL_TRUE;
	}
	
	/* The static method used to return a list of the controller indices that point to available controllers */
	public static int[] listAvailableControllerIndices(int maxNumber) {
		//The list of available controller indices
		List<Integer> availableIndices = new ArrayList<Integer>();
		//Go through the available controller spaces
		for (int a = 0; a < maxNumber; a++) {
			if (GLFW.glfwJoystickPresent(a) == GL11.GL_TRUE)
				availableIndices.add(a);
		}
		//Return the list of available controllers
		return ArrayUtils.toIntegerArray(availableIndices);
	}
	
}