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

import org.andor.Andor;
import org.andor.core.Colour;
import org.andor.core.Font;
import org.andor.core.Object2D;
import org.andor.core.Object2DBuilder;
import org.andor.core.RenderableObject2D;
import org.andor.core.render.RenderData;
import org.andor.core.resource.texture.Texture;

public class GUIComponentRenderer {
	
	/* The default font */
	public static Font defaultFont;
	
	/* The colours */
	public Colour[] colours;
	
	/* The textures */
	public Texture[] textures;
	
	/* The inactive colour */
	public Colour inactiveColour;
	
	/* The inactive image */
	public Texture inactiveTexture;
	
	/* The index value for the textures / colours */
	public int componentIndex;
	
	/* The entity this renders */
	public RenderableObject2D entity;
	
	/* The font */
	public Font font;
	
	/* The constructor */
	public GUIComponentRenderer(RenderData entityData) {
		this(new RenderableObject2D(entityData));
	}
	
	/* The constructor */
	public GUIComponentRenderer(RenderableObject2D entity) {
		//Assign the variables
		this.entity = entity;
		if (defaultFont == null)
			this.font = Andor.Resources.Fonts.Default;
		else
			this.font = defaultFont;
	}
	
	/* The method used to render the entity given an Object2D to base its position off of and the active value
	 * (Whether the component is active when  it is rendered) */
	public void render(Object2D object, boolean active) {
		//Make sure the entity has been set
		if (this.entity != null) {
			//Assign the entities values
			this.entity.position = object.getPosition();
			this.entity.rotation = object.getRotation();
			this.entity.scale = object.getScale();
			this.entity.setWidth(object.getWidth());
			this.entity.setHeight(object.getHeight());
			//Update the entity
			this.entity.update();
			//Can be optimised ----------------------------------------------------------------------------------------------------
			//Make sure the component is active
			if (active || (! this.shouldUseInactiveTexture()) || (! this.shouldUseInactiveColour())) {
				//Check whether textures or colours should be used
				if (this.shouldUseTextures()) {
					//Setup the textures
					this.entity.getRenderData().updateTextureCoordinates(Object2DBuilder.createQuadTextureCoordinates(this.textures[this.componentIndex]));
					this.entity.getRenderData().getMaterial().setDiffuseTexture(this.textures[this.componentIndex]);
					//Check to see whether the colours have also been set
					if (this.shouldUseColours())
						//Update the colours
						this.entity.getRenderData().updateColour(this.colours[this.componentIndex]);
					else
						//Set the colour to white
						this.entity.getRenderData().updateColour(Colour.WHITE);
				} else if (this.shouldUseColours())
					//Update the colours
					this.entity.getRenderData().updateColour(this.colours[this.componentIndex]);
			} else {
				//Check to see whether the inactive texture has been set
				if (this.shouldUseInactiveTexture()) {
					//Set the texture
					this.entity.getRenderData().getMaterial().setDiffuseTexture(this.inactiveTexture);
					//Check to see whether the colours have also been set
					if (this.shouldUseInactiveColour())
						//Update the colour
						this.entity.getRenderData().updateColour(this.inactiveColour);
				} else if (this.inactiveColour != null)
					//Update the colour
					this.entity.getRenderData().updateColour(this.inactiveColour);
			}
			//Render the entity
			this.entity.render();
		}
	}
	
	/* The methods used to set/return values */
	public void set(Texture[] textures) { this.textures = textures; }
	public void set(Colour[] colours) { this.colours = colours; }
	public void setComponentIndex(int componentIndex) { this.componentIndex = componentIndex; }
	public void setFont(Font font) { this.font = font; }
	public Texture[] getTextures() { return this.textures; }
	public Colour[] getColours() { return this.colours; }
	public int getComponentIndex() { return this.componentIndex; }
	public Font getFont() { return this.font; }
	public boolean shouldUseTextures() { return this.textures != null; }
	public boolean shouldUseColours() { return this.colours != null; }
	public boolean shouldUseInactiveTexture() { return this.inactiveTexture != null; }
	public boolean shouldUseInactiveColour() { return this.inactiveColour != null; }
	
	public int getTotalComponents() {
		if (this.shouldUseTextures())
			return this.textures.length;
		else if (this.shouldUseColours())
			return this.colours.length;
		else
			return 0;
	}
	
}