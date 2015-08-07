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

package org.andor.core.resource.audio;

import org.andor.core.Object3D;
import org.andor.core.Vector3f;

public class AudioObject {
	
	/* The position, velocity and rotation of this object */
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f velocity;
	
	/* The linked object if there is one */
	private Object3D attachedObject;
	
	/* The default constructor */
	public AudioObject() {
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		this.velocity = new Vector3f();
	}
	
	/* The other constructors */
	public AudioObject(Vector3f position) {
		this.position = position;
		this.rotation = new Vector3f();
		this.velocity = new Vector3f();
	}
	
	public AudioObject(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		this.velocity = new Vector3f();
	}
	
	public AudioObject(Vector3f position, Vector3f rotation, Vector3f velocity) {
		this.position = position;
		this.rotation = rotation;
		this.velocity = velocity;
	}
	
	public AudioObject(Object3D attachedObject) {
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		this.velocity = new Vector3f();
		this.attachedObject = attachedObject;
	}
	
	/* The setters and getters */
	public void setPosition(Vector3f position) { this.position = position; }
	public void setPosition(float x, float y, float z) { this.position.x = x; this.position.y = y; this.position.z = z; }
	public void setRotation(Vector3f rotation) { this.rotation = rotation; }
	public void setRotation(float x, float y, float z) { this.rotation.x = x; this.rotation.y = y; this.rotation.z = z; }
	public void setVelocity(Vector3f velocity) { this.velocity = velocity; }
	public void setVelocity(float x, float y, float z) { this.velocity.x = x; this.velocity.y = y; this.velocity.z = z; }
	public void attachObject(Object3D object) { this.attachedObject = object; }
	public Vector3f getPosition() {
		if (this.attachedObject != null)
			return this.attachedObject.getPosition().clone().add(this.position);
		else
			return this.position;
	}
	
	public Vector3f getRotation() {
		if (this.attachedObject != null)
			return this.attachedObject.getRotation().clone().add(this.rotation);
		else
			return this.rotation;
	}
	
	public Vector3f getVelocity() {
		return this.velocity;
	}
	
}