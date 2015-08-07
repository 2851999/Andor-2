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

import java.util.ArrayList;
import java.util.List;

import org.andor.core.Vector2f;

public class InputManager {
	
	/* The added controllers */
	private static List<Controller> controllers = new ArrayList<Controller>();
	
	/* The input listeners */
	private static List<InputListenerInterface> listeners = new ArrayList<InputListenerInterface>();
	
	/* The static method used to check for any change in the input system */
	public static void checkInput() {
		//Go through all of the controllers and check for any changes in them
		for (int a = 0; a < controllers.size(); a++)
			controllers.get(a).checkInput();
	}
	
	/* The static method used to add an input listener */
	public static void addListener(InputListenerInterface listener) {
		listeners.add(listener);
	}
	
	/* The static method used to remove an input listener */
	public static void removeListener(InputListenerInterface listener) {
		listeners.remove(listener);
	}
	
	/* The static method used to add a controller */
	public static void addController(Controller controller) {
		controllers.add(controller);
	}
	
	/* The static method used to add a controller given its index */
	public static void addController(int index) {
		controllers.add(new Controller(index));
	}
	
	/* The static method used by the engine to call a key event */
	public static void callOnKeyPressed(int code) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onKeyPressed(code);
	}
	
	public static void callOnKeyReleased(int code) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onKeyReleased(code);
	}
	
	public static void callOnKeyTyped(int code) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onKeyTyped(code);
	}
	
	public static void callOnChar(int code, char character) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onChar(code, character);
	}
	
	public static void callOnMousePressed(int button) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onMousePressed(button);
	}
	
	public static void callOnMouseReleased(int button) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onMouseReleased(button);
	}
	
	public static void callOnMouseClicked(int button) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onMouseClicked(button);
	}
	
	public static void callOnMouseMoved(double x, double y, double xOffset, double yOffset) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onMouseMoved(x, y, xOffset, yOffset);
	}
	
	public static void callOnMouseDragged(double x, double y, double xOffset, double yOffset) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onMouseDragged(x, y, xOffset, yOffset);
	}
	
	public static void callOnMouseEnter() {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onMouseEnter();
	}
	
	public static void callOnMouseLeave() {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onMouseLeave();
	}
	
	public static void callOnScroll(double xOffset, double yOffset) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onScroll(xOffset, yOffset);
	}
	
	public static void callOnWindowSizeChanged(Vector2f oldSize, Vector2f newSize) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onWindowSizeChanged(oldSize, newSize);
	}
	
	public static void callOnControllerButtonPressed(ControllerButton button) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onControllerButtonPressed(button);
	}
	
	public static void callOnControllerButtonReleased(ControllerButton button) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onControllerButtonReleased(button);
	}
	
	public static void callOnControllerAxisChange(ControllerAxis axis) {
		//Call the event in the listeners
		for (int a = 0; a < listeners.size(); a++)
			listeners.get(a).onControllerAxisChange(axis);
	}
	
}