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

import org.andor.core.BaseEngine;
import org.andor.core.GameLoopInterface;
import org.andor.core.Object2D;
import org.andor.core.Vector2f;

public class GUIPanel extends Object2D implements GameLoopInterface, GUIComponentListener {
	
	/* The group */
	public GUIGroup group;
	
	/* The initial resolution of this panel */
	public Vector2f resolution;
	
	/* The boolean that states whether a component listener should be added to any components */
	public boolean addListener;
	
	/* The constructor */
	public GUIPanel(String name, boolean autoUpdate) {
		//Assign the variables
		this.group = new GUIGroup(name);
		this.attach(this.group);
		if (autoUpdate)
			BaseEngine.add(this);
		this.addListener = true;
	}
	
	/* The method called when the game loop is updated */
	public void update() {
		//Update the group
		this.group.update();
	}
	
	/* The method called when the game loop is rendered */
	public void render() {
		//Render the group
		this.group.render();
	}
	
	/* The method used to add a component to this panel */
	public void add(GUIComponent component) {
		//Check to see whether a listener should be attached
		if (this.addListener)
			component.addListener(this);
		this.group.add(component);
	}
	
	/* The method used to change the scale of this panel */
	public void scale(Vector2f amount) {
		//Scale all of the components
		this.group.scale(amount);
	}
	
	/* The method used to change the resolution of the panel */
	public void setResolution(Vector2f resolution) {
		//Check to see whether the resolution has already been set
		if (this.resolution == null) {
			//Assign the resolution
			this.resolution = resolution;
		} else {
			//Calculate the change in scale
			float scaleWidth = resolution.x / this.resolution.x;
			float scaleHeight = resolution.y / this.resolution.y;
			//Assign the new resolution
			this.resolution = resolution;
			//Apply the scale
			this.scale(new Vector2f(scaleWidth, scaleHeight));
		}
	}
	
	/* The method used to clear this panel */
	public void clear() {
		//Clear the group
		this.group.clear();
	}
	
	/* The method used to remove a component */
	public void remove(GUIComponent component) {
		this.group.remove(component);
	}
	
	/* The method called when the mouse enters a gui component */
	public void onMouseEnter(GUIComponent component) {
		
	}
	
	/* The method called when the mouse exits a gui component */
	public void onMouseLeave(GUIComponent component) {
		
	}
	
	/* The method called when a gui component is clicked */
	public void onClicked(GUIComponent component) {
		
	}
	
}