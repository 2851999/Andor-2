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

public class GUITexture extends GUIComponent {
	
	/* The constructor */
	public GUITexture(Texture texture) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(texture.getWidth(), texture.getHeight(), texture, Colour.WHITE)));
		//Assign the variables
		this.setSize(texture.getWidth(), texture.getHeight());
		this.renderer.textures = new Texture[] { texture };
		this.renderer.colours = new Colour[] { Colour.WHITE };
	}
	
	/* The constructor */
	public GUITexture(Texture texture, Colour colour) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(texture.getWidth(), texture.getHeight(), texture, colour)));
		//Assign the variables
		this.setSize(texture.getWidth(), texture.getHeight());
		this.renderer.textures = new Texture[] { texture };
		this.renderer.colours = new Colour[] { colour };
	}
	
	/* The constructor */
	public GUITexture(Texture texture, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, texture, Colour.WHITE)));
		//Assign the variables
		this.setSize(width, height);
		this.renderer.textures = new Texture[] { texture };
		this.renderer.colours = new Colour[] { Colour.WHITE };
	}
	
	/* The constructor */
	public GUITexture(Texture texture, Colour colour, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, texture, colour)));
		//Assign the variables
		this.setSize(width, height);
		this.renderer.textures = new Texture[] { texture };
		this.renderer.colours = new Colour[] { colour };
	}
	
}