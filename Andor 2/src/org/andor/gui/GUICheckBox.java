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
import org.andor.core.Vector2f;
import org.andor.core.render.RenderData;
import org.andor.core.resource.texture.Texture;
import org.lwjgl.opengl.GL11;

public class GUICheckBox extends GUIComponent {
	
	/* The boolean that represents whether this is checked or not */
	public boolean checked;
	
	/* The text */
	public String text;
	
	/* The constructor */
	public GUICheckBox(GUIComponentRenderer renderer) {
		//Setup this component
		this.setup(renderer);
		this.checked = false;
	}
	
	/* The constructor */
	public GUICheckBox(Colour[] colours, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE, new RenderData(GL11.GL_TRIANGLES, false, true, true, false)), width, height));
		//Set the values
		this.renderer.colours = colours;
		this.checked = false;
	}
	
	/* The constructor */
	public GUICheckBox(Texture[] textures, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, textures[0], Colour.WHITE, new RenderData(GL11.GL_TRIANGLES, false, true, true, false)), width, height));
		//Set the values
		this.renderer.textures = textures;
		this.checked = false;
	}
	
	/* The constructor */
	public GUICheckBox(Texture[] textures, Colour[] colours, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, textures[0], colours, new RenderData(GL11.GL_TRIANGLES, false, true, true, false)), width, height));
		//Set the values
		this.renderer.textures = textures;
		this.renderer.colours = colours;
		this.checked = false;
	}
	
	/* The method used to update this component */
	protected void updateComponent() {
		//Get the number of components to the textures/colours
		int components = this.renderer.getTotalComponents();
		//Check to see the state of this button
		if (this.checked) {
			if (components == 1)
				this.renderer.componentIndex = 0;
			else if (components == 2)
				this.renderer.componentIndex = 1;
			else if (components == 3)
				this.renderer.componentIndex = 2;
		} else if (this.mouseHoveringInside) {
			if (components == 1)
				this.renderer.componentIndex = 0;
			else if (components == 2)
				this.renderer.componentIndex = 1;
			else if (components == 3)
				this.renderer.componentIndex = 1;
		} else {
			if (components == 1)
				this.renderer.componentIndex = 0;
			else if (components == 2)
				this.renderer.componentIndex = 0;
			else if (components == 3)
				this.renderer.componentIndex = 0;
		}
	}
	
	/* The method used to render this component */
	protected void renderComponent() {
		//Make sure there is text to be rendered
		if (this.text != null)
			//Render the text
			this.renderTextAtCentre(this.text, new Vector2f(((this.renderer.font.getWidth(this.text) / 2) + (this.getWidth() / 2)) + 4, 0));
	}
	
	/* The method called when the component is clicked */
	protected void componentOnClicked() {
		//Toggle checked
		this.checked = ! this.checked;
	}
	
	/* The methods used to set/toggle/return some values */
	public void setChecked(boolean checked) { this.checked = checked; }
	public void toggleChecked() { this.checked = ! this.checked; }
	public boolean isChecked() { return this.checked; }
	
}