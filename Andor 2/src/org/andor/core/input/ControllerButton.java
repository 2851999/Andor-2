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

import org.lwjgl.glfw.GLFW;

public class ControllerButton {
	
	/* The controller this button is from */
	private Controller controller;
	
	/* The index of this button */
	private int index;
	
	/* The current state of this button */
	private boolean pressed;
	
	/* The constructor */
	public ControllerButton(Controller controller, int index) {
		this.controller = controller;
		this.index = index;
		this.pressed = false;
	}
	
	/* The method used to check the state of this button */
	public void checkInput() {
		//Check the current state
		if ((GLFW.glfwGetJoystickButtons(controller.getIndex()).get(this.index) == GLFW.GLFW_PRESS) != this.pressed) {
			//Update the current state
			this.pressed = ! this.pressed;
			//Check the new value
			if (this.pressed)
				InputManager.callOnControllerButtonPressed(this);
			else
				InputManager.callOnControllerButtonReleased(this);
		}
	}
	
	/* The method used to check whether this button is the same as another */
	public boolean equals(ControllerButton other) {
		return this.getIndex() == other.getIndex() && other.getController().getIndex() == this.controller.getIndex();
	}
	
	/* The setters and getters */
	public Controller getController() { return this.controller; }
	public int getIndex() { return this.index; }
	public boolean isPressed() { return this.pressed; }
	
}