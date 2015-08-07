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

import org.andor.core.Font;
import org.andor.core.Object2D;
import org.andor.core.RenderableObject2D;
import org.andor.core.Vector2f;
import org.andor.core.input.Mouse;

public class GUIComponent extends Object2D {
	
	/* The GUIComponentRenderer instance */
	public GUIComponentRenderer renderer;
	
	/* The name of this component */
	public String name;
	
	/* The boolean that states whether this component is visible */
	public boolean visible;
	
	/* The boolean that states whether this component is active */
	public boolean active;
	
	/* The boolean that states whether the mouse is hovering inside this component */
	public boolean mouseHoveringInside;
	
	/* The boolean that states whether the mouse has been clicked inside this component */
	public boolean clicked;
	
	/* The boolean that states whether clicked events should be repeated */
	public boolean repeatClickedEvents;
	
	/* The boolean that states whether a clicked event has already been sent */
	private boolean hasBeenClickedEvent;
	
	/* The component listeners */
	private List<GUIComponentListener> componentListeners;
	
	/* The group */
	public GUIGroup group;
	
	/* The border */
	public GUIBorder border;
	
	/* The boolean that states whether the border should be shown (If the border exists) */
	public boolean borderEnabled;
	
	/* The components within this component */
	public List<GUIComponent> components;
	
	/* The GUIPosition preference */
	public GUIPosition positionPreference;
	
	/* The tool tip */
	public GUIToolTip toolTip;
	
	/* The default constructor - should only be used when the renderer will be setup separately */
	public GUIComponent() {}
	
	/* The constructor */
	public GUIComponent(RenderableObject2D object) {
		if (object != null)
			this.setSize(object.getWidth(), object.getHeight());
		this.setup(object);
	}
	
	/* The constructor */
	public GUIComponent(RenderableObject2D object, float width, float height) {
		this.setSize(width, height);
		this.setup(object);
	}
	
	/* The method used to setup this component with the renderer */
	public void setup(GUIComponentRenderer renderer) {
		//Assign the renderer
		this.renderer = renderer;
		//Set the defaults
		this.setDefaults();
	}
	
	/* The method used to setup this */
	public void setup(RenderableObject2D object) {
		//Assign the variables
		this.renderer = new GUIComponentRenderer(object);
		//Set the defaults
		this.setDefaults();
	}
	
	/* The method used to setup the default values */
	public void setDefaults() {
		//Assign the default values
		this.name = "";
		this.visible = true;
		this.active = true;
		this.mouseHoveringInside = false;
		this.clicked = false;
		this.repeatClickedEvents = false;
		this.hasBeenClickedEvent = false;
		this.componentListeners = new ArrayList<GUIComponentListener>();
		this.borderEnabled = true;
		this.components = new ArrayList<GUIComponent>();
		this.positionPreference = GUIPosition.NONE;
		this.toolTip = null;
	}
	
	/* The method used to update this component */
	public void update() {
		//Make sure this component is active
		if (this.visible && this.active) {
			//Check to see whether the mouse is inside this component
			if (this.getBounds().contains((float) Mouse.lastX, (float) Mouse.lastY)) {
				//Check to see whether the mouse is already hovering
				if (! this.mouseHoveringInside && ! Mouse.leftButtonDown) {
					//The mouse is hovering inside of this component
					this.mouseHoveringInside = true;
					//Call an onMouseEnter event
					this.callOnMouseEnterEvent();
				}
				//Check to see whether the left mouse button is down
				if (Mouse.leftButtonDown && this.mouseHoveringInside) {
					//Check to see whether repeat click events are enabled
					if (this.repeatClickedEvents || (! this.repeatClickedEvents && ! this.hasBeenClickedEvent)) {
						//Set clicked to true
						this.clicked = true;
						this.hasBeenClickedEvent = true;
						//Call an onClicked event
						this.callOnClickedEvent();
					}
				} else {
					//Not clicked since the left mouse button is not down
					this.clicked = false;
					this.hasBeenClickedEvent = false;
				}
			} else {
				//Check to see whether the mouse is already not hovering
				if (this.mouseHoveringInside) {
					//The mouse is not hovering inside of this component
					this.mouseHoveringInside = false;
					//Call an onMouseLeave event
					this.callOnMouseLeaveEvent();
				}
				//Not clicked, because the mouse isn't even within the bounds of this component
				this.clicked = false;
				this.hasBeenClickedEvent = false;
			}
			//Update this component
			this.updateComponent();
			//Go through all of the components within this component
			for (int a = 0; a < this.components.size(); a++)
				//Update the current component
				this.components.get(a).update();
			//Check to see whether the tool tip has been set
			if (this.toolTip != null)
				this.toolTip.update();
		}
	}
	
	/* The method used to render this component */
	public void render() {
		//Make sure this component is visible
		if (this.visible) {
			//Render this component
			if (this.hasBorder() && this.borderEnabled)
				this.border.render();
			this.renderer.render(this, this.active);
			//Go through all of the components within this component
			for (int a = 0; a < this.components.size(); a++)
				//Render the current component
				this.components.get(a).render();
			this.renderComponent();
			//Check to see whether the tool tip has been set
			if (this.toolTip != null)
				this.toolTip.render();
		}
	}
	
	/* The method used to update this component */
	protected void updateComponent() { }
	
	/* The method used to render this component */
	protected void renderComponent() { }
	
	/* The method called when the mouse enter's this component */
	protected void componentOnMouseEnter() { }
	
	/* The method called when the mouse leave's this component */
	protected void componentOnMouseLeave() { }
	
	/* The method called when the component is clicked */
	protected void componentOnClicked() { }
	
	/* The method used to render some text */
	public void renderText(String text) {
		//Get the position
		Vector2f p = this.getPosition();
		this.renderer.font.render(text, p.x, p.y);
	}
	public void renderText(String text, Vector2f offset) {
		//Get the position
		Vector2f p = this.getPosition();
		this.renderer.font.render(text, p.x + offset.x, p.y + offset.y);
	}
	public void renderText(String text, float x, float y) { this.renderer.font.render(text, x, y); }
	public void renderTextAtCentre(String text) { this.renderer.font.renderAtCentre(text, this, new Vector2f(0, 0)); }
	public void renderTextAtCentre(String text, Vector2f offset) { this.renderer.font.renderAtCentre(text, this, offset); }
	
	/* The method used to add a GUIComponent listener */
	public void addListener(GUIComponentListener listener) {
		this.componentListeners.add(listener);
	}
	
	/* The method used to call an onMouseEnter event from this component */
	public void callOnMouseEnterEvent() {
		//Call the method
		this.componentOnMouseEnter();
		//Go through the listeners
		for (int a = 0; a < this.componentListeners.size(); a++)
			//Call the event in the current listener
			this.componentListeners.get(a).onMouseEnter(this);
	}
	
	/* The method used to call an onMouseLeave event from this component */
	public void callOnMouseLeaveEvent() {
		//Call the method
		this.componentOnMouseLeave();
		//Go through the listeners
		for (int a = 0; a < this.componentListeners.size(); a++)
			//Call the event in the current listener
			this.componentListeners.get(a).onMouseLeave(this);
	}
	
	/* The method used to call an onClicked event from this component */
	public void callOnClickedEvent() {
		//Call the method
		this.componentOnClicked();
		//Go through the listeners
		for (int a = 0; a < this.componentListeners.size(); a++)
			//Call the event in the current listener
			this.componentListeners.get(a).onClicked(this);
	}
	
	/* The method used to add a component to this group */
	public void add(GUIComponent component) {
		//Link the component to this
		this.attach(component);
		//Add the component to the list of components
		this.components.add(component);
	}
	
	/* The method used to add a component to this group */
	public void add(GUIComponent component, GUIPosition pos) {
		this.add(component, pos, new Vector2f(0, 0));
	}
	
	/* The method used to add a component to this group given an offset */
	public void add(GUIComponent component, GUIPosition pos, Vector2f offset) {
		//Set the component's position preference
		component.positionPreference = pos;
		//The x and y position
		float xPos = 0;
		float yPos = 0;
		//Check the position value
		if (pos == GUIPosition.TOP) {
			//Get all of the components with the same position preference
			List<GUIComponent> comps = this.getComponentsWithPositionPreference(pos);
			//Setup x and y position
			//Don't use getCenter() because this component will be linked, so the position is relative
			//not exact
			xPos = (this.getWidth() / 2) - (component.getWidth() / 2);
			yPos = 0;
			//Go through the components
			for (int a = 0; a < comps.size(); a++)
				//Add onto the y position
				yPos += comps.get(a).getHeight();
		} else if (pos == GUIPosition.BOTTOM) {
			//Get all of the components with the same position preference
			List<GUIComponent> comps = this.getComponentsWithPositionPreference(pos);
			//Setup x and y position
			xPos = (this.getWidth() / 2) - (component.getWidth() / 2);
			yPos = this.getHeight() - component.getHeight();
			//Go through the components
			for (int a = 0; a < comps.size(); a++)
				//Add onto the y position
				yPos -= comps.get(a).getHeight();
		} else if (pos == GUIPosition.LEFT) {
			//Get all of the components with the same position preference
			List<GUIComponent> comps = this.getComponentsWithPositionPreference(pos);
			//Setup x and y position
			xPos = 0;
			yPos = (this.getHeight() / 2) - (component.getHeight() / 2);
			//Go through the components
			for (int a = 0; a < comps.size(); a++)
				//Add onto the x position
				xPos += comps.get(a).getWidth();
		} else if (pos == GUIPosition.RIGHT) {
			//Get all of the components with the same position preference
			List<GUIComponent> comps = this.getComponentsWithPositionPreference(pos);
			//Setup x and y position
			xPos = this.getWidth() - component.getWidth();
			yPos = (this.getHeight() / 2) - (component.getHeight() / 2);
			//Go through the components
			for (int a = 0; a < comps.size(); a++)
				//Add onto the x position
				xPos -= comps.get(a).getWidth();
		} else if (pos == GUIPosition.CENTRE) {
			//Setup x and y position
			xPos = (this.getWidth() / 2) - (component.getWidth() / 2);
			yPos = (this.getHeight() / 2) - (component.getHeight() / 2);
		}
		//Set the position of the component
		component.position = new Vector2f(xPos, yPos);
		//Add on the offset to the component's position
		component.position.add(offset);
		//Add the component
		this.add(component);
	}
	
	/* The method used to get all of the components with a specific position preference */
	public List<GUIComponent> getComponentsWithPositionPreference(GUIPosition preference) {
		//The list
		List<GUIComponent> list = new ArrayList<GUIComponent>();
		//Go through all of the components
		for (int a = 0; a < this.components.size(); a++) {
			//Check the current component's position preference
			if (this.components.get(a).positionPreference == preference)
				//Add it to the list
				list.add(this.components.get(a));
		}
		//Return the list
		return list;
	}
	
	/* The method used to change the scale of this component */
	public void scale(Vector2f amount) {
		//Assign the scale of this component
		this.scale.multiply(amount);
		//Go through each component within this one
		for (int a = 0; a < this.components.size(); a++)
			//Change the scale of the current component
			this.components.get(a).scale(amount);
	}
	
	/* The methods used to set/toggle/return some values */
	public void setName(String name) { this.name = name; }
	public void setVisible(boolean visible) { this.visible = visible; }
	public void setActive(boolean active) { this.active = active; }
	public void setRepeatClickedEvents(boolean repeatClickedEvents) { this.repeatClickedEvents = repeatClickedEvents; }
	public void setGroup(GUIGroup group) { this.group = group; }
	public void setBorder(GUIBorder border) { this.border = border; }
	public void setBorderEnabled(boolean borderEnabled) { this.borderEnabled = borderEnabled; }
	public void setComponents(List<GUIComponent> components) { this.components = components; }
	public void setPositionPreference(GUIPosition positionPreference) { this.positionPreference = positionPreference; }
	public void toggleVisible() { this.visible = ! this.visible; }
	public void toggleActive() { this.active = ! this.active; }
	public void toggleBorder() { this.borderEnabled = ! this.borderEnabled; }
	public void setFont(Font font) { this.renderer.setFont(font); }
	public String getName() { return this.name; }
	public boolean isVisible() { return this.visible; }
	public boolean isActive() { return this.active; }
	public boolean isRepeatClickedEventsEnabled() { return this.repeatClickedEvents; }
	public GUIGroup getGroup() { return this.group; }
	public boolean belongsToGroup() { return this.group != null; }
	public GUIBorder getBorder() { return this.border; }
	public boolean hasBorder() { return this.border != null; }
	public boolean isBorderEnabled() { return this.borderEnabled; }
	public List<GUIComponent> getComponents() { return this.components; }
	public GUIPosition getPositionPreference() { return this.positionPreference; }
	public boolean isClicked() { return this.clicked; }
	
}