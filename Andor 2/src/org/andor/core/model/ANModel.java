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

public class ANModel {
	
	/* The geometry */
	public List<ANGeometry> geometry;
	
	/* The constructor */
	public ANModel() {
		this.geometry = new ArrayList<ANGeometry>();
	}
	
	/* The method used to get a model */
	public Model createModel() {
		//Create the model
		Model model = new Model();
		//Go through the geometry
		for (int a = 0; a < geometry.size(); a++)
			//Add the current geometry data
			model.data.add(geometry.get(a).createRenderData());
		//Return the model
		return model;
	}
	
}