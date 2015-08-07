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

public class Object2DBuilder {
	
	/* Various parameters used in object creation */
	public static boolean separateVertices = false;
	public static boolean separateColours = false;
	public static boolean separateTextureCoordinates = false;
	public static boolean separateNormals = false;
	
	/* The static methods used to create a quad */
	public static RenderData createQuad(float width, float height, Texture texture, Colour[] colours) {
		return createQuad(width, height, texture, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(float width, float height, Texture texture, Colour colour) {
		return createQuad(width, height, texture, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(float width, float height, Colour[] colours) {
		return createQuad(width, height, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(float width, float height, Colour colour) {
		return createQuad(width, height, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(float width, float height) {
		return createQuad(width, height, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Texture texture, Colour[] colours) {
		return createQuad(v0, v1, v2, v3, texture, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Texture texture, Colour colour) {
		return createQuad(v0, v1, v2, v3, texture, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Colour[] colours) {
		return createQuad(v0, v1, v2, v3, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Colour colour) {
		return createQuad(v0, v1, v2, v3, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3) {
		return createQuad(v0, v1, v2, v3, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(float width, float height, Texture texture, Colour[] colours, RenderData data) {
		data.setup(createQuadVertices(width, height, texture, colours), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(float width, float height, Texture texture, Colour colour, RenderData data) {
		data.setup(createQuadVertices(width, height, texture, colour), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(float width, float height, Colour[] colours, RenderData data) {
		data.setup(createQuadVertices(width, height, colours), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(float width, float height, Colour colour, RenderData data) {
		data.setup(createQuadVertices(width, height, colour), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(float width, float height, RenderData data) {
		data.setup(createQuadVertices(width, height), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Texture texture, Colour[] colours, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3, texture, colours), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Texture texture, Colour colour, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3, texture, colour), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Colour[] colours, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3, colours), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Colour colour, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3, colour), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3), createQuadIndices());
		return data;
	}
	
	public static Vertex2D[] createQuadVertices(float width, float height, Texture texture, Colour[] colours) {
		Vertex2D[] vertices = createQuadVertices(width, height, texture);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex2D[] createQuadVertices(float width, float height, Texture texture, Colour colour) {
		Vertex2D[] vertices = createQuadVertices(width, height, texture);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex2D[] createQuadVertices(float width, float height, Colour[] colours) {
		Vertex2D[] vertices = createQuadVertices(width, height);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex2D[] createQuadVertices(float width, float height, Colour colour) {
		Vertex2D[] vertices = createQuadVertices(width, height);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex2D[] createQuadVertices(float width, float height, Texture texture) {
		return createQuadVertices(new Vector2f(0, 0), new Vector2f(width, 0), new Vector2f(width, height), new Vector2f(0, height), texture);
	}
	
	public static Vertex2D[] createQuadVertices(float width, float height) {
		return createQuadVertices(new Vector2f(0, 0), new Vector2f(width, 0), new Vector2f(width, height), new Vector2f(0, height));
	}
	
	public static Vertex2D[] createQuadVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Texture texture, Colour[] colours) {
		Vertex2D[] vertices = createQuadVertices(v0, v1, v2, v3, texture);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex2D[] createQuadVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Texture texture, Colour colour) {
		Vertex2D[] vertices = createQuadVertices(v0, v1, v2, v3, texture);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex2D[] createQuadVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Colour[] colours) {
		Vertex2D[] vertices = createQuadVertices(v0, v1, v2, v3);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex2D[] createQuadVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Colour colour) {
		Vertex2D[] vertices = createQuadVertices(v0, v1, v2, v3);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex2D[] createQuadVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3, Texture texture) {
		return new Vertex2D[] { new Vertex2D(v0, new Vector2f(texture.left, texture.top)), new Vertex2D(v1, new Vector2f(texture.right, texture.top)), new Vertex2D(v2, new Vector2f(texture.right, texture.bottom)), new Vertex2D(v3, new Vector2f(texture.left, texture.bottom)) };
	}
	
	public static Vertex2D[] createQuadVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f v3) {
		return new Vertex2D[] { new Vertex2D(v0), new Vertex2D(v1), new Vertex2D(v2), new Vertex2D(v3) };
	}
	
	public static float[] createQuadTextureCoordinates(Texture texture) {
		return new float[] { texture.left, texture.top, texture.right, texture.top, texture.right, texture.bottom, texture.left, texture.bottom };
	}
	
	public static short[] createQuadIndices() {
		return new short[] {
			0, 1, 2,
			2, 3, 0
		};
	}
	
	/* The static methods used to create a circle */
	public static RenderData createCircle(float radius, int segments, Colour[] colours) {
		return createCircle(radius, segments, colours, new RenderData(GL11.GL_TRIANGLE_FAN, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCircle(float radius, int segments, Colour colour) {
		return createCircle(radius, segments, colour, new RenderData(GL11.GL_TRIANGLE_FAN, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCircle(float radius, int segments) {
		return createCircle(radius, segments, new RenderData(GL11.GL_TRIANGLE_FAN, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCircle(float radius, int segments, Colour[] colours, RenderData data) {
		data.setup(createCircleVertices(radius, segments, colours), null);
		return data;
	}
	
	public static RenderData createCircle(float radius, int segments, Colour colour, RenderData data) {
		data.setup(createCircleVertices(radius, segments, colour), null);
		return data;
	}
	
	public static RenderData createCircle(float radius, int segments, RenderData data) {
		data.setup(createCircleVertices(radius, segments), null);
		return data;
	}
	
	public static Vertex2D[] createCircleVertices(float radius, int segments, Colour[] colours) {
		Vertex2D[] vertices = createCircleVertices(new Vector2f(), radius, segments);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex2D[] createCircleVertices(float radius, int segments, Colour colour) {
		Vertex2D[] vertices = createCircleVertices(new Vector2f(), radius, segments);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex2D[] createCircleVertices(float radius, int segments) {
		return createCircleVertices(new Vector2f(), radius, segments);
	}
	
	public static Vertex2D[] createCircleVertices(Vector2f centre, float radius, int segments) {
		//Create the list
		Vertex2D[] vertices = new Vertex2D[segments * 2];
		//Calculate the angle at the radius of each segment
		float theta = (float) (2 * Math.PI / segments);
		//Calculate the sine and cosine values
		float sine = (float) Math.sin(theta);
		float cos = (float) Math.cos(theta);
		//Define the other values needed
		float t;
		float x = radius;
		float y = 0;
		//Go through the vertices array
		for (int a = 0; a < vertices.length; a++) {
			//Output the vertices
			vertices[a] = new Vertex2D(new Vector2f(x + centre.x, y + centre.y));
			
			t = x;
			x = cos * x - sine * y;
			y = sine * t + cos * y;
		}
		//Return the vertices
		return vertices;
	}
	
	/* The static methods used to create a triangle */
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour[] colours) {
		return createTriangle(v0, v1, v2, t0, t1, t2, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour colour) {
		return createTriangle(v0, v1, v2, t0, t1, t2, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Colour[] colours) {
		return createTriangle(v0, v1, v2, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Colour colour) {
		return createTriangle(v0, v1, v2, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2) {
		return createTriangle(v0, v1, v2, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour[] colours, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2, t0, t1, t2, colours), createTriangleIndices());
		return data;
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour colour, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2, t0, t1, t2, colour), createTriangleIndices());
		return data;
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Colour[] colours, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2, colours), createTriangleIndices());
		return data;
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Colour colour, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2, colour), createTriangleIndices());
		return data;
	}
	
	public static RenderData createTriangle(Vector2f v0, Vector2f v1, Vector2f v2, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2), createTriangleIndices());
		return data;
	}
	
	public static Vertex2D[] createTriangleVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour[] colours) {
		Vertex2D[] vertices = createTriangleVertices(v0, v1, v2, t0, t1, t2);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex2D[] createTriangleVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour colour) {
		Vertex2D[] vertices = createTriangleVertices(v0, v1, v2, t0, t1, t2);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex2D[] createTriangleVertices(Vector2f v0, Vector2f v1, Vector2f v2, Colour[] colours) {
		Vertex2D[] vertices = createTriangleVertices(v0, v1, v2);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex2D[] createTriangleVertices(Vector2f v0, Vector2f v1, Vector2f v2, Colour colour) {
		Vertex2D[] vertices = createTriangleVertices(v0, v1, v2);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex2D[] createTriangleVertices(Vector2f v0, Vector2f v1, Vector2f v2) {
		return new Vertex2D[] { new Vertex2D(v0), new Vertex2D(v1), new Vertex2D(v2) };
	}
	
	public static Vertex2D[] createTriangleVertices(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f t0, Vector2f t1, Vector2f t2) {
		return new Vertex2D[] { new Vertex2D(v0, t0), new Vertex2D(v1, t1), new Vertex2D(v2, t2) };
	}
	
	public static short[] createTriangleIndices() {
		return new short[] { 0, 1, 2 };
	}
	
	/* The static methods used to set the colour of a set of vertices */
	public static void setColour(Vertex2D[] vertices, Colour colour) {
		for (int a = 0; a < vertices.length; a++)
			vertices[a].setColour(colour);
	}
	
	public static void setColour(Vertex2D[] vertices, Colour[] colours) {
		//The current colour
		int currentColour = 0;
		for (int a = 0; a < vertices.length; a++) {
			vertices[a].setColour(colours[currentColour]);
			currentColour++;
			if (currentColour >= colours.length)
				currentColour = 0;
		}
	}
	
}