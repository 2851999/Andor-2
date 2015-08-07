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

public class Object2D extends BaseObject {
	
	/* The parent object (If it exists) */
	private Object2D parent;
	
	/* The data stored about this object */
	public Vector2f position;
	public float rotation;
	public Vector2f scale;
	
	/* The size of this object */
	public Vector2f size;
	
	/* The default constructor */
	public Object2D() {
		this.position = new Vector2f();
		this.rotation = 0;
		this.scale = new Vector2f(1, 1);
		this.size = new Vector2f();
	}
	
	/* The other constructors */
	public Object2D(Vector2f position) {
		this.position = position;
		this.rotation = 0;
		this.scale = new Vector2f(1, 1);
		this.size = new Vector2f();
	}
	
	public Object2D(Vector2f position, float rotation) {
		this.position = position;
		this.rotation = rotation;
		this.scale = new Vector2f(1, 1);
		this.size = new Vector2f();
	}
	
	public Object2D(Vector2f position, float rotation, Vector2f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.size = new Vector2f();
	}
	
	/* The method used to attach a child object to this */
	public void attach(Object2D child) { child.setParent(this); }
	
	/* The setters and getters */
	public void setPosition(Vector2f position) { this.position = position; }
	public void setPosition(float x, float y) { this.position.x = x; this.position.y = y; }
	public void setX(float x) { this.position.x = x; }
	public void setY(float y) { this.position.y = y; }
	public void setRotation(float rotation) { this.rotation = rotation; }
	public void setScale(Vector2f scale) { this.scale = scale; }
	public void setScale(float x, float y) { this.scale.x = x; this.scale.y = y; }
	public void setScaleX(float x) { this.scale.x = x; }
	public void setScaleY(float y) { this.scale.y = y; }
	public void setSize(Vector2f size) { this.size = size; }
	public void setSize(float width, float height) { this.size.x = width; this.size.y = height; }
	public void setWidth(float width) { this.size.x = width; }
	public void setHeight(float height) { this.size.y = height; }
	
	public Vector2f getPosition() {
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
	
	public float getRotation() {
		if (parent == null)
			return this.rotation;
		else
			return this.parent.rotation + this.rotation;
	}
	
	public Vector2f getScale() {
		if (parent == null)
			return this.scale;
		else
			return this.parent.getScale().clone().multiply(this.scale);
	}
	
	public float getScaleX() {
		if (parent == null)
			return this.scale.x;
		else
			return this.parent.scale.x * this.scale.x;
	}
	
	public float getScaleY() {
		if (parent == null)
			return this.scale.y;
		else
			return this.parent.scale.y * this.scale.y;
	}
	
	public Vector2f getSize() { return this.size; }
	public float getWidth() { return this.size.x * scale.x; }
	public float getHeight() { return this.size.y * scale.y; }
	public Vector2f getCentre() { return this.getPosition().clone().add(this.size.clone().divide(2)); }
	
	public Rectangle getBounds() {
		Vector2f p = this.getPosition();
		return new Rectangle(p.x + ((this.size.x * (1 - scale.x)) / 2), p.y + ((this.size.y * (1 - scale.y)) / 2), this.getWidth(), this.getHeight());
	}
	
	public void setParent(Object2D parent) { this.parent = parent; }
	public Object2D getParent() { return this.parent; }
	
}