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
import org.andor.core.Object2DBuilder;
import org.andor.core.RenderableObject2D;
import org.andor.core.resource.texture.Texture;

public class GUIDropDownList extends GUIDropDownMenu {
	
	/* The overlay */
	public RenderableObject2D overlay;
	
	/* The overlay texture */
	public Texture overlayTexture;
	
	/* The constructor */
	public GUIDropDownList(GUIButton menuButton) {
		//Call the super constructor
		super(menuButton);
	}
	
	/* The constructor with the overlay texture */
	public GUIDropDownList(GUIButton menuButton, Texture overlayTexture) {
		//Call the super constructor
		super(menuButton);
		//Setup the overlay
		this.setupOverlay(overlayTexture);
	}
	
	/* The method used to setup the overlay */
	public void setupOverlay(Texture overlayTexture) {
		//Setup the overlay
		this.overlayTexture = overlayTexture;
		this.overlay = new RenderableObject2D(Object2DBuilder.createQuad(menuButton.getWidth(), menuButton.getHeight(), this.overlayTexture, Colour.WHITE));
	}
	
	/* The method used to render this component */
	protected void renderComponent() {
		//Render the menu button if it is set
		if (this.menuButton != null)
			this.menuButton.render();
		//Render the overlay if it is set
		if (this.overlay != null) {
			this.overlay.position = this.menuButton.position;
			this.overlay.render();
		}
		//Make sure this menu is open
		if (this.menuOpen) {
			//Go through all of the buttons
			for (int a = 0; a < this.buttons.size(); a++)
				//Render the current button
				this.buttons.get(a).render();
		}
	}
	
	/* The method called when a gui component is clicked */
	public void onClicked(GUIComponent component) {
		//Make sure the component is not the menu button
		if (component != this.menuButton) {
			//Close the menu
			this.menuOpen = false;
			//Add the menu button to the list of buttons
			this.buttons.add(this.menuButton);
			//The new menu button
			GUIButton newMenuButton = null;
			//Look at all of the buttons
			for (int a = 0; a < this.buttons.size(); a++) {
				//Check the current component
				if (this.buttons.get(a).equals(component)) {
					//Set the new menu button
					newMenuButton = this.buttons.get(a);
					//Remove the button from the list
					this.buttons.remove(a);
					break;
				}
			}
			//Set the menu button
			this.menuButton = newMenuButton;
			this.menuButton.clicked = false;
			this.menuButton.mouseHoveringInside = false;
			this.updateMenu();
		} else {
			//Toggle the menu open
			this.menuOpen = ! this.menuOpen;
		}
	}
	
}