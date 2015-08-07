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

import org.andor.core.Colour;
import org.andor.core.resource.texture.Texture;

public class GUIRadioCheckBox extends GUICheckBox {
	
	/* The constructor */
	public GUIRadioCheckBox(GUIComponentRenderer renderer) {
		//Call the super constructor
		super(renderer);
	}
	
	/* The constructor */
	public GUIRadioCheckBox(Colour[] colours, float width, float height) {
		//Call the super constructor
		super(colours, width, height);
	}
	
	/* The constructor */
	public GUIRadioCheckBox(Texture[] textures, float width, float height) {
		//Call the super constructor
		super(textures, width, height);
	}
	
	/* The constructor */
	public GUIRadioCheckBox(Texture[] textures, Colour[] colours, float width, float height) {
		//Call the super constructor
		super(textures, colours, width, height);
	}
	
	/* The method called when the component is clicked */
	protected void componentOnClicked() {
		//Toggle checked
		this.checked = ! this.checked;
		//Check to make sure this component is part of a GUIGroup
		if (this.belongsToGroup()) {
			//Go through the other components in the same group
			for (int a = 0; a < this.group.getGroupComponents().size(); a++) {
				//Get an instance of the current component
				GUIComponent component = this.group.getGroupComponents().get(a);
				//Check to make sure the component is also an instance of GUIRadioCheckBox, but not the same as this
				if (component instanceof GUIRadioCheckBox && component != this)
					//Make sure the component is unchecked
					((GUIRadioCheckBox) component).checked = false;
			}
		}
	}
	
}