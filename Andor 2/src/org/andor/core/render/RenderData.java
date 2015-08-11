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

package org.andor.core.render;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import org.andor.core.Colour;
import org.andor.core.Vertex;
import org.andor.utils.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderData {
	
	/* The constant values */
	public static final int VERTEX_VALUES_2D = 2;
	public static final int VERTEX_VALUES_3D = 3;
	public static final int COLOUR_VALUES = 4;
	public static final int TEXTURE_COORDINATE_VALUES = 2;
	public static final int NORMAL_VALUES = 3;
	
	/* The list of RenderData instances */
	private static List<RenderData> instances = new ArrayList<RenderData>();
	
	/* The various pieces of data stored for rendering */
	private int renderMode;
	private int numberOfVertices;
	private int vertexValuesCount;
	private int indicesCount;
	
	private int verticesOffset;
	private int verticesStride;
	private int coloursOffset;
	private int coloursStride;
	private int textureCoordinatesOffset;
	private int textureCoordinatesStride;
	private int normalsOffset;
	private int normalsStride;
	
	private boolean hasVertices;
	private boolean hasColours;
	private boolean hasTextureCoordinates;
	private boolean hasNormals;
	private boolean hasIndices;
	private boolean separateVertices;
	private boolean separateColours;
	private boolean separateTextureCoordinates;
	private boolean separateNormals;
	
	/* The various float buffers */
	private FloatBuffer vertices;
	private FloatBuffer colours;
	private FloatBuffer textureCoordinates;
	private FloatBuffer normals;
	private FloatBuffer other;
	private ShortBuffer indices;
	
	/* The various vbo's and the vao */
	private int vao = -1;
	private int verticesVBO = -1;
	private int coloursVBO = -1;
	private int textureCoordinatesVBO = -1;
	private int normalsVBO = -1;
	private int otherVBO = -1;
	private int indicesVBO = -1;
	
	/* The various usage variables */
	private int verticesUsage = GL15.GL_STATIC_DRAW;
	private int coloursUsage = GL15.GL_STATIC_DRAW;
	private int textureCoordinatesUsage = GL15.GL_STATIC_DRAW;
	private int normalUsage = GL15.GL_STATIC_DRAW;
	private int otherUsage = GL15.GL_STATIC_DRAW;
	private int indicesUsage = GL15.GL_STATIC_DRAW;
	
	/* The material of this render data */
	private Material material;
	
	/* The constructor */
	public RenderData(int renderMode, boolean separateVertices, boolean separateColours, boolean separateTextureCoordinates, boolean separateNormals) {
		this.renderMode = renderMode;
		this.separateVertices = separateVertices;
		this.separateColours = separateColours;
		this.separateTextureCoordinates = separateTextureCoordinates;
		this.separateNormals = separateNormals;
		
		this.material = new Material("Default");
		
		//Add this to the list of instances
		instances.add(this);
	}
	
	/* The method used to setup this render data (note: indices can be null) */
	public void setup(int dimensions, float[] vertices, float[] colours, float[] normals, float[] textureCoordinates, short[] indices) {
		//Assign some of the data
		this.numberOfVertices = vertices.length / dimensions;
		this.vertexValuesCount = dimensions;
		//Assign the booleans
		this.hasVertices = vertices != null;
		this.hasColours = colours != null;
		this.hasTextureCoordinates = textureCoordinates != null;
		this.hasNormals = normals != null;
		this.hasIndices = false;
		//Create the buffers
		if (this.hasVertices)
			this.vertices = BufferUtils.createFlippedBuffer(vertices);
		if (this.hasColours)
			this.colours = BufferUtils.createFlippedBuffer(colours);
		if (this.hasTextureCoordinates)
			this.textureCoordinates = BufferUtils.createFlippedBuffer(textureCoordinates);
		if (this.hasNormals)
			this.normals = BufferUtils.createFlippedBuffer(normals);
		if (this.hasIndices)
			this.indices = BufferUtils.createFlippedBuffer(indices);
		this.setup(false);
	}
	
	/* The method used to setup this render data (note: indices can be null) */
	public void setup(Vertex[] vertices, short[] indices) {
		//Get the first vertex
		Vertex first = vertices[0];
		//Assign some of the data
		this.numberOfVertices = vertices.length;
		this.vertexValuesCount = first.getNumberOfDimensions();
		//Assign the booleans
		this.hasVertices = first.hasPosition();
		this.hasColours = first.hasColour();
		this.hasTextureCoordinates = first.hasTextureCoordinate();
		this.hasNormals = first.hasNormal();
		this.hasIndices = indices != null;
		//The capacity's needed for each of the buffers
		int vertexCapacity = this.numberOfVertices * this.vertexValuesCount;
		int colourCapacity = this.numberOfVertices * COLOUR_VALUES;
		int textureCoordinateCapacity = this.numberOfVertices * TEXTURE_COORDINATE_VALUES;
		int normalCapacity = this.numberOfVertices * NORMAL_VALUES;
		int otherCapacity = 0;
		//Setup the appropriate buffers
		if (this.hasVertices) {
			if (this.separateVertices)
				this.vertices = BufferUtils.createFloatBuffer(vertexCapacity);
			else
				otherCapacity += vertexCapacity;
		}
		
		if (this.hasColours) {
			if (this.separateColours)
				this.colours = BufferUtils.createFloatBuffer(colourCapacity);
			else
				otherCapacity += colourCapacity;
		}
		
		if (this.hasTextureCoordinates) {
			if (this.separateTextureCoordinates)
				this.textureCoordinates = BufferUtils.createFloatBuffer(textureCoordinateCapacity);
			else
				otherCapacity += textureCoordinateCapacity;
		}
		
		if (this.hasNormals) {
			if (this.separateNormals)
				this.normals = BufferUtils.createFloatBuffer(textureCoordinateCapacity);
			else
				otherCapacity += normalCapacity;
		}
		
		if (otherCapacity > 0)
			this.other = BufferUtils.createFloatBuffer(otherCapacity);
		
		//Go through the vertices
		for (int a = 0; a < this.numberOfVertices; a++) {
			if (this.hasVertices) {
				if (this.separateVertices)
					this.vertices.put(vertices[a].getPositionValues());
				else
					this.other.put(vertices[a].getPositionValues());
			}
			if (this.hasColours) {
				if (this.separateColours)
					this.colours.put(vertices[a].getColourValues());
				else
					this.other.put(vertices[a].getColourValues());
			}
			
			if (this.hasTextureCoordinates) {
				if (this.separateTextureCoordinates)
					this.textureCoordinates.put(vertices[a].getTextureCoordinateValues());
				else
					this.other.put(vertices[a].getTextureCoordinateValues());
			}
			
			if (this.hasNormals) {
				if (this.separateNormals)
					this.normals.put(vertices[a].getNormalValues());
				else
					this.other.put(vertices[a].getNormalValues());
			}
		}
		
		//Setup the indices
		if (this.hasIndices) {
			this.indicesCount = indices.length;
			this.indices = BufferUtils.createBuffer(indices);
		}
		
		//Setup the rest of the data
		this.setup(true);
	}
	
	/* The method used to setup the data given the indices */
	private void setup(boolean flip) {
		//Setup the vao
		if (vao == -1)
			this.vao = GL30.glGenVertexArrays();
		//Bind the vao
		GL30.glBindVertexArray(this.vao);
		int currentStride = 0;
		//Check to see whether each buffer has been set, then set them up
		if (this.vertices != null) {
			//Give the data to OpenGL after setting up the VBO
			if (flip)
				this.vertices.flip();
			if (this.verticesVBO == -1)
				this.verticesVBO = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.verticesVBO);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, this.vertices, this.verticesUsage);
			
			GL20.glVertexAttribPointer(0, this.vertexValuesCount, GL11.GL_FLOAT, false, 0, 0);
		} else if (this.hasVertices)
			currentStride += this.vertexValuesCount * Float.BYTES;
		if (this.colours != null) {
			if (flip)
				this.colours.flip();
			if (this.coloursVBO == -1)
				this.coloursVBO = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.coloursVBO);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, this.colours, this.coloursUsage);
			
			GL20.glVertexAttribPointer(1, COLOUR_VALUES, GL11.GL_FLOAT, false, 0, 0);
		} else if (this.hasColours)
			currentStride += COLOUR_VALUES * Float.BYTES;
		if (this.textureCoordinates != null) {
			if (flip)
				this.textureCoordinates.flip();
			if (this.textureCoordinatesVBO == -1)
				this.textureCoordinatesVBO = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.textureCoordinatesVBO);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, this.textureCoordinates, this.textureCoordinatesUsage);
			
			GL20.glVertexAttribPointer(2, TEXTURE_COORDINATE_VALUES, GL11.GL_FLOAT, false, 0, 0);
		} else if (this.hasTextureCoordinates)
			currentStride += TEXTURE_COORDINATE_VALUES * Float.BYTES;
		if (this.normals != null) {
			if (flip)
				this.normals.flip();
			if (this.normalsVBO == -1)
				this.normalsVBO = GL15.glGenBuffers();
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.normalsVBO);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, this.normals, this.normalUsage);
			
			GL20.glVertexAttribPointer(3, NORMAL_VALUES, GL11.GL_FLOAT, false, 0, 0);
		} else if (this.hasNormals)
			currentStride += NORMAL_VALUES * Float.BYTES;
		if (this.other != null) {
			//Figure out what data is within the other buffer
			int currentOffset = 0;
			if (flip)
				other.flip();
			if (this.otherVBO == -1)
				this.otherVBO = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.otherVBO);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, other, this.otherUsage);
			//Setup the stride and offset values
			if (this.hasVertices && this.vertices == null) {
				this.verticesOffset = currentOffset;
				this.verticesStride = currentStride;
				currentOffset += this.vertexValuesCount * Float.BYTES;
				
				GL20.glVertexAttribPointer(0, this.vertexValuesCount, GL11.GL_FLOAT, false, this.verticesStride, this.verticesOffset);
			}
			if (this.hasColours && this.colours == null) {
				this.coloursOffset = currentOffset;
				this.coloursStride = currentStride;
				currentOffset += COLOUR_VALUES * Float.BYTES;
				
				GL20.glVertexAttribPointer(1, COLOUR_VALUES, GL11.GL_FLOAT, false, this.coloursStride, this.coloursOffset);
			}
			if (this.hasTextureCoordinates && this.textureCoordinates == null) {
				this.textureCoordinatesOffset = currentOffset;
				this.textureCoordinatesStride = currentStride;
				currentOffset += TEXTURE_COORDINATE_VALUES * Float.BYTES;
				
				GL20.glVertexAttribPointer(2, TEXTURE_COORDINATE_VALUES, GL11.GL_FLOAT, false, this.textureCoordinatesStride, this.textureCoordinatesOffset);
			}
			if (this.hasNormals && this.normals == null) {
				this.normalsOffset = currentOffset;
				this.normalsStride = currentStride;
				currentOffset += NORMAL_VALUES * Float.BYTES;
				
				GL20.glVertexAttribPointer(3, NORMAL_VALUES, GL11.GL_FLOAT, false, this.normalsStride, this.normalsOffset);
			}
		}
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		//Unbind the vao
		GL30.glBindVertexArray(0);
		
		if (this.indices != null) {
			//Setup the indices
			this.indices.flip();
			
			if (this.indicesVBO == -1)
				this.indicesVBO = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.indicesVBO);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, this.indices, this.indicesUsage);
		}
		
		//Allow gc to clear up unnecessary data
		this.vertices = null;
		this.colours = null;
		this.textureCoordinates = null;
		this.normals = null;
		this.other = null;
		this.indices = null;
	}
	
	/* The method used to delete all of the data within this render data */
	public void delete() {
		GL30.glDeleteVertexArrays(this.vao);
		if (this.verticesVBO != -1)
			GL15.glDeleteBuffers(this.verticesVBO);
		if (this.coloursVBO != -1)
			GL15.glDeleteBuffers(this.coloursVBO);
		if (this.textureCoordinatesVBO != -1)
			GL15.glDeleteBuffers(this.textureCoordinatesVBO);
		if (this.normalsVBO != -1)
			GL15.glDeleteBuffers(this.normalsVBO);
	}
	
	public void updateVertices(Vertex[] vertices) {
		this.vertices = BufferUtils.createFloatBuffer(this.numberOfVertices * this.vertexValuesCount);
		for (int a = 0; a < this.numberOfVertices; a++)
			this.vertices.put(vertices[a].getPositionValues());
		this.hasVertices = true;
		this.setup(true);
	}
	
	public void updateColour(Colour colour) {
		this.colours = BufferUtils.createFloatBuffer(this.numberOfVertices * COLOUR_VALUES);
		for (int a = 0; a < this.numberOfVertices; a++)
			this.colours.put(colour.getValues());
		this.hasColours = true;
		this.setup(true);
	}
	
	public void updateColour(Colour[] colours) {
		this.colours = BufferUtils.createFloatBuffer(this.numberOfVertices * COLOUR_VALUES);
		int currentColour = 0;
		for (int a = 0; a < this.numberOfVertices; a++) {
			this.colours.put(colours[currentColour].getValues());
			currentColour++;
			if (currentColour >= colours.length)
				currentColour = 0;
		}
		this.hasColours = true;
		this.setup(true);
	}
	
	public void updateColour(Vertex[] vertices) {
		this.colours = BufferUtils.createFloatBuffer(this.numberOfVertices * COLOUR_VALUES);
		for (int a = 0; a < this.numberOfVertices; a++)
			this.colours.put(vertices[a].getColourValues());
		this.hasColours = true;
		this.setup(true);
	}
	
	public void updateTextureCoordinates(Vertex[] vertices) {
		this.textureCoordinates = BufferUtils.createFloatBuffer(this.numberOfVertices * TEXTURE_COORDINATE_VALUES);
		for (int a = 0; a < this.numberOfVertices; a++)
			this.textureCoordinates.put(vertices[a].getTextureCoordinateValues());
		this.hasTextureCoordinates = true;
		this.setup(true);
	}
	
	public void updateNormals(Vertex[] vertices) {
		this.normals = BufferUtils.createFloatBuffer(this.numberOfVertices * NORMAL_VALUES);
		for (int a = 0; a < this.numberOfVertices; a++)
			this.normals.put(vertices[a].getNormalValues());
		this.hasNormals = true;
		this.setup(true);
	}
	
	public void updateIndices(short[] indices) {
		this.indicesCount = indices.length;
		this.indices = BufferUtils.createBuffer(indices);
		this.hasIndices = true;
		this.setup(true);
	}
	
	public void updateVertices(float[] vertices) {
		this.numberOfVertices = vertices.length / this.vertexValuesCount;
		this.vertices = BufferUtils.createBuffer(vertices);
		this.hasVertices = true;
		this.setup(true);
	}
	
	public void updateColour(float[] colours) {
		this.colours = BufferUtils.createBuffer(colours);
		this.hasColours = true;
		this.setup(true);
	}
	
	public void updateTextureCoordinates(float[] textureCoordinates) {
		this.textureCoordinates = BufferUtils.createBuffer(textureCoordinates);
		this.hasTextureCoordinates = true;
		this.setup(true);
	}
	
	public void updateNormals(float[] normals) {
		this.normals = BufferUtils.createBuffer(normals);
		this.hasNormals = true;
		this.setup(true);
	}
	
	/* The setters and getters */
	public void setVerticesUsage(int verticesUsage) { this.verticesUsage = verticesUsage; }
	public void setColoursUsage(int coloursUsage) { this.coloursUsage = coloursUsage; }
	public void setTextureCoordinatesUsage(int textureCoordinatesUsage) { this.textureCoordinatesUsage = textureCoordinatesUsage; }
	public void setNormalsUsage(int normalsUsage) { this.normalUsage = normalsUsage; }
	public void setIndicesUsage(int indicesUsage) { this.indicesUsage = indicesUsage; }
	public void setMaterial(Material material) { this.material = material; }
	public int getRenderMode() { return this.renderMode; }
	public int getNumberOfVertices() { return this.numberOfVertices; }
	public int getVertexValuesCount() { return this.vertexValuesCount; }
	public int getIndicesCount() { return this.indicesCount; }
	public int getVerticesOffset() { return this.verticesOffset; }
	public int getVerticesStride() { return this.verticesStride; }
	public int getColoursOffset() { return this.coloursOffset; }
	public int getColoursStride() { return this.coloursStride; }
	public int getTextureCoordinatesOffset() { return this.textureCoordinatesOffset; }
	public int getTextureCoordinatesStride() { return this.textureCoordinatesStride; }
	public int getNormalsOffset() { return this.normalsOffset; }
	public int getNormalsStride() { return this.normalsStride; }
	public boolean hasVertices() { return this.hasVertices; }
	public boolean hasColours() { return this.hasColours; }
	public boolean hasTextureCoordinates() { return this.hasTextureCoordinates; }
	public boolean hasNormals() { return this.hasNormals; }
	public boolean hasIndices() { return this.hasIndices; }
	public boolean separateVertices() { return this.separateVertices; }
	public boolean separateColours() { return this.separateColours; }
	public boolean separateTextureCoordinates() { return this.separateTextureCoordinates; }
	public boolean separateNormals() { return this.separateNormals; }
	public int getVAO() { return this.vao; }
	public int getIndicesVBO() { return this.indicesVBO; }
	public Material getMaterial() { return this.material; }
	public boolean hasMaterial() { return this.material != null; }
	
	/* The static method used to delete all of the instances */
	public static void deleteAll() {
		for (int a = 0; a < instances.size(); a++)
			instances.get(a).delete();
	}
	
}