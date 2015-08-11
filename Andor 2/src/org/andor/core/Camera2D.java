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

import org.andor.Settings;

public class Camera2D extends Object2D implements Camera {
	
	/* The projection matrix */
	public Matrix4f projectionMatrix;
	
	/* The view matrix */
	public Matrix4f viewMatrix;
	
	/* The constructors */
	public Camera2D(Matrix4f projectionMatrix) { super(); this.projectionMatrix = projectionMatrix; this.viewMatrix = new Matrix4f(); this.update();  }
	public Camera2D(Matrix4f projectionMatrix, Vector2f position) { super(position); this.projectionMatrix = projectionMatrix; this.viewMatrix = new Matrix4f(); this.update(); }
	public Camera2D(Matrix4f projectionMatrix, Vector2f position, float rotation) { super(position, rotation); this.projectionMatrix = projectionMatrix; this.viewMatrix = new Matrix4f(); this.update(); }
	public Camera2D(Matrix4f projectionMatrix, Vector2f position, float rotation, Vector2f scale) { super(position, rotation, scale); this.projectionMatrix = projectionMatrix; this.viewMatrix = new Matrix4f(); this.update(); }
	
	/* The method used to update this camera */
	public void update() {
		this.viewMatrix.setIdentity();
		this.viewMatrix.transform(this.getPosition(), this.getRotation(), this.getScale());
	}
	
	/* The setters and getters */
	public void setProjectionMatrix(Matrix4f projectionMatrix) { this.projectionMatrix = projectionMatrix; }
	public void setViewMatrix(Matrix4f viewMatrix) { this.viewMatrix = viewMatrix; }
	public Matrix4f getProjectionMatrix() { return this.projectionMatrix; }
	public Matrix4f getViewMatrix() { return this.viewMatrix; }
	public Matrix4f getProjectionViewMatrix() { return this.projectionMatrix.multiply(this.viewMatrix); }
	
	/* The static methods used to create a camera */
	public static Camera2D createOrtho(float zNear, float zFar) {
		return new Camera2D(new Matrix4f().initOrtho(0, Settings.Window.Width, Settings.Window.Height, 0, zNear, zFar));
	}
	
	public static Camera2D createOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
		return new Camera2D(new Matrix4f().initOrtho(left, right, top, bottom, zNear, zFar));
	}
	
	public static Camera2D createPerspective(float fov, float zNear, float zFar) {
		return new Camera2D(new Matrix4f().initPerspective(fov, (float) Settings.Window.Width / (float) Settings.Window.Height, zNear, zFar));
	}
	
	public static Camera2D createPerspective(float fov, float aspect, float zNear, float zFar) {
		return new Camera2D(new Matrix4f().initPerspective(fov, aspect, zNear, zFar));
	}
	
}