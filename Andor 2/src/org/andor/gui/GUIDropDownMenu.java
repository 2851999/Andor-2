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

package org.andor.gui;

import java.util.ArrayList;
import java.util.List;

import org.andor.core.Rectangle;
import org.andor.core.Vector2f;
import org.andor.core.input.ControllerAxis;
import org.andor.core.input.ControllerButton;
import org.andor.core.input.InputListenerInterface;
import org.andor.core.input.InputManager;
import org.andor.core.input.Mouse;

public class GUIDropDownMenu extends GUIComponent implements GUIComponentListener, InputListenerInterface {
	
	/* The menu button */
	public GUIButton menuButton;
	
	/* The other buttons in this drop down menu */
	protected List<GUIButton> buttons;
	
	/* The boolean that represents whether this menu is open */
	public boolean menuOpen;
	
	/* The constructor */
	public GUIDropDownMenu(GUIButton menuButton) {
		//Call the super constructor
		super(null);
		//Assign the variables
		this.menuButton = menuButton;
		this.setSize(this.menuButton.getWidth(), this.menuButton.getHeight());
		this.buttons = new ArrayList<GUIButton>();
		this.menuOpen = false;
		//Add this component listener to the menu button
		this.menuButton.addListener(this);
		//Add this input listener interface
		InputManager.addListener(this);
	}
	
	/* The method used to update this component */
	protected void updateComponent() {
		this.updateMenu();
	}
	
	protected void updateMenu() {
		//Update the menu button
		this.menuButton.update();
		//Set the menu buttons visible value
		this.menuButton.visible = this.visible;
		//Set the menu buttons active value
		this.menuButton.active = this.active;
		//Set the position of the menu button
		this.menuButton.position = this.position;
		//Set the border enabled
		this.borderEnabled = this.menuOpen;
		//The current y position
		float currentY = this.position.y + this.menuButton.getHeight();
		//Set all of the components visible and active values
		for (int a = 0; a < this.buttons.size(); a++) {
			this.buttons.get(a).visible = this.visible && this.menuOpen;
			this.buttons.get(a).active = this.active && this.menuOpen;
			//Set the current button's position
			this.buttons.get(a).position = new Vector2f(this.position.x, currentY);
			//Update the current button
			this.buttons.get(a).update();
			//Add onto the current y position
			currentY += this.buttons.get(a).getHeight();
		}
	}
	
	/* The method used to render this component */
	protected void renderComponent() {
		//Render the menu button if it is set
		if (this.menuButton != null)
			this.menuButton.render();
		//Make sure this menu is open
		if (this.menuOpen) {
			//Go through all of the buttons
			for (int a = 0; a < this.buttons.size(); a++)
				//Render the current button
				this.buttons.get(a).render();
		}
	}
	
	/* The method called when the mouse enters a gui component */
	public void onMouseEnter(GUIComponent component) {
		
	}
	
	/* The method called when the mouse exits a gui component */
	public void onMouseLeave(GUIComponent component) {
		
	}
	
	/* The method called when a gui component is clicked */
	public void onClicked(GUIComponent component) {
		//Toggle menu open
		this.menuOpen = ! this.menuOpen;
	}
	
	/* The keyboard events */
	public void onKeyPressed(int code) {}
	public void onKeyReleased(int code) {}
	public void onKeyTyped(int code) {}
	public void onChar(int code, char character) {}
	
	/* The mouse events */
	public void onMousePressed(int button) {}
	public void onMouseReleased(int button) {}
	public void onMouseClicked(int button) {
		//Check to see whether the mouse was clicked within this menu
		if (! this.getMenuBounds().contains((float) Mouse.lastX, (float) Mouse.lastY) && this.menuOpen)
			//Hide this menu
			this.menuOpen = false;
	}
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
	
	/* The methods used to add/set/toggle/return values */
	public void addButton(GUIButton button) {
		//Add this to the buttons component listeners
		button.addListener(this);
		//Add the button to the list of buttons
		this.buttons.add(button);
		//Get the new bounds of this menu
		Rectangle bounds = this.getMenuBounds();
		//Set the width and height
		this.setWidth(bounds.width);
		this.setHeight(bounds.height);
	}
	
	public void addButton(GUIButton button, String name) {
		//Assign the name
		button.setName(name);
		//Add the button
		this.addButton(button);
	}
	
	public void setOpen(boolean menuOpen) { this.menuOpen = menuOpen; }
	public void toggleOpen() { this.menuOpen = ! this.menuOpen; }
	public boolean isOpen() { return this.menuOpen; }
	public List<GUIButton> getButtons() { return this.buttons; }
	
	public Rectangle getMenuBounds() {
		//The positions
		float x = this.position.x;
		float y = this.position.y;
		//Get the maximum width and total height
		float width = this.menuButton.getWidth();
		float height = this.menuButton.getHeight();
		for (int a = 0; a < this.buttons.size(); a++) {
			//The current button
			GUIButton button = this.buttons.get(a);
			//Add onto the height
			height += button.getHeight();
			//Check the width of the current button
			if (button.getWidth() > width)
				this.setWidth(button.getWidth());
		}
		//Return the rectangle
		return new Rectangle(x, y, width, height);
	}
	
}