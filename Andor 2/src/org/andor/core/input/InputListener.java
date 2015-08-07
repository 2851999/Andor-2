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

import org.andor.core.Vector2f;

public class InputListener implements InputListenerInterface {
	
	/* The method used to setup this listener for use */
	public void addListener() {
		InputManager.addListener(this);
	}
	
	/* The method used to delete this listener */
	public void removeListener() {
		InputManager.removeListener(this);
	}
	
	/* The keyboard events */
	public void onKeyPressed(int code) {}
	public void onKeyReleased(int code) {}
	public void onKeyTyped(int code) {}
	public void onChar(int code, char character) {}
	
	/* The mouse events */
	public void onMousePressed(int button) {}
	public void onMouseReleased(int button) {}
	public void onMouseClicked(int button) {}
	public void onMouseMoved(double x, double y, double xOffset, double yOffset) {}
	public void onMouseDragged(double x, double y, double xOffset, double yOffset) {}
	public void onMouseEnter() {}
	public void onMouseLeave() {}
	
	/* The scroll event */
	public void onScroll(double xOffset, double yOffset) {}
	
	/* The window events */
	public void onWindowSizeChanged(Vector2f oldSize, Vector2f newSize) {}
	
	/* The controller events */
	public void onControllerButtonPressed(ControllerButton button) {}
	public void onControllerButtonReleased(ControllerButton button) {}
	public void onControllerAxisChange(ControllerAxis axis) {}
	
}