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

package org.andor.core.model;

import org.andor.core.Colour;
import org.andor.core.Vector3f;
import org.andor.core.render.Material;
import org.andor.core.render.RenderData;
import org.lwjgl.opengl.GL11;

public class ModelVertexGroup {
	
	/* The model skin instance */
	public ModelSkin skin;
	
	/* The skinned vertices and normals */
	public float[] skinnedVertices;
	public float[] skinnedNormals;
	
	/* The vertices within this group */
	public ModelVertex[] vertices;
	
	/* The material of this group */
	public Material material;
	
	/* The render data */
	public RenderData data;
	
	/* The constructor */
	public ModelVertexGroup(ModelSkin skin) {
		this.skin = skin;
	}
	
	/* The method used to setup this for rendering */
	public void setup() {
		if (this.data == null)
			this.data = new RenderData(GL11.GL_TRIANGLES, false, false, false, false);
		//Setup the arrays
		float[] vertices = new float[this.vertices.length * 3];
		//float[] textureCoordinates = new float[this.vertices.length * 2];
		//float[] normals  = new float[this.vertices.length * 3];
		//float[] textureCoordinates = new float[this.vertices.length * 2];
		//Go through the data
		for (int a = 0; a < this.vertices.length; a++) {
			Vector3f p = this.vertices[a].getPosition();
			vertices[(a * 3)] = p.x;
			vertices[(a * 3) + 1] = p.y;
			vertices[(a * 3) + 2] = p.z;
		}
		//for (int a = 0; a < this.vertices.length; a++) {
			//textureCoordinates[(a * 2)] = this.vertices[a].textureCoordinate.x;
			//textureCoordinates[(a * 2) + 1] = this.vertices[a].textureCoordinate.y;
		//}
		this.data.setup(3, vertices, null, null, null, null);
		this.data.updateColour(Colour.WHITE);
		this.data.setMaterial(material);
	}
	
	/* The method used to setup this for rendering */
	public void update() {
		//Setup the arrays
		float[] vertices = new float[this.vertices.length * 3];
		for (int a = 0; a < this.vertices.length; a++) {
			Vector3f p = this.vertices[a].getPosition();
			vertices[(a * 3)] = p.x;
			vertices[(a * 3) + 1] = p.y;
			vertices[(a * 3) + 2] = p.z;
		}
		data.updateVertices(vertices);
	}
	
}