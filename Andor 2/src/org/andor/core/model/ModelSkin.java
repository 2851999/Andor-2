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

import java.util.ArrayList;
import java.util.List;

import org.andor.core.Matrix4f;
import org.andor.core.render.Renderer;

public class ModelSkin {
	
	/* The model instance */
	public Model model;
	
	/* The bind shape matrix */
	public Matrix4f bindShapeMatrix;
	
	/* The vertex groups */
	public List<ModelVertexGroup> vertexGroups;
	
	/* The constructor */
	public ModelSkin(Model model) {
		this.model = model;
		this.vertexGroups = new ArrayList<ModelVertexGroup>();
	}
	
	/* The method used to setup the model */
	public void setup() {
		for (int a = 0; a < vertexGroups.size(); a++)
			this.vertexGroups.get(a).setup();
	}
	
	/* The method used to update the model */
	public void update() {
		for (int a = 0; a < vertexGroups.size(); a++)
			this.vertexGroups.get(a).update();
	}
	
	/* The method used to render this skin */
	public void render(Matrix4f modelMatrix) {
		for (int a = 0; a < vertexGroups.size(); a++)
			Renderer.render(this.vertexGroups.get(a).data, modelMatrix);
	}
	
}