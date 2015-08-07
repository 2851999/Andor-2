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

package org.andor.core.input;

import org.andor.Settings;
import org.andor.core.Window;
import org.lwjgl.glfw.GLFW;

public class Keyboard {
	
	/* The static method used to check whether a specific key is pressed */
	public static boolean isPressed(int key) {
		int state = GLFW.glfwGetKey(Window.getInstance(), key);
		return state == GLFW.GLFW_PRESS || (Settings.Input.KeyboardEventsRepeat && state == GLFW.GLFW_REPEAT);
	}
	
}