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

public class Colour {
	
	/* The predefined colours */
	public static final Colour NONE = new Colour(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Colour BLACK = new Colour(0.0f, 0.0f, 0.0f);
	public static final Colour GREY = new Colour(0.2f, 0.2f, 0.2f);
	public static final Colour LIGHT_GREY = new Colour(0.45f, 0.45f, 0.45f);
	public static final Colour RED = new Colour(1.0f, 0.0f, 0.0f);
	public static final Colour ORANGE = new Colour(1.0f, 0.6470588235294118f, 0.0f);
	public static final Colour YELLOW = new Colour(1.0f, 1.0f, 0.0f);
	public static final Colour PINK = new Colour(1.0f, 0.0f, 1.0f);
	public static final Colour GREEN = new Colour(0.0f, 1.0f, 0.f);
	public static final Colour BLUE = new Colour(0.0f, 0.0f, 1.0f);
	public static final Colour LIGHT_BLUE = new Colour(0.0f, 1.0f, 1.0f);
	public static final Colour WHITE = new Colour(1.0f, 1.0f, 1.0f);
	
	/* Some predefined arrays of colours */
	public static final Colour[] ARRAY_RGB = new Colour[] { RED, GREEN, BLUE };
	public static final Colour[] ARRAY_GREY = new Colour[] { GREY, LIGHT_GREY };
	public static final Colour[] ARRAY_BLUE = new Colour[] { BLUE, LIGHT_BLUE };
	public static final Colour[] ARRAY_SUNSET = new Colour[] { RED, ORANGE, YELLOW };
	
	/* The values contained within this vector */
	public float r;
	public float g;
	public float b;
	public float a;
	
	/* The default constructor */
	public Colour() {
		this.r = 0;
		this.g = 0;
		this.b = 0;
		this.a = 0;
	}
	
	/* The constructor given the rgb components (a is set to 1.0 by default) */
	public Colour(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1.0f;
	}
	
	
	/* The constructor given the rgba components */
	public Colour(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/* The constructor given another colour and the a component */
	public Colour(Colour colour, float a) {
		this.r = colour.r;
		this.g = colour.g;
		this.b = colour.b;
		this.a = a;
	}
	
	/* The methods used to perform operations on this vector */
	public Colour addR(float x) { this.r += x; return this; }
	public Colour addG(float y) { this.g += y; return this; }
	public Colour addB(float z) { this.b += z; return this; }
	public Colour addA(float w) { this.a += w; return this; }
	public Colour add(float v) { this.r += v; this.g += v; this.b += v; this.b += v; return this; }
	public Colour subtractR(float x) { this.r -= x; return this; }
	public Colour subtractG(float y) { this.g -= y; return this; }
	public Colour subtractB(float z) { this.b -= z; return this; }
	public Colour subtractA(float w) { this.a -= w; return this; }
	public Colour subtract(float v) { this.r -= v; this.g -= v; this.b -= v; this.a -= v; return this; }
	public Colour divideR(float x) { this.r /= x; return this; }
	public Colour divideG(float y) { this.g /= y; return this; }
	public Colour divideB(float z) { this.b /= z; return this; }
	public Colour divideA(float w) { this.a /= w; return this; }
	public Colour divide(float v) { this.r /= v; this.g /= v; this.b /= v; this.a /= v; return this; }
	public Colour multiplyR(float x) { this.r *= x; return this; }
	public Colour multiplyG(float y) { this.g *= y; return this; }
	public Colour multiplyB(float z) { this.b *= z; return this; }
	public Colour multiplyA(float w) { this.a *= w; return this; }
	public Colour multiply(float v) { this.r *= v; this.g *= v; this.b *= v; this.a *= v; return this; }
	
	/* The methods used to perform operations between another vector and this */
	public Colour add(Colour other) {
		this.r += other.r;
		this.g += other.g;
		this.b += other.b;
		this.a += other.a;
		return this;
	}
	
	public Colour subtract(Colour other) {
		this.r -= other.r;
		this.g -= other.g;
		this.b -= other.b;
		this.a -= other.a;
		return this;
	}
	
	public Colour divide(Colour other) {
		this.r /= other.r;
		this.g /= other.g;
		this.b /= other.b;
		this.a /= other.a;
		return this;
	}
	
	public Colour multiply(Colour other) {
		this.r *= other.r;
		this.g *= other.g;
		this.b *= other.b;
		this.a *= other.a;
		return this;
	}
	
	/* The other utility methods */
	public Vector3f asVector3f() { return new Vector3f(this.r, this.g, this.b); }
	public Vector4f asVector4f() { return new Vector4f(this.r, this.g, this.b, this.a); }
	public String toString() { return "(" + this.r + "," + this.g; }
	public Colour fromString(String s) {
		String numbers[] = s.substring(1, s.length() - 2).split(",");
		return new Colour(Float.parseFloat(numbers[0]), Float.parseFloat(numbers[1]), Float.parseFloat(numbers[2]), Float.parseFloat(numbers[3]));
	}
	public Colour clone() { return new Colour(this.r, this.g, this.b, this.a); }
	
	/* The setters and getters */
	public void setR(float x) { this.r = x; }
	public void setG(float y) { this.g = y; }
	public void setB(float z) { this.b = z; }
	public void setA(float w) { this.a = w; }
	public float getR() { return this.r; }
	public float getG() { return this.g; }
	public float getB() { return this.b; }
	public float getA() { return this.a; }
	public float[] getValues() { return new float[] { this.r, this.g, this.b, this.a }; }
	
	/* The static utility methods */
	public static Colour fromVector(Vector3f vector) { return new Colour(vector.x, vector.y, vector.z); }
	public static Colour fromVector(Vector4f vector) { return new Colour(vector.x, vector.y, vector.z, vector.w); }
	public static Colour fromInt(int r, int g, int b) { return new Colour((float) r / 255f, (float) g / 255f, (float) b / 255f); }
	public static Colour fromInt(int r, int g, int b, int a) { return new Colour((float) r / 255f, (float) g / 255f, (float) b / 255f, (float) a / 255f); }

}
