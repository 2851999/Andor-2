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
import org.andor.utils.MathUtils;

public class ModelJoint {
	
	/* The name */
	public String name;
	
	/* The skin instance */
	public ModelSkin skin;
	
	/* The various matrices */
	public Matrix4f jointMatrix;
	public Matrix4f worldMatrix;
	public Matrix4f inverseBindMatrix;
	
	/* The parent joint */
	public ModelJoint parent;
	
	/* The other data */
	public float[] keyframes;
	public float animationTime;
	public Matrix4f[] keyframeTransforms;
	public int lastKeyframe;
	
	/* The child joints */
	public List<ModelJoint> children;
	
	/* The constructor */
	public ModelJoint() {
		this.children = new ArrayList<ModelJoint>();
	}
	
	/* The method used to setup this joint for a specific key frame */
	public void setup(int keyframe, float i) {
		if (keyframe < this.keyframes.length) {
			
			Matrix4f current = this.keyframeTransforms[keyframe];
			Matrix4f next = null;
			if (keyframe + 1 < this.keyframes.length)
				next = this.keyframeTransforms[keyframe + 1];
			else
				next = current;
			Matrix4f keyframeTransform = new Matrix4f();
			
			for (int a = 0; a < 16; a++)
				keyframeTransform.getValues()[a] = MathUtils.interpolate(current.getValues()[a], next.getValues()[a], i);
			
			if (this.parent == null)
				this.worldMatrix = (keyframeTransform);
			else
				this.worldMatrix = this.parent.worldMatrix.multiply(keyframeTransform); 
		}
		for (int a = 0; a < children.size(); a++)
			this.children.get(a).setup(keyframe, i);
		
		this.lastKeyframe = keyframe;
	}
	
}