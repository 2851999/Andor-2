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

public class ControllerAxis {
	
	/* The controller this axis is from */
	private Controller controller;
	
	/* The index of this axis */
	private int index;
	
	/* The current value on this axis */
	private float value;
	
	/* The constructor */
	public ControllerAxis(Controller controller, int index) {
		this.controller = controller;
		this.index = index;
		this.value = 0;
	}
	
	/* The method used to check the state of this button */
	public void checkInput() {
		//Get the current value
		float currentValue = GLFW.glfwGetJoystickAxes(this.controller.getIndex()).get(this.index);
		//Check the current value against the last recorded value
		if (this.value != currentValue) {
			//Update the current value
			this.value = currentValue;
			InputManager.callOnControllerAxisChange(this);
		}
	}
	
	/* The method used to check whether this axis is the same as another */
	public boolean equals(ControllerAxis other) {
		return this.getIndex() == other.getIndex() && other.getController().getIndex() == this.controller.getIndex();
	}
	
	/* The setters and getters */
	public Controller getController() { return this.controller; }
	public int getIndex() { return this.index; }
	public float getValue() { return this.value; }
	
}