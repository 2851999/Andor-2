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

import org.andor.utils.MathUtils;

public class Vector3f {
	
	/* The values contained within this vector */
	public float x;
	public float y;
	public float z;
	
	/* The default constructor */
	public Vector3f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	/* The other constructors */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector2f vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = 0;
	}
	
	public Vector3f(Vector2f vector, float z) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = z;
	}
	
	/* The methods used to perform operations on this vector */
	public Vector3f addX(float x) { this.x += x; return this; }
	public Vector3f addY(float y) { this.y += y; return this; }
	public Vector3f addZ(float z) { this.z += z; return this; }
	public Vector3f add(float v) { this.x += v; this.y += v; this.z += v; return this; }
	public Vector3f subtractX(float x) { this.x -= x; return this; }
	public Vector3f subtractY(float y) { this.y -= y; return this; }
	public Vector3f subtractZ(float z) { this.z -= z; return this; }
	public Vector3f subtract(float v) { this.x -= v; this.y -= v; this.z -= v; return this; }
	public Vector3f divideX(float x) { this.x /= x; return this; }
	public Vector3f divideY(float y) { this.y /= y; return this; }
	public Vector3f divideZ(float z) { this.z /= z; return this; }
	public Vector3f divide(float v) { this.x /= v; this.y /= v; this.z /= v; return this; }
	public Vector3f multiplyX(float x) { this.x *= x; return this; }
	public Vector3f multiplyY(float y) { this.y *= y; return this; }
	public Vector3f multiplyZ(float z) { this.z *= z; return this; }
	public Vector3f multiply(float v) { this.x *= v; this.y *= v; this.z *= v; return this; }
	
	/* The methods used to perform operations between another vector and this */
	public Vector3f add(Vector3f other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	public Vector3f subtract(Vector3f other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	public Vector3f divide(Vector3f other) {
		this.x /= other.x;
		this.y /= other.y;
		this.z /= other.z;
		return this;
	}
	
	public Vector3f multiply(Vector3f other) {
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		return this;
	}
	
	/* Some methods used to calculate various values */
	public float length() { return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z); }
	public float min() { return MathUtils.min(MathUtils.min(this.x, this.y), this.z); }
	public float max() { return MathUtils.max(MathUtils.max(this.x, this.y), this.z); }
	/* NOTE: Normalise will modify this vectors value, whereas normalised will return a
	 * separate one */
	public Vector3f normalise() {
		float length = this.length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}
	public Vector3f normalised() {
		return clone().normalise();
	}
	public Vector3f min(Vector3f other) { return new Vector3f(MathUtils.min(this.x, other.x), MathUtils.min(this.y, other.y), MathUtils.min(this.z, other.z)); }
	public Vector3f max(Vector3f other) { return new Vector3f(MathUtils.max(this.x, other.x), MathUtils.max(this.y, other.y), MathUtils.max(this.z, other.z)); }
	public float dot(Vector3f other) { return this.x * other.x + this.y * other.y + this.z * other.z; }
	public Vector3f cross(Vector3f other) {
		return new Vector3f(
				y * other.z - z * other.y,
				z * other.x - x * other.z,
				x * other.y - y * other.x
		);
	}
	//public Vector3f reflect(Vector3f normal) { return clone().subtract(normal.clone().multiply(this.dot(normal) * 2)); }
	
	/* The other utility methods */
	public String toString() { return "(" + this.x + "," + this.y + "," + this.z + ")"; }
	public static Vector3f fromString(String s) {
		String numbers[] = s.substring(1, s.length() - 2).split(",");
		return new Vector3f(Float.parseFloat(numbers[0]), Float.parseFloat(numbers[1]), Float.parseFloat(numbers[2]));
	}
	public Vector3f clone() { return new Vector3f(this.x, this.y, this.z); }
	
	/* The setters and getters */
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }
	public float getX() { return this.x; }
	public float getY() { return this.y; }
	public float getZ() { return this.z; }
	public float[] getValues() { return new float[] { this.x, this.y, this.z }; }
	
}