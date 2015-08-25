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

import org.andor.core.Matrix4f;
import org.andor.core.Vector2f;
import org.andor.core.Vector3f;

public class ModelVertex {
	
	/* The position and normal */
	public Vector3f position;
	public Vector3f normal;
	
	/* The texture coordinate */
	public Vector2f textureCoordinate;
	
	/* The joints this vertex is joined to */
	public ModelJoint[] joints;
	
	/* The weight values for each joint */
	public float[] weights;
	
	/* The method used to clone this vertex */
	public ModelVertex clone() {
		ModelVertex clone = new ModelVertex();
		clone.position = position;
		clone.joints = joints;
		clone.weights = weights;
		return clone;
	}
	
	/* The methods used to check what values have been assigned */
	public boolean hasPosition() { return this.position != null; }
	public boolean hasNormal() { return this.normal != null; }
	public boolean hasTextureCoordinate() { return this.textureCoordinate != null; }
	public boolean hasJoints() { return this.joints != null; }
	public boolean hasWeights() { return this.weights != null; }
	
	/* The method used to get the position from this vertex */
	public Vector3f getPosition() {
		if (joints != null) {
			float totalWeight = 0;
			Vector3f currentPosition = new Vector3f();
			for (int a = 0; a < joints.length; a++) {
				ModelJoint current = joints[a];
				
//				Vector3f row1 = new Vector3f(current.inverseBindMatrix.getValues()[0], current.inverseBindMatrix.getValues()[1], current.inverseBindMatrix.getValues()[2]);
//				Vector3f row2 = new Vector3f(current.inverseBindMatrix.getValues()[4], current.inverseBindMatrix.getValues()[5], current.inverseBindMatrix.getValues()[6]);
//				Vector3f row3 = new Vector3f(current.inverseBindMatrix.getValues()[8], current.inverseBindMatrix.getValues()[9], current.inverseBindMatrix.getValues()[10]);
//				row1.normalise();
//				row2.normalise();
//				row3.normalise();
//				current.inverseBindMatrix.getValues()[0] = row1.x;
//				current.inverseBindMatrix.getValues()[1] = row1.y;
//				current.inverseBindMatrix.getValues()[2] = row1.z;
//				
//				current.inverseBindMatrix.getValues()[4] = row2.x;
//				current.inverseBindMatrix.getValues()[5] = row2.y;
//				current.inverseBindMatrix.getValues()[6] = row2.z;
//				
//				current.inverseBindMatrix.getValues()[8] = row3.x;
//				current.inverseBindMatrix.getValues()[9] = row3.y;
//				current.inverseBindMatrix.getValues()[10] = row3.z;
				
				Vector3f row1 = new Vector3f(current.worldMatrix.getValues()[0], current.worldMatrix.getValues()[1], current.worldMatrix.getValues()[2]);
				Vector3f row2 = new Vector3f(current.worldMatrix.getValues()[4], current.worldMatrix.getValues()[5], current.worldMatrix.getValues()[6]);
				Vector3f row3 = new Vector3f(current.worldMatrix.getValues()[8], current.worldMatrix.getValues()[9], current.worldMatrix.getValues()[10]);
				row1.normalise();
				row2.normalise();
				row3.normalise();
				current.worldMatrix.getValues()[0] = row1.x;
				current.worldMatrix.getValues()[1] = row1.y;
				current.worldMatrix.getValues()[2] = row1.z;
				
				current.worldMatrix.getValues()[4] = row2.x;
				current.worldMatrix.getValues()[5] = row2.y;
				current.worldMatrix.getValues()[6] = row2.z;
				
				current.worldMatrix.getValues()[8] = row3.x;
				current.worldMatrix.getValues()[9] = row3.y;
				current.worldMatrix.getValues()[10] = row3.z;
				
				if (current.inverseBindMatrix != null) {
					Matrix4f matrix = current.worldMatrix.multiply(current.inverseBindMatrix).multiply(current.skin.bindShapeMatrix);
					currentPosition = currentPosition.add(matrix.multiply(this.position).multiply(weights[a]));
				}
				totalWeight += weights[a];
			}
			if (totalWeight != 1.0f) {
				currentPosition = currentPosition.multiply(1.0f / totalWeight);
			}
			return currentPosition;
		} else
			return this.position;
	}
	
}