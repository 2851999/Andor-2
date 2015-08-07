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

public class Controller {
	
	/* The index of this controller */
	private int index;
	
	/* The list of buttons in this controller */
	private ControllerButton[] buttons;
	
	/* The list of axes in this controller */
	private ControllerAxis[] axes;
	
	/* The constructor */
	public Controller(int index) {
		this.index = index;
		//Setup this controller
		this.setup();
	}
	
	/* The method used to setup this controller */
	public void setup() {
		//Assign all of the buttons and axes
		this.buttons = new ControllerButton[GLFW.glfwGetJoystickButtons(this.index).capacity()];
		this.axes = new ControllerAxis[GLFW.glfwGetJoystickAxes(this.index).capacity()];
		for (int a = 0; a < buttons.length; a++)
			this.buttons[a] = new ControllerButton(this, a);
		for (int a = 0; a < axes.length; a++)
			this.axes[a] = new ControllerAxis(this, a);
	}
	
	/* The method used to check for changes in the controller */
	public void checkInput() {
		//Go through all of the buttons and axes and check for changes
		for (int a = 0; a < this.buttons.length; a++)
			this.buttons[a].checkInput();
		for (int a = 0; a < this.axes.length; a++)
			this.axes[a].checkInput();
	}
	
	/* The setters and getters */
	public int getIndex() { return this.index; }
	public String getName() { return GLFW.glfwGetJoystickName(this.index); }
	public ControllerButton[] getButtons() { return this.buttons; }
	public ControllerAxis[] getAxes() { return this.axes; }
	
}