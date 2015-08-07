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

public class Camera3D extends Object3D implements Camera {
	
	/* The projection matrix */
	public Matrix4f projectionMatrix;
	
	/* The view matrix */
	public Matrix4f viewMatrix;
	
	/* The variable used to enable the movement up and down when looking (Flying) */
	public boolean flying;
	
	/* The sky box */
	private SkyBox skyBox;
	
	/* Should the sky box be used if available */
	public boolean useSkyBox;
	
	/* The constructors */
	public Camera3D(Matrix4f projectionMatrix) { super(); this.projectionMatrix = projectionMatrix; this.viewMatrix = new Matrix4f(); this.useSkyBox = true; }
	public Camera3D(Matrix4f projectionMatrix, Vector3f position) { super(position); this.projectionMatrix = projectionMatrix; this.viewMatrix = new Matrix4f(); this.useSkyBox = true; }
	public Camera3D(Matrix4f projectionMatrix, Vector3f position, Vector3f rotation) { super(position, rotation); this.projectionMatrix = projectionMatrix; this.viewMatrix = new Matrix4f(); this.useSkyBox = true; }
	public Camera3D(Matrix4f projectionMatrix, Vector3f position, Vector3f rotation, Vector3f scale) { super(position, rotation, scale); this.projectionMatrix = projectionMatrix; this.viewMatrix = new Matrix4f(); this.useSkyBox = true; }
	
	/* The method used to update this camera */
	public void update() {
		this.viewMatrix.setIdentity();
		this.viewMatrix.transformR(this.getPosition(), this.getRotation(), this.getScale());
	}
	
	/* The method used to use this camera's view */
	public void useView() {
		this.viewMatrix.setIdentity();
		this.viewMatrix.transformR(this.getPosition(), this.getRotation(), this.getScale());
		
		//Check to see whether the skybox should be used
		if (this.skyBox != null && this.useSkyBox) {
			//Get the position
			Vector3f pos = this.getPosition().clone();
			//Multiply the position by -1
			pos.multiply(-1f);
			//Set the sky box's position
			this.skyBox.box.position = pos;
			//Render the skybox based on this camera's position
			this.skyBox.renderSkybox();
		}
	}
	
	/* The method used to move the camera forwards relative to its rotation */
	public void moveForward(float amount) {
		//Move the camera 'forwards'
		this.position.x -= amount * (float) Math.sin(Math.toRadians(this.getRotation().y));
		this.position.z += amount * (float) Math.cos(Math.toRadians(this.getRotation().y));
		//Check whether flying is enabled
		if (this.flying)
			this.position.y += amount * (float) Math.tan(Math.toRadians(this.getRotation().x));
	}
	
	/* The method used to move the camera backwards relative to its rotation */
	public void moveBackward(float amount) {
		//Move the camera 'backwards'
		this.position.x += amount * (float) Math.sin(Math.toRadians(this.getRotation().y));
		this.position.z -= amount * (float) Math.cos(Math.toRadians(this.getRotation().y));
		//Check whether flying is enabled
		if (this.flying)
			this.position.y -= amount * (float) Math.tan(Math.toRadians(this.getRotation().x));
	}
	
	/* The method used to move the camera left relative to its rotation */
	public void moveLeft(float amount) {
		//Move the camera 'left'
		this.position.x -= amount * (float) Math.sin(Math.toRadians(this.getRotation().y - 90));
		this.position.z += amount * (float) Math.cos(Math.toRadians(this.getRotation().y - 90));
	}
	
	/* The method used to move the camera right relative to its rotation */
	public void moveRight(float amount) {
		//Move the camera 'right'
		this.position.x += amount * (float) Math.sin(Math.toRadians(this.getRotation().y - 90));
		this.position.z -= amount * (float) Math.cos(Math.toRadians(this.getRotation().y - 90));
	}
	
	/* The setters and getters */
	public void setProjectionMatrix(Matrix4f projectionMatrix) { this.projectionMatrix = projectionMatrix; }
	public void setViewMatrix(Matrix4f viewMatrix) { this.viewMatrix = viewMatrix; }
	public void setFlying(boolean flying) { this.flying = flying; }
	public void setSkyBox(SkyBox skyBox) { this.skyBox = skyBox; }
	public void toggleFlying() { this.flying = ! this.flying; }
	public Matrix4f getProjectionMatrix() { return this.projectionMatrix; }
	public Matrix4f getViewMatrix() { return this.viewMatrix; }
	public Matrix4f getProjectionViewMatrix() { return this.projectionMatrix.multiply(this.viewMatrix); }
	public boolean isFlying() { return this.flying; }
	public SkyBox getSkyBox() { return this.skyBox; }
	
}