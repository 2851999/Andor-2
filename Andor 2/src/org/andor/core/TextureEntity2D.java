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

package org.andor.core;

import org.andor.core.render.RenderData;
import org.andor.core.resource.texture.Texture;
import org.lwjgl.opengl.GL11;

public class TextureEntity2D extends RenderableObject2D {
	
	/* The constructor */
	public TextureEntity2D(Texture texture, Colour colour, float width, float height) {
		super(Object2DBuilder.createQuad(width, height, texture, colour, new RenderData(GL11.GL_TRIANGLES, false, false, true, false)), width, height);
		this.getRenderData().getMaterial().setDiffuseTexture(texture);
	}
	
	/* The other constructors */
	public TextureEntity2D(Texture texture, float width, float height) {
		this(texture, Colour.WHITE, width, height);
	}
	
	public TextureEntity2D(Texture texture) {
		this(texture, texture.getWidth(), texture.getHeight());
	}
	
	/* The method used to set the image of this entity */
	public void setImage(Texture texture) {
		this.setTexture(texture);
		this.getRenderData().updateTextureCoordinates(Object2DBuilder.createQuadTextureCoordinates(texture));
	}
	
}