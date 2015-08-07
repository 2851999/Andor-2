package org.andor.core.resource.texture;
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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.andor.core.Vector2f;

public class TextureSet {
	
	/* The loaded textures */
	public List<BufferedImage> loadedTextures;
	
	/* The textures */
	public List<Texture> textures;
	
	/* The constructor */
	public TextureSet() {
		//Assign the variables
		this.loadedTextures = new ArrayList<BufferedImage>();
		this.textures = new ArrayList<Texture>();
	}
	
	/* The method used to join the textures and calculate the
	 * texture coordinates for each texture */
	public Texture join() {
		return join(TextureParameters.DEFAULT);
	}
	
	/* The method used to join the textures and calculate the
	 * texture coordinates for each texture */
	public Texture join(TextureParameters parameters) {
		//Get the total size of the textures
		Vector2f totalSize = getTotalSize();
		//Create a buffered image
		BufferedImage bufferedImage = new BufferedImage((int) totalSize.x, (int) totalSize.y, BufferedImage.TYPE_4BYTE_ABGR);
		//Create the graphics object
		Graphics2D g2d = bufferedImage.createGraphics();
		//The current x position
		float x = 0;
		//Go through each texture
		for (int a = 0; a < this.loadedTextures.size(); a++) {
			//Draw the image
			g2d.drawImage(this.loadedTextures.get(a), (int) x, 0, null);
			//Add onto the texture coordinate values
			float top = (0f) / bufferedImage.getHeight();
			float bottom = ((float) this.loadedTextures.get(a).getHeight()) / bufferedImage.getHeight();
			float left = (x / bufferedImage.getWidth());
			float right = (x + this.loadedTextures.get(a).getWidth()) / bufferedImage.getWidth();
			//Set the equivalent texture's texture coordinates
			this.textures.get(a).top = top;
			this.textures.get(a).bottom = bottom;
			this.textures.get(a).left = left;
			this.textures.get(a).right = right;
			//Add onto the x position
			x += this.loadedTextures.get(a).getWidth();
		}
		//Return the texture
		return Texture.load(bufferedImage, parameters);
	}
	
	/* The method used to load a texture */
	public Texture load(String filePath, boolean external) {
		//Load the image
		BufferedImage bufferedImage = TextureLoader.loadBufferedImage(filePath, external);
		//Add the image to the list of loaded textures
		loadedTextures.add(bufferedImage);
		//Create the image instance
		Texture texture = new Texture(bufferedImage.getWidth(), bufferedImage.getHeight());
		//Add the texture to the list of texture
		this.textures.add(texture);
		//Return the texture
		return texture;
	}
	
	/* The method used to get the total width and height of all of the textures */
	public Vector2f getTotalSize() {
		//The total width
		float totalWidth = 0;
		float totalHeight = 0;
		//Go through all of the textures
		for (int a = 0; a < this.textures.size(); a++) {
			//Add onto the total width
			totalWidth += this.textures.get(a).getWidth();
			//Get the current texture's height 
			float height = this.textures.get(a).getHeight();
			//Check the total height against the current texture's height
			if (height > totalHeight)
				//Set the total height
				totalHeight = height;
		}
		//Return the values in a Vector2D
		return new Vector2f(totalWidth, totalHeight);
	}
	
}