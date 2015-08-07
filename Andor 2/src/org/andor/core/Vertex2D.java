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

import java.nio.FloatBuffer;

public class Vertex2D implements Vertex {
	
	/* The data in this vertex */
	private Vector2f position;
	private Colour colour;
	private Vector2f textureCoordinate;
	
	/* The default constructor */
	public Vertex2D() {}
	
	/* The other constructors */
	public Vertex2D(Vector2f position) {
		this.position = position;
	}
	
	public Vertex2D(Vector2f position, Colour colour) {
		this.position = position;
		this.colour = colour;
	}
	
	public Vertex2D(Vector2f position, Vector2f textureCoordinate) {
		this.position = position;
		this.textureCoordinate = textureCoordinate;
		this.colour = Colour.WHITE;
	}
	
	public Vertex2D(Vector2f position, Colour colour, Vector2f textureCoordinate) {
		this.position = position;
		this.colour = colour;
		this.textureCoordinate = textureCoordinate;
	}
	
	/* The method used to add this vertex's data to a float buffer */
	public void addData(FloatBuffer data) {
		//Add each piece of data as long as it exists
		if (position != null)
			data.put(this.position.getValues());
		if (colour != null)
			data.put(this.colour.getValues());
		if (textureCoordinate != null)
			data.put(this.textureCoordinate.getValues());
	}
	
	/* The setters and getters */
	public void setPosition(Vector2f position) { this.position = position; }
	public void setColour(Colour colour) { this.colour = colour; }
	public void setTextureCoordinate(Vector2f textureCoordinate) { this.textureCoordinate = textureCoordinate; }
	public Vector2f getPosition() { return this.position; }
	public Colour getColour() { return this.colour; }
	public Vector2f getTextureCoordinate() { return this.textureCoordinate; }
	public float[] getPositionValues() { return this.position.getValues(); }
	public float[] getColourValues() { return this.colour.getValues(); }
	public float[] getTextureCoordinateValues() { return this.textureCoordinate.getValues(); }
	public float[] getNormalValues() { return null; }
	public boolean hasPosition() { return this.position != null; }
	public boolean hasColour() { return this.colour != null; }
	public boolean hasTextureCoordinate() { return this.textureCoordinate != null; }
	public boolean hasNormal() { return false; }
	public int getNumberOfDimensions() { return 2; }
	
}