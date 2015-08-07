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

public class Object3DBuilder {
	
	/* Various parameters used in object creation */
	public static boolean separateVertices = false;
	public static boolean separateColours = false;
	public static boolean separateTextureCoordinates = false;
	public static boolean separateNormals = false;
	
	/* The static methods used to create a cube */
	public static RenderData createCube(float width, float height, float depth, Texture[] textures, Colour[] colours) {
		return createCube(width, height, depth, textures, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCube(float width, float height, float depth, Texture[] textures, Colour colour) {
		return createCube(width, height, depth, textures, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCube(float width, float height, float depth, Texture texture, Colour[] colours) {
		return createCube(width, height, depth, texture, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCube(float width, float height, float depth, Texture texture, Colour colour) {
		return createCube(width, height, depth, texture, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCube(float width, float height, float depth, Colour[] colours) {
		return createCube(width, height, depth, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCube(float width, float height, float depth, Colour colour) {
		return createCube(width, height, depth, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCube(float width, float height, float depth) {
		return createCube(width, height, depth, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createCube(float width, float height, float depth, Texture[] textures, Colour[] colours, RenderData data) {
		data.setup(createCubeVertices(width, height, depth, textures, colours), createCubeIndices());
		return data;
	}
	
	public static RenderData createCube(float width, float height, float depth, Texture[] textures, Colour colour, RenderData data) {
		data.setup(createCubeVertices(width, height, depth, textures, colour), createCubeIndices());
		return data;
	}
	
	public static RenderData createCube(float width, float height, float depth, Texture texture, Colour[] colours, RenderData data) {
		data.setup(createCubeVertices(width, height, depth, texture, colours), createCubeIndices());
		return data;
	}
	
	public static RenderData createCube(float width, float height, float depth, Texture texture, Colour colour, RenderData data) {
		data.setup(createCubeVertices(width, height, depth, texture, colour), createCubeIndices());
		return data;
	}
	
	public static RenderData createCube(float width, float height, float depth, Colour[] colours, RenderData data) {
		data.setup(createCubeVertices(width, height, depth, colours), createCubeIndices());
		return data;
	}
	
	public static RenderData createCube(float width, float height, float depth, Colour colour, RenderData data) {
		data.setup(createCubeVertices(width, height, depth, colour), createCubeIndices());
		return data;
	}
	
	public static RenderData createCube(float width, float height, float depth, RenderData data) {
		data.setup(createCubeVertices(width, height, depth), createCubeIndices());
		return data;
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth, Texture[] textures, Colour[] colours) {
		Vertex3D[] vertices = createCubeVertices(width, height, depth, textures);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth, Texture[] textures, Colour colour) {
		Vertex3D[] vertices = createCubeVertices(width, height, depth, textures);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth, Texture texture, Colour[] colours) {
		Vertex3D[] vertices = createCubeVertices(width, height, depth, texture);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth, Texture texture, Colour colour) {
		Vertex3D[] vertices = createCubeVertices(width, height, depth, texture);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth, Colour[] colours) {
		Vertex3D[] vertices = createCubeVertices(width, height, depth);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth, Colour colour) {
		Vertex3D[] vertices = createCubeVertices(width, height, depth);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth, Texture[] t) {
		//Calculate some needed values
		float w = width / 2;
		float h = height / 2;
		float d = depth / 2;
		return new Vertex3D[] {
				//Front face
				new Vertex3D(new Vector3f(-w, h, d), new Vector2f(t[0].left, t[0].top)),
				new Vertex3D(new Vector3f(w, h, d), new Vector2f(t[0].right, t[0].top)),
				new Vertex3D(new Vector3f(w, -h, d), new Vector2f(t[0].right, t[0].bottom)),
				new Vertex3D(new Vector3f(-w, -h, d), new Vector2f(t[0].left, t[0].bottom)),
				
				//Left face
				new Vertex3D(new Vector3f(-w, -h, d), new Vector2f(t[2].right, t[2].bottom)),
				new Vertex3D(new Vector3f(-w, -h, -d), new Vector2f(t[2].left, t[2].bottom)),
				new Vertex3D(new Vector3f(-w, h, -d), new Vector2f(t[2].left, t[2].top)),
				new Vertex3D(new Vector3f(-w, h, d), new Vector2f(t[2].right, t[2].top)),
				
				//Back face
				new Vertex3D(new Vector3f(-w, h, -d), new Vector2f(t[1].right, t[1].top)),
				new Vertex3D(new Vector3f(w, h, -d), new Vector2f(t[1].left, t[1].top)),
				new Vertex3D(new Vector3f(w, -h, -d), new Vector2f(t[1].left, t[1].bottom)),
				new Vertex3D(new Vector3f(-w, -h, -d), new Vector2f(t[1].right, t[1].bottom)),
				
				//Bottom face
				new Vertex3D(new Vector3f(w, -h, -d), new Vector2f(t[5].right, t[5].bottom)),
				new Vertex3D(new Vector3f(w, -h, d), new Vector2f(t[5].right, t[5].top)),
				new Vertex3D(new Vector3f(-w, -h, d), new Vector2f(t[5].left, t[5].top)),
				new Vertex3D(new Vector3f(-w, -h, -d), new Vector2f(t[5].left, t[5].bottom)),
				
				//Right face
				new Vertex3D(new Vector3f(w, -h, -d), new Vector2f(t[3].right, t[3].bottom)),
				new Vertex3D(new Vector3f(w, -h, d), new Vector2f(t[3].left, t[3].bottom)),
				new Vertex3D(new Vector3f(w, h, d), new Vector2f(t[3].left, t[3].top)),
				new Vertex3D(new Vector3f(w, h, -d), new Vector2f(t[3].right, t[3].top)),
				
				//Top face
				new Vertex3D(new Vector3f(-w, h, -d), new Vector2f(t[4].left, t[4].top)),
				new Vertex3D(new Vector3f(-w, h, d), new Vector2f(t[4].left, t[4].bottom)),
				new Vertex3D(new Vector3f(w, h, d), new Vector2f(t[4].right, t[4].bottom)),
				new Vertex3D(new Vector3f(w, h, -d), new Vector2f(t[4].right, t[4].top))
		};
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth, Texture t) {
		//Calculate some needed values
		float w = width / 2;
		float h = height / 2;
		float d = depth / 2;
		return new Vertex3D[] {
				//Front face
				new Vertex3D(new Vector3f(-w, h, d), new Vector2f(t.left, t.top)),
				new Vertex3D(new Vector3f(w, h, d), new Vector2f(t.right, t.top)),
				new Vertex3D(new Vector3f(w, -h, d), new Vector2f(t.right, t.bottom)),
				new Vertex3D(new Vector3f(-w, -h, d), new Vector2f(t.left, t.bottom)),
				
				//Left face
				new Vertex3D(new Vector3f(-w, -h, d), new Vector2f(t.right, t.bottom)),
				new Vertex3D(new Vector3f(-w, -h, -d), new Vector2f(t.left, t.bottom)),
				new Vertex3D(new Vector3f(-w, h, -d), new Vector2f(t.left, t.top)),
				new Vertex3D(new Vector3f(-w, h, d), new Vector2f(t.right, t.top)),
				
				//Back face
				new Vertex3D(new Vector3f(-w, h, -d), new Vector2f(t.right, t.top)),
				new Vertex3D(new Vector3f(w, h, -d), new Vector2f(t.left, t.top)),
				new Vertex3D(new Vector3f(w, -h, -d), new Vector2f(t.left, t.bottom)),
				new Vertex3D(new Vector3f(-w, -h, -d), new Vector2f(t.right, t.bottom)),
				
				//Bottom face
				new Vertex3D(new Vector3f(w, -h, -d), new Vector2f(t.right, t.bottom)),
				new Vertex3D(new Vector3f(w, -h, d), new Vector2f(t.right, t.top)),
				new Vertex3D(new Vector3f(-w, -h, d), new Vector2f(t.left, t.top)),
				new Vertex3D(new Vector3f(-w, -h, -d), new Vector2f(t.left, t.bottom)),
				
				//Right face
				new Vertex3D(new Vector3f(w, -h, -d), new Vector2f(t.right, t.bottom)),
				new Vertex3D(new Vector3f(w, -h, d), new Vector2f(t.left, t.bottom)),
				new Vertex3D(new Vector3f(w, h, d), new Vector2f(t.left, t.top)),
				new Vertex3D(new Vector3f(w, h, -d), new Vector2f(t.right, t.top)),
				
				//Top face
				new Vertex3D(new Vector3f(-w, h, -d), new Vector2f(t.left, t.top)),
				new Vertex3D(new Vector3f(-w, h, d), new Vector2f(t.left, t.bottom)),
				new Vertex3D(new Vector3f(w, h, d), new Vector2f(t.right, t.bottom)),
				new Vertex3D(new Vector3f(w, h, -d), new Vector2f(t.right, t.top))
		};
	}
	
	public static Vertex3D[] createCubeVertices(float width, float height, float depth) {
		//Calculate some needed values
		float w = width / 2;
		float h = height / 2;
		float d = depth / 2;
		return new Vertex3D[] {
				//Front face
				new Vertex3D(new Vector3f(-w, h, d)),
				new Vertex3D(new Vector3f(w, h, d)),
				new Vertex3D(new Vector3f(w, -h, d)),
				new Vertex3D(new Vector3f(-w, -h, d)),
				
				//Left face
				new Vertex3D(new Vector3f(-w, -h, d)),
				new Vertex3D(new Vector3f(-w, -h, -d)),
				new Vertex3D(new Vector3f(-w, h, -d)),
				new Vertex3D(new Vector3f(-w, h, d)),
				
				//Back face
				new Vertex3D(new Vector3f(-w, h, -d)),
				new Vertex3D(new Vector3f(w, h, -d)),
				new Vertex3D(new Vector3f(w, -h, -d)),
				new Vertex3D(new Vector3f(-w, -h, -d)),
				
				//Bottom face
				new Vertex3D(new Vector3f(w, -h, -d)),
				new Vertex3D(new Vector3f(w, -h, d)),
				new Vertex3D(new Vector3f(-w, -h, d)),
				new Vertex3D(new Vector3f(-w, -h, -d)),
				
				//Right face
				new Vertex3D(new Vector3f(w, -h, -d)),
				new Vertex3D(new Vector3f(w, -h, d)),
				new Vertex3D(new Vector3f(w, h, d)),
				new Vertex3D(new Vector3f(w, h, -d)),
				
				//Top face
				new Vertex3D(new Vector3f(-w, h, -d)),
				new Vertex3D(new Vector3f(-w, h, d)),
				new Vertex3D(new Vector3f(w, h, d)),
				new Vertex3D(new Vector3f(w, h, -d))
		};
	}
	
	public static short[] createCubeIndices() {
		return new short[] {
				//Front face
				//Bottom-Left triangle
				0, 1, 2,
				//Top-Right triangle
				2, 3, 0,
				
				//Left face
				//Bottom-Left triangle
				4, 5, 6,
				//Top-Right triangle
				6, 7, 4,
				
				//Back face
				//Bottom-Left triangle
				8, 9, 10,
				//Top-Right triangle
				10, 11, 8,
				
				//Bottom face
				//Bottom-Left triangle
				12, 13, 14,
				//Top-Right triangle
				14, 15, 12,
				
				//Right face
				//Bottom-Left triangle
				16, 17, 18,
				//Top-Right triangle
				18, 19, 16,
				
				//Top face
				//Bottom-Left triangle
				20, 21, 22,
				//Top-Right triangle
				22, 23, 20
			};
	}
	
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
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Texture texture, Colour[] colours) {
		return createQuad(v0, v1, v2, v3, texture, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Texture texture, Colour colour) {
		return createQuad(v0, v1, v2, v3, texture, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Colour[] colours) {
		return createQuad(v0, v1, v2, v3, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Colour colour) {
		return createQuad(v0, v1, v2, v3, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3) {
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
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Texture texture, Colour[] colours, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3, texture, colours), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Texture texture, Colour colour, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3, texture, colour), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Colour[] colours, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3, colours), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Colour colour, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3, colour), createQuadIndices());
		return data;
	}
	
	public static RenderData createQuad(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, RenderData data) {
		data.setup(createQuadVertices(v0, v1, v2, v3), createQuadIndices());
		return data;
	}
	
	public static Vertex3D[] createQuadVertices(float width, float height, Texture texture, Colour[] colours) {
		Vertex3D[] vertices = createQuadVertices(width, height, texture);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createQuadVertices(float width, float height, Texture texture, Colour colour) {
		Vertex3D[] vertices = createQuadVertices(width, height, texture);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createQuadVertices(float width, float height, Colour[] colours) {
		Vertex3D[] vertices = createQuadVertices(width, height);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createQuadVertices(float width, float height, Colour colour) {
		Vertex3D[] vertices = createQuadVertices(width, height);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createQuadVertices(float width, float height, Texture texture) {
		return createQuadVertices(new Vector3f(0, 0, 0), new Vector3f(width, 0, 0), new Vector3f(width, height, 0), new Vector3f(0, height, 0), texture);
	}
	
	public static Vertex3D[] createQuadVertices(float width, float height) {
		return createQuadVertices(new Vector3f(0, 0, 0), new Vector3f(width, 0, 0), new Vector3f(width, height, 0), new Vector3f(0, height, 0));
	}
	
	public static Vertex3D[] createQuadVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Texture texture, Colour[] colours) {
		Vertex3D[] vertices = createQuadVertices(v0, v1, v2, v3, texture);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createQuadVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Texture texture, Colour colour) {
		Vertex3D[] vertices = createQuadVertices(v0, v1, v2, v3, texture);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createQuadVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Colour[] colours) {
		Vertex3D[] vertices = createQuadVertices(v0, v1, v2, v3);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createQuadVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Colour colour) {
		Vertex3D[] vertices = createQuadVertices(v0, v1, v2, v3);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createQuadVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3, Texture texture) {
		return new Vertex3D[] { new Vertex3D(v0, new Vector2f(texture.left, texture.top)), new Vertex3D(v1, new Vector2f(texture.right, texture.top)), new Vertex3D(v2, new Vector2f(texture.right, texture.bottom)), new Vertex3D(v3, new Vector2f(texture.left, texture.bottom)) };
	}
	
	public static Vertex3D[] createQuadVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3) {
		return new Vertex3D[] { new Vertex3D(v0), new Vertex3D(v1), new Vertex3D(v2), new Vertex3D(v3) };
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
	
	/* The static methods used to create a triangle */
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour[] colours) {
		return createTriangle(v0, v1, v2, t0, t1, t2, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour colour) {
		return createTriangle(v0, v1, v2, t0, t1, t2, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Colour[] colours) {
		return createTriangle(v0, v1, v2, colours, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Colour colour) {
		return createTriangle(v0, v1, v2, colour, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2) {
		return createTriangle(v0, v1, v2, new RenderData(GL11.GL_TRIANGLES, separateVertices, separateColours, separateTextureCoordinates, separateNormals));
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour[] colours, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2, t0, t1, t2, colours), createTriangleIndices());
		return data;
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour colour, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2, t0, t1, t2, colour), createTriangleIndices());
		return data;
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Colour[] colours, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2, colours), createTriangleIndices());
		return data;
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Colour colour, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2, colour), createTriangleIndices());
		return data;
	}
	
	public static RenderData createTriangle(Vector3f v0, Vector3f v1, Vector3f v2, RenderData data) {
		data.setup(createTriangleVertices(v0, v1, v2), createTriangleIndices());
		return data;
	}
	
	public static Vertex3D[] createTriangleVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour[] colours) {
		Vertex3D[] vertices = createTriangleVertices(v0, v1, v2, t0, t1, t2);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createTriangleVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector2f t0, Vector2f t1, Vector2f t2, Colour colour) {
		Vertex3D[] vertices = createTriangleVertices(v0, v1, v2, t0, t1, t2);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createTriangleVertices(Vector3f v0, Vector3f v1, Vector3f v2, Colour[] colours) {
		Vertex3D[] vertices = createTriangleVertices(v0, v1, v2);
		setColour(vertices, colours);
		return vertices;
	}
	
	public static Vertex3D[] createTriangleVertices(Vector3f v0, Vector3f v1, Vector3f v2, Colour colour) {
		Vertex3D[] vertices = createTriangleVertices(v0, v1, v2);
		setColour(vertices, colour);
		return vertices;
	}
	
	public static Vertex3D[] createTriangleVertices(Vector3f v0, Vector3f v1, Vector3f v2) {
		return new Vertex3D[] { new Vertex3D(v0), new Vertex3D(v1), new Vertex3D(v2) };
	}
	
	public static Vertex3D[] createTriangleVertices(Vector3f v0, Vector3f v1, Vector3f v2, Vector2f t0, Vector2f t1, Vector2f t2) {
		return new Vertex3D[] { new Vertex3D(v0, t0), new Vertex3D(v1, t1), new Vertex3D(v2, t2) };
	}
	
	public static short[] createTriangleIndices() {
		return new short[] { 0, 1, 2 };
	}
	
	/* The static methods used to set the colour of a set of vertices */
	public static void setColour(Vertex3D[] vertices, Colour colour) {
		for (int a = 0; a < vertices.length; a++)
			vertices[a].setColour(colour);
	}
	
	public static void setColour(Vertex3D[] vertices, Colour[] colours) {
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