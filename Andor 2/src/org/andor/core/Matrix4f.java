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

public class Matrix4f {
	
	/* The values contained within this matrix */
	private float[] values;
	
	/* The default constructor */
	public Matrix4f() {}
	
	/* The constructor with the values given */
	public Matrix4f(float[] values) { this.values = values; }
	
	/* The constructor with a 2D array */
	public Matrix4f(float[][] values) {
		this.values = new float[] {
				values[0][0], values[0][1], values[0][2], values[0][3],
				values[1][0], values[1][1], values[1][2], values[1][3],
				values[2][0], values[2][1], values[2][2], values[2][3],
				values[3][0], values[3][1], values[3][2], values[3][3]
		};
	}
	
	/* The method used to set this to the identity */
	public void setIdentity() {
		//Set the identity
		this.values = new float[]{ 1, 0, 0, 0,
								   0, 1, 0, 0,
								   0, 0, 1, 0,
								   0, 0, 0, 1 };
	}
	
	/* The method used to set this to an orthographic projection matrix */
	public void setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
		this.values = new float[] {
				(2 / (right - left)), 0, 0, - ((right + left) / (right - left)) ,
				0, 2 / (top - bottom), 0, - ((top + bottom) / (top - bottom)),
				0, 0, -2 / (zFar - zNear), -(zNear + zFar) / (zFar - zNear),
				0, 0, 0, 1 ,
		};
	}
	
	/* The method used to set this to a perspective projection matrix */
	public void setPerspective(float fov, float aspect, float zNear, float zFar) {
		//Calculate the scale
		float scale = (float) (Math.tan(fov / 2 * (Math.PI / 360)));
		//Calculate the right, left, top and bottom values
		float right = aspect * scale;
		float left = -right;
		float top = scale;
		float bottom = -top;
		//Return the result
		this.setFrustum(left, right, bottom, top, zNear, zFar);
	}
	
	/* The method used to set this to a frustum projection matrix */
	public void setFrustum(float left, float right, float bottom, float top, float zNear, float zFar) {
		this.values = new float[] {
				2 * zNear / (right - left), 0, 0, 0,
				0, 2 * zNear / (top - bottom), 0, 0,
				(right + left) / (right - left), (top + bottom) / (top - bottom), -(zFar + zNear) / (zFar - zNear), -1,
				0, 0, -2 * zFar * zNear / (zFar - zNear), 0,
		};
	}
	
	/* The methods used to initlialise this to the identity then return it */
	public Matrix4f initIdentity() {
		this.setIdentity();
		return this;
	}
	
	public Matrix4f initOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
		this.setOrtho(left, right, bottom, top, zNear, zFar);
		return this;
	}
	
	public Matrix4f initPerspective(float fov, float aspect, float zNear, float zFar) {
		this.setPerspective(fov, aspect, zNear, zFar);
		return this;
	}
	
	public Matrix4f initFrustum(float left, float right, float top, float bottom, float zNear, float zFar) {
		this.setFrustum(left, right, bottom, top, zNear, zFar);
		return this;
	}
	
	/* The method used to add another matrix to this one */
	public void add(Matrix4f other) {
		//Go through the values and add them
		for (int a = 0; a < 16; a++)
			this.values[a] += other.get(a);
	}
	
	/* The method used to subtract another matrix from this one */
	public void subtract(Matrix4f other) {
		//Go through the values and subtract them
		for (int a = 0; a < 16; a++)
			this.values[a] -= other.get(a);
	}
	
	/* The method used to multiply this matrix by another and return the result */
	public Matrix4f multiply(Matrix4f other) {
		return new Matrix4f(multiply(this.values, other.getValues()));
	}
	
	/* The method used to matrix multiply two arrays and return the result */
	private float[] multiply(float[] matrixA, float[] matrixB) {
		return new float[] {
				(matrixA[0] * matrixB[0]) + (matrixA[1] * matrixB[4]) + (matrixA[2] * matrixB[8]) + (matrixA[3] * matrixB[12]),
				(matrixA[0] * matrixB[1]) + (matrixA[1] * matrixB[5]) + (matrixA[2] * matrixB[9]) + (matrixA[3] * matrixB[13]),
				(matrixA[0] * matrixB[2]) + (matrixA[1] * matrixB[6]) + (matrixA[2] * matrixB[10]) + (matrixA[3] * matrixB[14]),
				(matrixA[0] * matrixB[3]) + (matrixA[1] * matrixB[7]) + (matrixA[2] * matrixB[11]) + (matrixA[3] * matrixB[15]),
				
				(matrixA[4] * matrixB[0]) + (matrixA[5] * matrixB[4]) + (matrixA[6] * matrixB[8]) + (matrixA[7] * matrixB[12]),
				(matrixA[4] * matrixB[1]) + (matrixA[5] * matrixB[5]) + (matrixA[6] * matrixB[9]) + (matrixA[7] * matrixB[13]),
				(matrixA[4] * matrixB[2]) + (matrixA[5] * matrixB[6]) + (matrixA[6] * matrixB[10]) + (matrixA[7] * matrixB[14]),
				(matrixA[4] * matrixB[3]) + (matrixA[5] * matrixB[7]) + (matrixA[6] * matrixB[11]) + (matrixA[7] * matrixB[15]),
				
				(matrixA[8] * matrixB[0]) + (matrixA[9] * matrixB[4]) + (matrixA[10] * matrixB[8]) + (matrixA[11] * matrixB[12]),
				(matrixA[8] * matrixB[1]) + (matrixA[9] * matrixB[5]) + (matrixA[10] * matrixB[9]) + (matrixA[11] * matrixB[13]),
				(matrixA[8] * matrixB[2]) + (matrixA[9] * matrixB[6]) + (matrixA[10] * matrixB[10]) + (matrixA[11] * matrixB[14]),
				(matrixA[8] * matrixB[3]) + (matrixA[9] * matrixB[7]) + (matrixA[10] * matrixB[11]) + (matrixA[11] * matrixB[15]),
				
				(matrixA[12] * matrixB[0]) + (matrixA[13] * matrixB[4]) + (matrixA[14] * matrixB[8]) + (matrixA[15] * matrixB[12]),
				(matrixA[12] * matrixB[1]) + (matrixA[13] * matrixB[5]) + (matrixA[14] * matrixB[9]) + (matrixA[15] * matrixB[13]),
				(matrixA[12] * matrixB[2]) + (matrixA[13] * matrixB[6]) + (matrixA[14] * matrixB[10]) + (matrixA[15] * matrixB[14]),
				(matrixA[12] * matrixB[3]) + (matrixA[13] * matrixB[7]) + (matrixA[14] * matrixB[11]) + (matrixA[15] * matrixB[15]) };
	}
	
	/* The methods used to translate this matrix */
	public void translate(Vector3f vector) {
		this.values = multiply(this.values, new float[] {
			1, 0, 0, vector.x,
			0, 1, 0, vector.y,
			0, 0, 1, vector.z,
			0, 0, 0, 1 });
	}
	
	public void translate(Vector2f vector) {
		this.values = multiply(this.values, new float[] {
			1, 0, 0, vector.x,
			0, 1, 0, vector.y,
			0, 0, 1, 0,
			0, 0, 0, 1 });
	}
	
	/* The methods used to rotate this matrix */
	public void rotate(float angle, int x, int y, int z) {
		//The transform matrix
		float transform[] = null;
		//Calculate the values needed
		float cos = (float) Math.cos(Math.toRadians(angle));
		float sin = (float) Math.sin(Math.toRadians(angle));
		//Check the x y and z values
		if (x == 1) {
			transform = new float[] {
					1, 0, 0, 0,
					0, cos, -sin, 0 ,
					0, sin, cos, 0 ,
					0, 0, 0, 1 ,
			};
		} else if (y == 1) {
			transform = new float[] {
					cos, 0, sin, 0,
					0, 1, 0, 0,
					-sin, 0, cos, 0,
					0, 0, 0, 1
			};
		} else if (z == 1) {
			transform = new float[] {
					cos, -sin, 0, 0,
					sin, cos, 0, 0,
					0, 0, 1, 0,
					0, 0, 0, 1
			};
		}
		this.values = multiply(this.values, transform);
	}
	
	public void rotate(float angle) {
		rotate(angle, 0, 0, 1);
	}
	
	public void rotate(Vector2f angles) {
		rotate(angles.getX(), 1, 0, 0);
		rotate(angles.getY(), 0, 1, 0);
	}
	
	public void rotate(Vector3f angles) {
		rotate(angles.getX(), 1, 0, 0);
		rotate(angles.getY(), 0, 1, 0);
		rotate(angles.getZ(), 0, 0, 1);
	}
	
	/* The methods used to scale this matrix */
	public void scale(Vector3f vector) {
		this.values = multiply(this.values, new float[] {
			vector.x, 0, 0, 0,
			0, vector.y, 0, 0,
			0, 0, vector.z, 0,
			0, 0, 0, 1 });
	}
	
	public void scale(Vector2f vector) {
		this.values = multiply(this.values, new float[] {
			vector.x, 0, 0, 0,
			0, vector.y, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 1 });
	}
	
	/* The method used to transpose this matrix */
	public Matrix4f transpose() {
		float newValues[] = new float[16];
		for (int a = 0; a < 4; a++) {
			int n = a * 4;
			newValues[a] = this.values[n];
			newValues[a + 4] = this.values[n + 1];
			newValues[a + 8] = this.values[n + 2];
			newValues[a + 12] = this.values[n + 3];
		}
		return new Matrix4f(newValues);
	}
	
	/* The method used to transform this matrix */
	public void transform(Vector2f translate, float rotate, Vector2f scale) {
		translate(translate);
		rotate(rotate);
		scale(scale);
	}
	
	/* The static method used to transform a matrix (Does this in the reverse order) */
	public void transformR(Vector2f translate, float rotate, Vector2f scale) {
		scale(scale);
		rotate(rotate);
		translate(translate);
	}
	
	/* The method used to transform this matrix */
	public void transform(Vector3f translate, Vector3f rotate, Vector3f scale) {
		translate(translate);
		rotate(rotate);
		scale(scale);
	}
	
	/* The method used to transform this matrix (Does this in the reverse order) */
	public void transformR(Vector3f translate, Vector3f rotate, Vector3f scale) {
		scale(scale);
		rotate(rotate);
		translate(translate);
	}
	
	/* The method used to invert this matrix */
	public Matrix4f invert() {
		//Get the values of the matrix (Transposed)
		float mat0 = this.values[0];
		float mat4 = this.values[1];
		float mat8 = this.values[2];
		float mat12 = this.values[3];
		
		float mat1 = this.values[4];
		float mat5 = this.values[5];
		float mat9 = this.values[6];
		float mat13 = this.values[7];
		
		float mat2 = this.values[8];
		float mat6 = this.values[9];
		float mat10 = this.values[10];
		float mat14 = this.values[11];
		
		float mat3 = this.values[12];
		float mat7 = this.values[13];
		float mat11 = this.values[14];
		float mat15 = this.values[15];
		
		//Calculate the pairs
		float mp0 = mat10 * mat15;
		float mp1 = mat11 * mat14;
		float mp2 = mat9 * mat15;
		float mp3 = mat11 * mat13;
		float mp4 = mat9 * mat14;
		float mp5 = mat10 * mat13;
		float mp6 = mat8 * mat15;
		float mp7 = mat11 * mat12;
		float mp8 = mat8 * mat14;
		float mp9 = mat10 * mat12;
		float mp10 = mat8 * mat13;
		float mp11 = mat9 * mat12;
		
		float scof0 = mat2 * mat7;
		float scof1 = mat3 * mat6;
		float scof2 = mat1 * mat7;
		float scof3 = mat3 * mat5;
		float scof4 = mat1 * mat6;
		float scof5 = mat2 * mat5;
		float scof6 = mat0 * mat7;
		float scof7 = mat3 * mat4;
		float scof8 = mat0 * mat6;
		float scof9 = mat2 * mat4;
		float scof10 = mat0 * mat5;
		float scof11 = mat1 * mat4;
		
		//Calculate cofactors
		float fcof0 = (mp0 * mat5 + mp3 * mat6 + mp4 * mat7) - (mp1 * mat5 + mp2 * mat6 + mp5 * mat7);
		float fcof1 = (mp1 * mat4 + mp6 * mat6 + mp9 * mat7) - (mp0 * mat4 + mp7 * mat6 + mp8 * mat7);
		float fcof2 = (mp2 * mat4 + mp7 * mat5 + mp10 * mat7) - (mp3 * mat4 + mp6 * mat5 + mp11 * mat7);
		float fcof3 = (mp5 * mat4 + mp8 * mat5 + mp11 * mat6) - (mp4 * mat4 + mp9 * mat5 + mp10 * mat6);
		float fcof4 = (mp1 * mat1 + mp2 * mat2 + mp5 * mat3) - (mp0 * mat1 + mp3 * mat2 + mp4 * mat3);
		float fcof5 = (mp0 * mat0 + mp7 * mat2 + mp8 * mat3) - (mp1 * mat0 + mp6 * mat2 + mp9 * mat3);
		float fcof6 = (mp3 * mat0 + mp6 * mat1 + mp11 * mat3) - (mp2 * mat0 + mp7 * mat1 + mp10 * mat3);
		float fcof7 = (mp4 * mat0 + mp9 * mat1 + mp10 * mat2) - (mp5 * mat0 + mp8 * mat1 + mp11 * mat2);
		
		float fcof8 = (scof0 * mat13 + scof3 * mat14 + scof4 * mat15) - (scof1 * mat13 + scof2 * mat14 + scof5 * mat15);
		float fcof9 = (scof1 * mat12 + scof6 * mat14 + scof9 * mat15) - (scof0 * mat12 + scof7 * mat14 + scof8 * mat15);
		float fcof10 = (scof2 * mat12 + scof7 * mat13 + scof10 * mat15) - (scof3 * mat12 + scof6 * mat13 + scof11 * mat15);
		float fcof11 = (scof5 * mat12 + scof8 * mat13 + scof11 * mat14) - (scof4 * mat12 + scof9 * mat13 + scof10 * mat14);
		float fcof12 = (scof2 * mat10 + scof5 * mat11 + scof1 * mat9 ) - (scof4 * mat11 + scof0 * mat9 + scof3 * mat10);
		float fcof13 = (scof8 * mat11 + scof0 * mat8 + scof7 * mat10) - (scof6 * mat10 + scof9 * mat11 + scof1 * mat8);
		float fcof14 = (scof6 * mat9 + scof11 * mat11 + scof3 * mat8) - (scof10 * mat11 + scof2 * mat8 + scof7 * mat9);
		float fcof15 = (scof10 * mat10 + scof4 * mat8 + scof9 * mat9) - (scof8 * mat9 + scof11 * mat10 + scof5 * mat8);
		
		//Calculate the determinant
		float determinant = mat0 * fcof0 + mat1 * fcof1 + mat2 * fcof2 + mat3 * fcof3;
		
		//Make sure the determinant isn't 0
		if (determinant == 0.0f)
			//Return nothing
			return new Matrix4f();
		
		//The inverted matrix's values
		float invertedValues[] = new float[16];
		
		//Calculate the matrix inverse
		float value = 1.0f / determinant;
		invertedValues[0] = fcof0 * value;
		invertedValues[1] = fcof1 * value;
		invertedValues[2] = fcof2 * value;
		invertedValues[3] = fcof3 * value;

		invertedValues[4] = fcof4 * value;
		invertedValues[5] = fcof5 * value;
		invertedValues[6] = fcof6 * value;
		invertedValues[7] = fcof7 * value;

		invertedValues[8] = fcof8 * value;
		invertedValues[9] = fcof9 * value;
		invertedValues[10] = fcof10 * value;
		invertedValues[11] = fcof11 * value;

		invertedValues[12] = fcof12 * value;
		invertedValues[13] = fcof13 * value;
		invertedValues[14] = fcof14 * value;
		invertedValues[15] = fcof15 * value;
		return new Matrix4f(invertedValues);
	}
	
	/* The setters and getters */
	public void setValues(float[] values) { this.values = values; }
	public void set(int n, float value) { this.values[n] = value; }
	public void set(int x, int y, float value) { this.values[y * 4 + x] = value; }
	public float[] getValues() { return this.values; }
	public float get(int n) { return this.values[n]; }
	public float get(int x, int y) { return this.values[y * 4 + x]; }
	
}