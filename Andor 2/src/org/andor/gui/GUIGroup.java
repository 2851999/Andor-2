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

public class GUIGroup extends GUIComponent {
	
	/* The list of components within this group */
	private List<GUIComponent> groupComponents;
	
	/* The constructor */
	public GUIGroup(String name) {
		//Call the super constructor
		super(null);
		//Assign the variables
		this.name = name;
		this.groupComponents = new ArrayList<GUIComponent>();
		this.setSize(0, 0);
	}
	
	/* The method used to update this component */
	public void updateComponent() {
		//Go through all of the components within this group
		for (int a = 0; a < this.groupComponents.size(); a++)
			//Update the current component
			this.groupComponents.get(a).update();
	}
	
	/* The method used to render this component */
	public void renderComponent() {
		//Go through all of the components within this group
		for (int a = 0; a < this.groupComponents.size(); a++)
			//Render the current component
			this.groupComponents.get(a).render();
	}
	
	/* The method used to add a component to this group */
	public void add(GUIComponent component) {
		//Set the components group to this
		component.setGroup(this);
		//Link the component to this
		this.attach(component);
		//Add the component to the list of components
		this.groupComponents.add(component);
	}
	
	/* The method used to remove a component */
	public void remove(GUIComponent component) {
		//Go through each component
		for (int a = 0; a < this.groupComponents.size(); a++) {
			//Check the current component
			if (this.groupComponents.get(a) == component) {
				//Remove the component
				this.groupComponents.remove(a);
				//Exit the loop
				break;
			}
		}
	}
	
	/* The method used to clear this group */
	public void clear() {
		//Clear the group
		this.groupComponents.clear();
	}
	
	/* The set/get methods */
	public void setGroupComponents(List<GUIComponent> groupComponents) { this.groupComponents = groupComponents; }
	public List<GUIComponent> getGroupComponents() { return this.groupComponents; }
	
}