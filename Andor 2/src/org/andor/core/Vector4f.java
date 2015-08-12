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

public class Vector4f {
	
	/* The values contained within this vector */
	public float x;
	public float y;
	public float z;
	public float w;
	
	/* The default constructor */
	public Vector4f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}
	
	/* The other constructors */
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f(Vector3f vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
		this.w = 0;
	}
	
	public Vector4f(Vector3f vector, float w) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
		this.w = w;
	}
	
	/* The methods used to perform operations on this vector */
	public Vector4f addX(float x) { this.x += x; return this; }
	public Vector4f addY(float y) { this.y += y; return this; }
	public Vector4f addZ(float z) { this.z += z; return this; }
	public Vector4f addW(float w) { this.w += w; return this; }
	public Vector4f add(float v) { this.x += v; this.y += v; this.z += v; this.z += v; return this; }
	public Vector4f subtractX(float x) { this.x -= x; return this; }
	public Vector4f subtractY(float y) { this.y -= y; return this; }
	public Vector4f subtractZ(float z) { this.z -= z; return this; }
	public Vector4f subtractW(float w) { this.w -= w; return this; }
	public Vector4f subtract(float v) { this.x -= v; this.y -= v; this.z -= v; this.w -= v; return this; }
	public Vector4f divideX(float x) { this.x /= x; return this; }
	public Vector4f divideY(float y) { this.y /= y; return this; }
	public Vector4f divideZ(float z) { this.z /= z; return this; }
	public Vector4f divideW(float w) { this.w /= w; return this; }
	public Vector4f divide(float v) { this.x /= v; this.y /= v; this.z /= v; this.w /= v; return this; }
	public Vector4f multiplyX(float x) { this.x *= x; return this; }
	public Vector4f multiplyY(float y) { this.y *= y; return this; }
	public Vector4f multiplyZ(float z) { this.z *= z; return this; }
	public Vector4f multiplyW(float w) { this.w *= w; return this; }
	public Vector4f multiply(float v) { this.x *= v; this.y *= v; this.z *= v; this.w *= v; return this; }
	
	/* The methods used to perform operations between another vector and this */
	public Vector4f add(Vector4f other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		this.w += other.w;
		return this;
	}
	
	public Vector4f subtract(Vector4f other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		this.w -= other.w;
		return this;
	}
	
	public Vector4f divide(Vector4f other) {
		this.x /= other.x;
		this.y /= other.y;
		this.z /= other.z;
		this.w /= other.w;
		return this;
	}
	
	public Vector4f multiply(Vector4f other) {
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		this.w *= other.w;
		return this;
	}
	
	/* Some methods used to calculate various values */
	public float length() { return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w); }
	public float min() { return MathUtils.min(MathUtils.min(MathUtils.min(this.x, this.y), this.z), this.w); }
	public float max() { return MathUtils.max(MathUtils.max(MathUtils.max(this.x, this.y), this.z), this.w); }
	/* NOTE: Normalise will modify this vectors value, whereas normalised will return a
	 * separate one */
	public Vector4f normalise() {
		float length = this.length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;
		return this;
	}
	public Vector4f normalised() {
		return clone().normalise();
	}
	public Vector4f min(Vector4f other) { return new Vector4f(MathUtils.min(this.x, other.x), MathUtils.min(this.y, other.y), MathUtils.min(this.z, other.z), MathUtils.min(this.w, other.w)); }
	public Vector4f max(Vector4f other) { return new Vector4f(MathUtils.max(this.x, other.x), MathUtils.max(this.y, other.y), MathUtils.max(this.z, other.z), MathUtils.max(this.w, other.w)); }
	//public float dot(Vector4f other) { return this.x * other.x + this.y * other.y; } Check these
	//public float cross(Vector4f other) { return this.x * other.x - this.y * other.y; } Check these
	//public Vector3f reflect(Vector3f normal) { return clone().subtract(normal.clone().multiply(this.dot(normal) * 2)); }
	
	/* The other utility methods */
	public String toString() { return "(" + this.x + "," + this.y + "," + this.z + "," + this.w + ")"; }
	public static Vector4f fromString(String s) {
		String numbers[] = s.substring(1, s.length() - 2).split(",");
		return new Vector4f(Float.parseFloat(numbers[0]), Float.parseFloat(numbers[1]), Float.parseFloat(numbers[2]), Float.parseFloat(numbers[3]));
	}
	public Vector4f clone() { return new Vector4f(this.x, this.y, this.z, this.w); }
	
	/* The setters and getters */
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }
	public void setW(float w) { this.w = w; }
	public float getX() { return this.x; }
	public float getY() { return this.y; }
	public float getZ() { return this.z; }
	public float getW() { return this.w; }
	public float[] getValues() { return new float[] { this.x, this.y, this.z, this.w }; }
	
}