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

import org.andor.core.render.ForwardRenderer;
import org.andor.core.render.RenderData;

public class RenderableObject3D extends Object3D {
	
	/* The render data */
	private RenderData renderData;
	
	/* The model matrix */
	private Matrix4f modelMatrix;
	
	/* The constructors */
	public RenderableObject3D(RenderData renderData) {
		//Assign the variables
		this.renderData = renderData;
		this.modelMatrix = new Matrix4f();
	}
	
	public RenderableObject3D(RenderData renderData, float width, float height, float depth) {
		this(renderData);
		this.setSize(width, height, depth);
	}
	
	/* The method used to update this object */
	public void update() {
		//Update the model matrix
		modelMatrix.setIdentity();
		Vector3f p = this.getPosition();
		float width = getWidth();
		float height = getHeight();
		float depth = getDepth();
		modelMatrix.translate(new Vector3f(p.x + width / 2, p.y + height / 2, p.z + depth / 2));
		modelMatrix.rotate(this.getRotation());
		modelMatrix.translate(new Vector3f(-width / 2, -height / 2, -depth / 2));
		modelMatrix.scale(this.getScale());
	}
	
	/* The method used to render this object */
	public void render() {
		ForwardRenderer.render(this.renderData, this.modelMatrix);
	}
	
	/* The setters and getters */
	public void setRenderData(RenderData renderData) { this.renderData = renderData; }
	public void setModelMatrix(Matrix4f modelMatrix) { this.modelMatrix = modelMatrix; }
	public RenderData getRenderData() { return this.renderData; }
	public Matrix4f getModelMatrix() { return this.modelMatrix; }
	
}