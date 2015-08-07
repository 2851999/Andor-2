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

import java.util.ArrayList;
import java.util.List;

import org.andor.core.render.RenderData;
import org.andor.core.resource.texture.Texture;
import org.andor.utils.ArrayUtils;
import org.lwjgl.opengl.GL11;

public class BitmapText extends RenderableObject2D {
	
	/* The font size */
	public float fontSize;
	
	/* The number of cells on the width */
	public int gridWidth;
	
	/* The number of cells on the height */
	public int gridHeight;
	
	/* The width of a cell */
	public float cellWidth;
	
	/* The height of a cell */
	public float cellHeight;
	
	/* The current text */
	public String currentText;
	
	/* The colours */
	public Colour[] colour;
	
	/* The constructor of the font */
	public BitmapText(Texture texture, int gridSize, float fontSize, Colour[] colour) {
		super(Object2DBuilder.createQuad(1, 1, texture, Colour.WHITE, new RenderData(GL11.GL_TRIANGLES, true, true, true, true)));
		//Set the colour
		this.colour = colour;
		//Set the grid size
		this.gridWidth = gridSize;
		this.gridHeight = gridSize;
		this.cellWidth = texture.getWidth() / this.gridWidth;
		this.cellHeight = texture.getHeight() / this.gridHeight;
		//Set the size
		this.fontSize = fontSize;
		//Load the font texture
		this.getRenderData().getMaterial().setDiffuseTexture(texture);
	}
	
	/* The constructor of the font */
	public BitmapText(Texture texture, int gridSize, float fontSize, Colour colour) {
		this(texture, gridSize, fontSize, new Colour[] { colour });
	}
	
	/* The constructor of the font */
	public BitmapText(Texture texture, int gridSize, float fontSize) {
		this(texture, gridSize, fontSize, new Colour[] { Colour.WHITE });
	}
	
	/* The method that updates the text */
	public void update(String text) {
		//Set the current text
		this.currentText = text;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textures = new ArrayList<Float>();
		List<Short> indices = new ArrayList<Short>();
		float x = 0;
		
		//Calculate some values
		float mapWidth = this.getRenderData().getMaterial().getDiffuseTexture().getWidth();
		float mapHeight = this.getRenderData().getMaterial().getDiffuseTexture().getHeight();
		this.cellWidth = mapWidth / this.gridWidth;
		this.cellHeight = mapHeight / this.gridHeight;
		
		//Loop though the text
		for (int a = 0; a < text.length(); a++) {
			//Get the ASCII code of the character
			int asciiCode = (int) text.charAt(a);
			
			//Calculate the cell's x position
			float cellX = ((int) asciiCode % this.gridWidth) * this.cellWidth;
			//Calculate the cell's y position
			float cellY = (float) ((Math.floor((int) asciiCode / this.gridHeight)) * this.cellHeight);
			
			//Add the vertices
			vertices.add(x);
			vertices.add(0f);
			vertices.add(x + (this.cellWidth / this.cellHeight) * this.fontSize);
			vertices.add(0f);
			vertices.add(x + (this.cellWidth / this.cellHeight) * this.fontSize);
			vertices.add(this.fontSize);
			vertices.add(x);
			vertices.add(this.fontSize);
			
			//Add the textures
			textures.add(cellX / mapWidth);
			textures.add(cellY / mapHeight);
			textures.add((cellX + cellWidth) / mapWidth);
			textures.add(cellY / mapHeight);
			textures.add((cellX + cellWidth) / mapWidth);
			textures.add((cellY + cellHeight) / mapHeight);
			textures.add(cellX / mapWidth);
			textures.add((cellY + cellHeight) / mapHeight);
			
			//Get the draw order
			short[] cIndices = Object2DBuilder.createQuadIndices();
			//Add the draw order onto the draw order list
			indices.add((short) ((a * 4) + cIndices[0]));
			indices.add((short) ((a * 4) + cIndices[1]));
			indices.add((short) ((a * 4) + cIndices[2]));
			indices.add((short) ((a * 4) + cIndices[3]));
			indices.add((short) ((a * 4) + cIndices[4]));
			indices.add((short) ((a * 4) + cIndices[5]));
			
			x += ((this.cellWidth / this.cellHeight) * this.fontSize) / 1.5f;
		}
		//Update the renderer
		this.getRenderData().updateVertices(ArrayUtils.toFloatArray(vertices));
		this.getRenderData().updateColour(this.colour);
		this.getRenderData().updateTextureCoordinates(ArrayUtils.toFloatArray(textures));
		this.getRenderData().updateIndices(ArrayUtils.toShortArray(indices));
	}
	
	/* The method to get the width of a string */
	public float getWidth(String text) {
		return (text.length() * (((this.cellWidth / this.cellHeight) * this.fontSize) / 1.4f));
	}
	
	/* The method to get the height of a string */
	public float getHeight(String text) {
		//Return the height of the text
		return this.fontSize;
	}
	
	/* The get/set methods */
	public void setColour(Colour[] colours) {
		this.colour = colours;
		this.getRenderData().updateColour(this.colour);
	}
	public void setColour(Colour colour) {
		this.setColour(new Colour[] { colour });
	}
	public String getCurrentText() { return this.currentText; }
	public Colour[] getColour() { return this.colour; }
	
}