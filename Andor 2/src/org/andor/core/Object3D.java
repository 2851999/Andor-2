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

public class Object3D extends BaseObject {
	
	/* The parent object (If it exists) */
	private Object3D parent;
	
	/* The data stored about this object */
	public Vector3f position;
	public Vector3f rotation;
	public Vector3f scale;
	
	/* The size of this object */
	public Vector3f size;
	
	/* The physics data */
	public Vector3f velocity;
	public Vector3f acceleration;
	public Vector3f angularVelocity;
	public Vector3f angularAcceleration;
	
	/* The default constructor */
	public Object3D() {
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = new Vector3f(1, 1, 1);
		this.size = new Vector3f();
		this.velocity = new Vector3f();
		this.acceleration = new Vector3f();
		this.angularVelocity = new Vector3f();
		this.angularAcceleration = new Vector3f();
	}
	
	/* The other constructors */
	public Object3D(Vector3f position) {
		this.position = position;
		this.rotation = new Vector3f();
		this.scale = new Vector3f(1, 1, 1);
		this.size = new Vector3f();
		this.velocity = new Vector3f();
		this.acceleration = new Vector3f();
		this.angularVelocity = new Vector3f();
		this.angularAcceleration = new Vector3f();
	}
	
	public Object3D(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		this.scale = new Vector3f(1, 1, 1);
		this.size = new Vector3f();
		this.velocity = new Vector3f();
		this.acceleration = new Vector3f();
		this.angularVelocity = new Vector3f();
		this.angularAcceleration = new Vector3f();
	}
	
	public Object3D(Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.size = new Vector3f();
		this.velocity = new Vector3f();
		this.acceleration = new Vector3f();
		this.angularVelocity = new Vector3f();
		this.angularAcceleration = new Vector3f();
	}
	
	/* The method used to attach a child object to this */
	public void attach(Object3D child) { child.setParent(this); }
	
	/* The setters and getters */
	public void setPosition(Vector3f position) { this.position = position; }
	public void setPosition(float x, float y, float z) { this.position.x = x; this.position.y = y; this.position.z = z; }
	public void setX(float x) { this.position.x = x; }
	public void setY(float y) { this.position.y = y; }
	public void setZ(float z) { this.position.z = z; }
	public void setRotation(Vector3f rotation) { this.rotation = rotation; }
	public void setRotation(float x, float y, float z) { this.rotation.x = x; this.rotation.y = y; this.rotation.z = z; }
	public void setRotationX(float x) { this.rotation.x = x; }
	public void setRotationY(float y) { this.rotation.y = y; }
	public void setRotationZ(float z) { this.rotation.z = z; }
	public void setScale(Vector3f scale) { this.scale = scale; }
	public void setScale(float x, float y, float z) { this.scale.x = x; this.scale.y = y; this.scale.z = z; }
	public void setScaleX(float x) { this.scale.x = x; }
	public void setScaleY(float y) { this.scale.y = y; }
	public void setScaleZ(float z) { this.scale.z = z; }
	public void setSize(Vector3f size) { this.size = size; }
	public void setSize(float width, float height, float depth) { this.size.x = width; this.size.y = height; this.size.z = depth; }
	public void setWidth(float width) { this.size.x = width; }
	public void setHeight(float height) { this.size.y = height; }
	public void setDepth(float depth) { this.size.z = depth; }
	public void setVelocity(Vector3f velocity) { this.velocity = velocity; }
	public void setVelocity(float x, float y, float z) { this.velocity.x = x; this.velocity.y = y; this.velocity.z = z; }
	public void setVelocityX(float x) { this.velocity.x = x; }
	public void setVelocityY(float y) { this.velocity.y = y; }
	public void setVelocity(float z) { this.velocity.z = z; }
	public void setAcceleration(Vector3f velocity) { this.velocity = velocity; }
	public void setAcceleration(float x, float y, float z) { this.velocity.x = x; this.velocity.y = y; this.velocity.z = z; }
	public void setAccelerationX(float x) { this.velocity.x = x; }
	public void setAccelerationY(float y) { this.velocity.y = y; }
	public void setAcceleration(float z) { this.velocity.z = z; }
	public void setAngularVelocity(Vector3f angularVelocity) { this.angularVelocity = angularVelocity; }
	public void setAngularVelocity(float x, float y, float z) { this.angularVelocity.x = x; this.angularVelocity.y = y; this.angularVelocity.z = z; }
	public void setAngularVelocityX(float x) { this.angularVelocity.x = x; }
	public void setAngularVelocityY(float y) { this.angularVelocity.y = y; }
	public void setAngularVelocityZ(float z) { this.angularVelocity.z = z; }
	public void setAngularAccceleration(Vector3f angularAcceleration) { this.angularAcceleration = angularAcceleration; }
	public void setAngularAccceleration(float x, float y, float z) { this.angularAcceleration.x = x; this.angularAcceleration.y = y; this.angularAcceleration.z = z; }
	public void setAngularAcccelerationX(float x) { this.angularAcceleration.x = x; }
	public void setAngularAcccelerationY(float y) { this.angularAcceleration.y = y; }
	public void setAngularAcccelerationZ(float z) { this.angularAcceleration.z = z; }
	
	public Vector3f getPosition() {
		if (parent == null)
			return this.position;
		else
			return this.parent.getPosition().clone().add(this.position);
	}
	
	public float getX() {
		if (parent == null)
			return this.position.x;
		else
			return this.parent.getPosition().x + this.position.x;
	}
	
	public float getY() {
		if (parent == null)
			return this.position.y;
		else
			return this.parent.getPosition().y + this.position.y;
	}
	
	public float getZ() {
		if (parent == null)
			return this.position.z;
		else
			return this.parent.getPosition().z + this.position.z;
	}
	
	public Vector3f getRotation() {
		if (parent == null)
			return this.rotation;
		else
			return this.parent.rotation.add(this.rotation);
	}
	
	public float getRotationX() {
		if (parent == null)
			return this.rotation.x;
		else
			return this.parent.getRotation().x + this.rotation.x;
	}
	
	public float getRotationY() {
		if (parent == null)
			return this.rotation.y;
		else
			return this.parent.getRotation().y + this.rotation.y;
	}
	
	public float getRotationZ() {
		if (parent == null)
			return this.rotation.z;
		else
			return this.parent.getRotation().z + this.rotation.z;
	}
	
	public Vector3f getScale() {
		if (parent == null)
			return this.scale;
		else
			return this.parent.getScale().clone().add(this.scale);
	}
	
	public float getScaleX() {
		if (parent == null)
			return this.scale.x;
		else
			return this.parent.getScale().x + this.scale.x;
	}
	
	public float getScaleY() {
		if (parent == null)
			return this.scale.y;
		else
			return this.parent.getScale().y + this.scale.y;
	}
	
	public float getScaleZ() {
		if (parent == null)
			return this.scale.z;
		else
			return this.parent.getScale().y + this.scale.z;
	}
	
	public Vector3f getSize() { return this.size; }
	public float getWidth() { return this.size.x * scale.x; }
	public float getHeight() { return this.size.y * scale.y; }
	public float getDepth() { return this.size.z * scale.z; }
	public Vector3f getCentre() { return this.getPosition().clone().add(this.size.clone().divide(2)); }
	
	public void setParent(Object3D parent) { this.parent = parent; }
	public Object3D getParent() { return this.parent; }
	
}