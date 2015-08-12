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
import org.andor.core.render.Material;
import org.andor.core.render.RenderData;
import org.lwjgl.opengl.GL11;

public class ANGeometry {
	
	/* The data stored in this geometry */
	public int numberOfVertices;
	public float[] vertices;
	public float[] normals;
	public float[] textureCoordinates;
	public short[] indices;
	
	public Material material;
	
	/* The method used to construct render data for this geometry */
	public RenderData createRenderData() {
		RenderData data = new RenderData(GL11.GL_TRIANGLES, true, true, true, true, this.material);
		data.setup(3, vertices, null, normals, textureCoordinates, indices);
		data.updateColour(Colour.WHITE);
		return data;
	}
	
}