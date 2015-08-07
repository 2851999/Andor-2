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
import org.andor.core.Object2D;
import org.andor.core.Object2DBuilder;
import org.andor.core.Vector2f;
import org.andor.core.render.RenderData;
import org.andor.core.resource.texture.Texture;
import org.lwjgl.opengl.GL11;

public class GUIBorder extends Object2D {
	
	/* The component */
	public GUIComponent component;
	
	/* The renderer */
	public GUIComponentRenderer renderer;
	
	/* The thickness */
	private float thickness;
	
	/* The colours */
	private Colour[] colours;
	
	/* The textures */
	private Texture[] textures;
	
	/* The default constructor */
	public GUIBorder() {
		
	}
	
	/* The constructor */
	public GUIBorder(GUIComponent component, float thickness) {
		//Assign the variables
		this.thickness = thickness;
		//Set the colour to white
		this.colours = new Colour[] { Colour.WHITE };
		//Setup this border
		this.setup(component);
	}
	
	/* The constructor */
	public GUIBorder(float thickness, Colour colour) {
		//Assign the variables
		this.thickness = thickness;
		//Set the colours
		this.colours = new Colour[] { colour };
	}
	
	/* The constructor */
	public GUIBorder(float thickness, Texture textures) {
		//Assign the variables
		this.thickness = thickness;
		//Set the texture
		this.textures = new Texture[] { textures };
	}
	
	/* The constructor */
	public GUIBorder(GUIComponent component, float thickness, Colour colour) {
		//Assign the variables
		this.thickness = thickness;
		//Set the colours
		this.colours = new Colour[] { colour };
		//Setup this border
		this.setup(component);
	}
	
	/* The constructor */
	public GUIBorder(GUIComponent component, float thickness, Texture texture) {
		//Assign the variables
		this.thickness = thickness;
		//Set the textures
		this.textures = new Texture[] { texture };
		//Setup this border
		this.setup(component);
	}
	
	/* The constructor */
	public GUIBorder(GUIComponent component, float thickness, Colour[] colours) {
		//Assign the variables
		this.thickness = thickness;
		//Set the colours
		this.colours = colours;
		//Setup this border
		this.setup(component);
	}
	
	/* The constructor */
	public GUIBorder(GUIComponent component, float thickness, Texture[] textures) {
		//Assign the variables
		this.thickness = thickness;
		//Setup this border
		this.setup(component);
		//Set the texture
		this.textures = textures;
	}
	
	/* The method used to setup this boarder */
	public void setup(GUIComponent component) {
		//Assign the variables
		this.component = component;
		this.renderer = new GUIComponentRenderer(Object2DBuilder.createQuad(this.component.getWidth() + (this.thickness * 2), this.component.getHeight() + (this.thickness * 2), Colour.WHITE, new RenderData(GL11.GL_TRIANGLES, true, true, true, true)));
		this.position = new Vector2f(-this.thickness, -this.thickness);
		this.component.attach(this);
		if (this.colours != null)
			this.renderer.colours = this.colours;
		else if (this.textures != null)
			this.renderer.textures = this.textures;
	}
	
	/* The method used to render this boarder */
	public void render() {
		//Make sure this boarder has been setup
		if (this.renderer != null)
			this.renderer.render(this, this.component.active);
	}
	
	/* The methods used to return values */
	public float getThickness() { return this.thickness; }
	
	/* The methods used to get and set the colour/texture of this border */
	public void setColour(Colour colour) { this.colours = new Colour[] { colour }; }
	public void setImage(Texture texture) { this.textures = new Texture[] { texture }; }
	public Colour getColour() { return this.colours[0]; }
	public Texture getTexture() { return this.textures[0]; }
	
	/* The methods used to check whether the colours/textures have been set */
	public boolean hasColour() { return this.colours != null; }
	public boolean hasTexture() { return this.textures != null; }
	
}