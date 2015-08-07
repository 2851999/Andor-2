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
import org.andor.core.render.RenderData;
import org.andor.core.resource.texture.Texture;
import org.lwjgl.opengl.GL11;

public class GUIFill extends Object2D {
	
	/* The component */
	public GUIComponent component;
	
	/* The renderer */
	public GUIComponentRenderer renderer;
	
	/* The colour of this fill */
	public Colour colour;
	
	/* The texture of this fill */
	public Texture texture;
	
	/* The last width and height */
	private float lastWidth;
	private float lastHeight;
	
	/* The default constructor */
	public GUIFill() {
		
	}
	
	/* The constructor */
	public GUIFill(GUIComponent component) {
		//Setup this fill
		this.setup(component);
	}
	
	/* The constructor */
	public GUIFill(GUIComponent component, Colour colour) {
		//Setup this fill
		this.setup(component);
		this.colour = colour;
	}
	
	/* The constructor */
	public GUIFill(GUIComponent component, Texture texture) {
		//Setup this fill
		this.setup(component);
		//Set the texture
		this.texture = texture;
	}
	
	/* The method used to setup this fill */
	public void setup(GUIComponent component) {
		//Assign the variables
		this.component = component;
		this.component.attach(this);
		if (texture != null)
			this.renderer = new GUIComponentRenderer(Object2DBuilder.createQuad(this.component.getWidth(), this.component.getHeight(), texture, Colour.WHITE, new RenderData(GL11.GL_TRIANGLES, true, false, true, false)));
		else
			this.renderer = new GUIComponentRenderer(Object2DBuilder.createQuad(this.component.getWidth(), this.component.getHeight(), Colour.WHITE, new RenderData(GL11.GL_TRIANGLES, true, false, true, false)));
		this.colour = Colour.WHITE;
	}
	
	/* The method used to assign the texture */
	public void setTexture(Texture texture) {
		//Check to see whether one has already been set
		if (this.texture == null)
			this.renderer = new GUIComponentRenderer(Object2DBuilder.createQuad(this.component.getWidth(), this.component.getHeight(), texture, Colour.WHITE, new RenderData(GL11.GL_TRIANGLES, true, false, true, false)));
		this.texture = texture;
	}
	
	/* The method used to update this fill */
	public void update() {
		
	}
	
	/* The method used to fill this boarder */
	public void render() {
		//Make sure this fill has been setup
		if (this.renderer != null) {
			this.renderer.colours = new Colour[] { this.colour };
			if (this.texture != null)
				this.renderer.textures = new Texture[] { this.texture };
			float width = this.getWidth();
			float height = this.getHeight();
			if (width != this.lastWidth || height != this.lastHeight) {
				this.renderer.entity.getRenderData().updateVertices(Object2DBuilder.createQuadVertices(width, height));
				this.lastWidth = width;
				this.lastHeight = height;
			}
			this.renderer.render(this, this.component.active);
		}
	}
	
}