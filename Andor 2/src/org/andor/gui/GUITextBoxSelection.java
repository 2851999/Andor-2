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

public class GUITextBoxSelection extends Object2D {
	
	/* The text box instance */
	public GUITextBox textBox;
	
	/* The renderer */
	public GUIComponentRenderer renderer;
	
	/* The colour and texture */
	public Colour colour;
	public Texture texture;
	
	/* The default constructor */
	public GUITextBoxSelection() {
		
	}
	
	/* The constructor */
	public GUITextBoxSelection(GUITextBox textBox) {
		//Setup
		this.setup(textBox);
	}
	
	/* The method used to setup this */
	public void setup(GUITextBox textBox) {
		//Assign the variables
		this.textBox = textBox;
		this.setSize(0, 0);
		this.renderer = new GUIComponentRenderer(Object2DBuilder.createQuad(this.getWidth(), this.getHeight(), Colour.WHITE, new RenderData(GL11.GL_TRIANGLES, true, false, false, false)));
		this.renderer.colours = new Colour[] { Colour.BLACK };
		//Set the colour and texture
		if (this.colour != null)
			this.setColour(colour);
		if (this.texture != null)
			this.setTexture(texture);
	}
	
	/* The methods used to set the colour/texture and check whether they are set/return them */
	public void setColour(Colour colour) { this.renderer.colours = new Colour[] { colour }; }
	public void setTexture(Texture texture) { this.renderer.textures = new Texture[] { texture }; }
	public Colour getColour() { return this.renderer.colours[0]; }
	public Texture getTexture() { return this.renderer.textures[0]; }
	public boolean hasColour() { return this.renderer.colours != null; }
	public boolean hasTexture() { return this.renderer.textures != null; }
	
	/* The method used to render this cursor */
	public void render() {
		//Check to see whether there is a selection
		if (this.textBox.isSelection) {
			//Get the position of the text box
			Vector2f p = this.textBox.getPosition();
			//Get the position and size of the selection
			float selectionX = 0;
			//Catch any errors
			try {
				if (this.textBox.selectionIndexStart < this.textBox.selectionIndexEnd)
					selectionX = p.x + this.textBox.renderer.font.getWidth(this.textBox.renderText.substring(0, this.textBox.selectionIndexStart - this.textBox.viewIndexStart));
				else
					selectionX = p.x + this.textBox.renderer.font.getWidth(this.textBox.renderText.substring(0, this.textBox.selectionIndexEnd - this.textBox.viewIndexStart));
			} catch (StringIndexOutOfBoundsException e) {
				
			}
			float selectionY = p.y;
			float selectionWidth = this.textBox.renderer.font.getWidth(this.textBox.getRenderTextSelection());
			float selectionHeight = this.textBox.getHeight();
			//Update the renderer
			this.renderer.entity.getRenderData().updateVertices(Object2DBuilder.createQuadVertices(selectionWidth, selectionHeight));
			//Set the position
			this.position = new Vector2f(selectionX, selectionY);
			//Render the selection
			this.renderer.render(this, this.textBox.active);
		}
	}
	
}