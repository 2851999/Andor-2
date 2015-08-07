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

public class Vector2f {
	
	/* The values contained within this vector */
	public float x;
	public float y;
	
	/* The default constructor */
	public Vector2f() {
		this.x = 0;
		this.y = 0;
	}
	
	/* The other constructors */
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/* The methods used to perform operations on this vector */
	public Vector2f addX(float x) { this.x += x; return this; }
	public Vector2f addY(float y) { this.y += y; return this; }
	public Vector2f add(float v) { this.x += v; this.y += v; return this; }
	public Vector2f subtractX(float x) { this.x -= x; return this; }
	public Vector2f subtractY(float y) { this.y -= y; return this; }
	public Vector2f subtract(float v) { this.x -= v; this.y -= v; return this; }
	public Vector2f divideX(float x) { this.x /= x; return this; }
	public Vector2f divideY(float y) { this.y /= y; return this; }
	public Vector2f divide(float v) { this.x /= v; this.y /= v; return this; }
	public Vector2f multiplyX(float x) { this.x *= x; return this; }
	public Vector2f multiplyY(float y) { this.y *= y; return this; }
	public Vector2f multiply(float v) { this.x *= v; this.y *= v; return this; }
	
	/* The methods used to perform operations between another vector and this */
	public Vector2f add(Vector2f other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2f subtract(Vector2f other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}
	
	public Vector2f divide(Vector2f other) {
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}
	
	public Vector2f multiply(Vector2f other) {
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}
	
	/* Some methods used to calculate various values */
	public float length() { return (float) Math.sqrt(this.x * this.x + this.y * this.y); }
	public float min() { return MathUtils.min(this.x, this.y); }
	public float max() { return MathUtils.max(this.x, this.y); }
	/* NOTE: Normalise will modify this vectors value, whereas normalised will return a
	 * separate one */
	public Vector2f normalise() {
		float length = this.length();
		this.x /= length;
		this.y /= length;
		return this;
	}
	public Vector2f normalised() {
		return clone().normalise();
	}
	public Vector2f min(Vector2f other) { return new Vector2f(MathUtils.min(this.x, other.x), MathUtils.min(this.y, other.y)); }
	public Vector2f max(Vector2f other) { return new Vector2f(MathUtils.max(this.x, other.x), MathUtils.max(this.y, other.y)); }
	public float dot(Vector2f other) { return this.x * other.x + this.y * other.y; }
	public float cross(Vector2f other) { return this.x * other.x - this.y * other.y; }
	public Vector2f reflect(Vector2f normal) { return clone().subtract(normal.clone().multiply(this.dot(normal) * 2)); }
	
	/* The other utility methods */
	public String toString() { return "(" + this.x + "," + this.y + ")"; }
	public static Vector2f fromString(String s) {
		String numbers[] = s.substring(1, s.length() - 2).split(",");
		return new Vector2f(Float.parseFloat(numbers[0]), Float.parseFloat(numbers[1]));
	}
	public Vector2f clone() { return new Vector2f(this.x, this.y); }
	
	/* The setters and getters */
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public float getX() { return this.x; }
	public float getY() { return this.y; }
	public float[] getValues() { return new float[] { this.x, this.y }; }
	
}