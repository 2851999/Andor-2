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

package org.andor.core.resource.texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.andor.utils.BufferUtils;
import org.andor.utils.Log;
import org.andor.utils.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Texture {
	
	/* The pointer to this texture */
	private int pointer = -1;
	
	/* The texture coordinates */
	public float top = 0;
	public float bottom = 1;
	public float left = 0;
	public float right = 1;
	
	/* The width and height of this texture */
	private int width;
	private int height;
	
	/* The number of colour components in this texture */
	private int numberOfColourComponents;
	
	/* The texture parameters used in this texture */
	private TextureParameters parameters;
	
	/* The default constructor */
	public Texture() {
		this.pointer = GL11.glGenTextures();
		this.parameters = TextureParameters.DEFAULT;
		
		TextureManager.add(this);
	}
	
	/* The other constructors */
	public Texture(TextureParameters parameters) {
		this.pointer = GL11.glGenTextures();
		this.parameters = parameters;
		
		TextureManager.add(this);
	}
	
	public Texture(int pointer) {
		this.pointer = pointer;
		this.parameters = TextureParameters.DEFAULT;
		
		TextureManager.add(this);
	}
	
	public Texture(int pointer, TextureParameters parameters) {
		this.pointer = pointer;
		this.parameters = parameters;
		
		TextureManager.add(this);
	}
	
	public Texture(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/* The methods used to apply the texture parameters to this texture */
	public void applyParameters() {
		if (this.pointer != - 1)
			this.parameters.apply(this.pointer);
	}
	
	public void applyParameters(boolean unbind) {
		if (this.pointer != - 1)
			this.parameters.apply(this.pointer, unbind);
	}
	
	public void applyParameters(boolean bind, boolean unbind) {
		if (this.pointer != - 1)
			this.parameters.apply(this.pointer, bind, unbind);
	}
	
	/* The methods used to bind and unbind this texture */
	public void bind() { GL11.glBindTexture(this.parameters.getTarget(), this.pointer); }
	public void unbind() { GL11.glBindTexture(this.parameters.getTarget(), 0); }
	
	/* The method used to delete this texture */
	public void delete() {
		GL11.glDeleteTextures(this.pointer);
	}
	
	/* The setters and getters */
	public void setParameters(TextureParameters parameters) { this.parameters = parameters; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public void setSize(int width, int height) { this.width = width; this.height = height; }
	public void setNumberOfColourComponents(int numberOfColourComponents) { this.numberOfColourComponents = numberOfColourComponents; }
	public int getPointer() { return this.pointer; }
	public TextureParameters getParameters() { return this.parameters; }
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public int getNumberOfColourComponents() { return this.numberOfColourComponents; }
	public boolean hasTexture() { return this.pointer != -1; }
	
	/* The static method used to load and setup a texture given a buffered image */
	public static Texture load(BufferedImage bufferedImage) {
		return load(bufferedImage, TextureParameters.DEFAULT);
	}
	
	/* The static method used to load and setup a texture given a buffered image */
	public static Texture load(BufferedImage bufferedImage, TextureParameters parameters) {
		//Create the texture and set its variables
		Texture texture = new Texture(parameters);
		int imageWidth = bufferedImage.getWidth();
		int imageHeight = bufferedImage.getHeight();
		int numberOfColourComponents = bufferedImage.getColorModel().getNumComponents();
		texture.setSize(imageWidth, imageHeight);
		texture.setNumberOfColourComponents(numberOfColourComponents);
		//Get the colour mode
		int colourMode = 0;
		if (numberOfColourComponents == 1)
			colourMode = GL11.GL_RED;
		else if (numberOfColourComponents == 2)
			colourMode = GL30.GL_RG;
		else if (numberOfColourComponents == 3)
			colourMode = GL11.GL_RGB;
		else if (numberOfColourComponents == 4)
			colourMode = GL11.GL_RGBA;
		else
			Logger.log("Andor - Texture load()", "Unknown number of colour components " + numberOfColourComponents, Log.ERROR);
		
		//The data array
		byte[] data = new byte[numberOfColourComponents * imageWidth * imageHeight];
		bufferedImage.getRaster().getDataElements(0, 0, imageWidth, imageHeight, data);
		ByteBuffer pixels = BufferUtils.createFlippedBuffer(data);
		
		texture.bind();
		GL11.glTexImage2D(texture.parameters.getTarget(), 0, colourMode, texture.getWidth(), texture.getHeight(), 0, colourMode, GL11.GL_UNSIGNED_BYTE, pixels);
		//Apply the parameters
		texture.applyParameters(false, true);
		
		return texture;
	}
	
}