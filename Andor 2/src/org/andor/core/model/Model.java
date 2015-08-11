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

import org.andor.core.RenderableObject3D;
import org.andor.core.render.RenderData;
import org.andor.core.render.Renderer;

public class Model extends RenderableObject3D {
	
	/* The render data */
	public List<RenderData> data;
	
	/* The constructor */
	public Model() {
		super(null);
		this.data = new ArrayList<RenderData>();
	}
	
	/* The method used to render this model */
	public void render() {
		//Go through the data and render it
		for (int a = 0; a < data.size(); a++)
			Renderer.render(data.get(a), this.getModelMatrix());
	}
	
}