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

public class Mouse {
	
	/* The last mouse position */
	public static double lastX = -1;
	public static double lastY = -1;
	
	/* The booleans that state whether a particular button is currently pressed */
	public static boolean leftButtonDown = false;
	public static boolean middleButtonDown = false;
	public static boolean rightButtonDown = false;
	
	/* The boolean that states whether the cursor is currently locked to the window */
	private static boolean cursorLocked = false;
	
	/* The static method used to get the state of a button on the mouse */
	public static boolean isPressed(int button) {
		int state = GLFW.glfwGetMouseButton(Window.getInstance(), button);
		return state == GLFW.GLFW_PRESS || (Settings.Input.MouseEventsRepeat && state == GLFW.GLFW_REPEAT);
	}
	
	/* The static method used to set the position of the cursor */
	public static void setPosition(float x, float y) {
		//Set the cursor position
		GLFW.glfwSetCursorPos(Window.getInstance(), x, y);
	}
	
	/* The static method used to centre the mouse into the middle of the screen */
	public static void centre() {
		//Set the cursor position into the middle of the screen
		setPosition(Settings.Window.Width / 2, Settings.Window.Height);
	}
	
	/* The static method used to lock the cursor to the window by disabling it */
	public static void lock() {
		//Disable the cursor
		GLFW.glfwSetInputMode(Window.getInstance(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		cursorLocked = true;
	}
	
	/* The static method used to unlock the cursor from the window by enabling it */
	public static void unlock() {
		//Set the cursor back to normal
		GLFW.glfwSetInputMode(Window.getInstance(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
		cursorLocked = false;
		//Set the cursor's position into the centre of the window
		centre();
	}
	
	/* The static method used to check whether the mouse is currently locked */
	public static boolean isLocked() { return cursorLocked; }
	
}