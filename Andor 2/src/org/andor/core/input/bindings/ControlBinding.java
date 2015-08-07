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

package org.andor.core.input.bindings;

import org.andor.core.input.ControllerAxis;
import org.andor.core.input.ControllerButton;
import org.andor.core.input.InputListener;

public class ControlBinding extends InputListener {
	
	/* The name of this binding */
	private String name;
	
	/* The id of this binding */
	private int id;
	
	/* The boolean that states whether this binding is awaiting input from the user */
	private boolean awaitingInput;
	
	/* The boolean that states whether this binding has been bound */
	private boolean bound;
	
	/* The key associated with this binding */
	private int key;
	
	/* The mouse button associated with this binding */
	private int mouseButton;
	
	/* The controller button associated with this binding */
	private ControllerButton button;
	
	/* The controller axis associated with this binding */
	private ControllerAxis axis;
	
	/* The axis direction (Allows the axis values to be inverted) */
	private int axisDirection;
	
	/* The current button value */
	private boolean buttonDown;
	
	/* The current axis value */
	private float axisValue;
	
	/* The bindings instance this is attached to */
	private ControlBindings bindings;
	
	/* The constructor */
	public ControlBinding(String name, int id) {
		//Assign the variables
		this.name = name;
		this.id = id;
		this.awaitingInput = false;
		this.bound = false;
		this.key = -1;
		this.mouseButton = -1;
		this.button = null;
		this.axis = null;
		this.axisDirection = 1;
		this.buttonDown = false;
		this.axisValue = 0;
		this.bindings = null;
		//Add this as an input listener
		this.addListener();
	}
	
	/* The method called to tell this binding to wait for input */
	public void waitForInput() {
		//Assign the boolean
		this.awaitingInput = true;
	}
	
	/* The method called to tell this binding to stop waiting for input */
	public void stopWaitingForInput() {
		this.awaitingInput = false;
	}
	
	/* The events */
	public void onKeyTyped(int code) {
		if (this.awaitingInput) {
			this.key = code;
			this.awaitingInput = false;
			this.bound = true;
		}
	}
	
	public void onMouseClicked(int button) {
		if (this.awaitingInput) {
			this.mouseButton = button;
			this.awaitingInput = false;
			this.bound = true;
		}
	}
	
	public void onControllerButtonReleased(ControllerButton button) {
		if (this.awaitingInput) {
			this.button = button;
			this.awaitingInput = false;
			this.bound = true;
		} else if (this.bound && this.button != null) {
			if (this.button.equals(button)) {
				this.buttonDown = false;
				if (this.axisDirection == 1)
					this.axisValue = 0;
				else
					this.axisValue = 1;
				this.bindings.callOnBindingChange(this);
			}
		}
	}
	
	public void onControllerAxisChange(ControllerAxis axis) {
		if (this.awaitingInput) {
			this.axis = axis;
			this.awaitingInput = false;
			this.bound = true;
		} else if (this.bound && this.axis != null) {
			if (this.axis.equals(axis)) {
				if (axis.getValue() >= 0)
					this.buttonDown = true;
				else
					this.buttonDown = false;
				this.axisValue = axis.getValue() * this.axisDirection;
				this.bindings.callOnBindingChange(this);
			}
		}
	}
	
	public void onKeyPressed(int code) {
		if (this.bound && this.key != -1) {
			if (code == this.key) {
				this.buttonDown = true;
				if (this.axisDirection == 1)
					this.axisValue = 1;
				else
					this.axisValue = 0;
				this.bindings.callOnBindingChange(this);
			}
		}
	}
	
	public void onKeyReleased(int code) {
		if (this.bound && this.key != -1) {
			if (code == this.key) {
				this.buttonDown = false;
				if (this.axisDirection == 1)
					this.axisValue = 0;
				else
					this.axisValue = 1;
				this.bindings.callOnBindingChange(this);
			}
		}
	}
	
	public void onMousePressed(int button) {
		if (this.bound && this.mouseButton != -1) {
			if (button == this.mouseButton) {
				this.buttonDown = true;
				if (this.axisDirection == 1)
					this.axisValue = 1;
				else
					this.axisValue = 0;
				this.bindings.callOnBindingChange(this);
			}
		}
	}
	
	public void onMouseReleased(int button) {
		if (this.bound && this.mouseButton != -1) {
			if (button == this.mouseButton) {
				this.buttonDown = false;
				if (this.axisDirection == 1)
					this.axisValue = 0;
				else
					this.axisValue = 1;
				this.bindings.callOnBindingChange(this);
			}
		}
	}
	
	public void onControllerButtonPressed(ControllerButton button) {
		if (this.bound && this.button != null) {
			if (this.button.equals(button)) {
				this.buttonDown = true;
				if (this.axisDirection == 1)
					this.axisValue = 1;
				else
					this.axisValue = 0;
				this.bindings.callOnBindingChange(this);
			}
		}
	}
	
	/* The setters and getters */
	public void setName(String name) { this.name = name; }
	public void setId(int id) { this.id = id; }
	public void setKey(int key) { this.key = key; this.bound = true; }
	public void setMouseButton(int button) { this.mouseButton = button; this.bound = true; }
	public void setControllerButton(ControllerButton button) { this.button = button; this.bound = true; }
	public void setControllerAxis(ControllerAxis axis) { this.axis = axis; this.bound = true; }
	public void setAxisDirection(int axisDirection) { this.axisDirection = axisDirection; }
	public void invertAxisDirection() { this.axisDirection *= -1; }
	public void setBindings(ControlBindings bindings) { this.bindings = bindings; }
	public String getName() { return this.name; }
	public int getId() { return this.id; }
	public boolean isAwaitingInput() { return this.awaitingInput; }
	public boolean isBound() { return this.bound; }
	public int getKey() { return this.key; }
	public int getMouseButton() { return this.mouseButton; }
	public ControllerButton getControllerButton() { return this.button; }
	public ControllerAxis getControllerAxis() { return this.axis; }
	public int getAxisDirection() { return this.axisDirection; }
	public boolean isPressed() { return this.buttonDown; }
	public float getValue() { return this.axisValue; }
	public ControlBindings getBindings() { return this.bindings; }
	public boolean hasBindings() { return this.bindings != null; }
	
}