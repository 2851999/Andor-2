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

public class ModelSkeleton {
	
	/* The root joints */
	public List<ModelJoint> rootJoints;
	public List<ModelJoint> allJoints;
	
	/* The constructor */
	public ModelSkeleton() {
		this.rootJoints = new ArrayList<ModelJoint>();
		this.allJoints = new ArrayList<ModelJoint>();
	}
	
	/* The method used to setup the joints given a keyframe */
	public void setup(int keyframe, float i) {
		for (int a = 0; a < rootJoints.size(); a++)
			rootJoints.get(a).setup(keyframe, i);
	}
	
	/* The method used to get a joint given its name */
	public ModelJoint getJointByName(String name) {
		for (int a = 0; a < allJoints.size(); a++) {
			if (allJoints.get(a).name.equals(name))
				return allJoints.get(a);
		}
		return null;
	}
	
}