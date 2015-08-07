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

package org.andor.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class BufferUtils {
	
	/* The static methods used to create an empty buffer */
	public static ByteBuffer createByteBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
	}
	
	public static ShortBuffer createShortBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * Short.SIZE).order(ByteOrder.nativeOrder()).asShortBuffer();
	}
	
	public static IntBuffer createIntegerBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * Integer.SIZE).order(ByteOrder.nativeOrder()).asIntBuffer();
	}
	
	public static FloatBuffer createFloatBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * Float.SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	
	public static DoubleBuffer createDoubleBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * Double.SIZE).order(ByteOrder.nativeOrder()).asDoubleBuffer();
	}
	
	/* The static method used to create a byte buffer given the data */
	public static ByteBuffer createBuffer(byte[] data) {
		//Create the buffer
		ByteBuffer buffer = ByteBuffer.allocateDirect(data.length).order(ByteOrder.nativeOrder());
		//Give the data to the buffer
		buffer.put(data);
		//Return the buffer
		return buffer;
	}
	
	/* The static method used to create a short buffer given the data */
	public static ShortBuffer createBuffer(short[] data) {
		//Create the buffer
		ShortBuffer buffer = ByteBuffer.allocateDirect(data.length * Short.SIZE).order(ByteOrder.nativeOrder()).asShortBuffer();
		//Give the data to the buffer
		buffer.put(data);
		//Return the buffer
		return buffer;
	}
	
	/* The static method used to create an integer buffer given the data */
	public static IntBuffer createBuffer(int[] data) {
		//Create the buffer
		IntBuffer buffer = ByteBuffer.allocateDirect(data.length * Integer.SIZE).order(ByteOrder.nativeOrder()).asIntBuffer();
		//Give the data to the buffer
		buffer.put(data);
		//Return the buffer
		return buffer;
	}
	
	/* The static method used to create a float buffer given the data */
	public static FloatBuffer createBuffer(float[] data) {
		//Create the buffer
		FloatBuffer buffer = ByteBuffer.allocateDirect(data.length * Float.SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
		//Give the data to the buffer
		buffer.put(data);
		//Return the buffer
		return buffer;
	}
	
	/* The static method used to create a double buffer given the data */
	public static DoubleBuffer createBuffer(double[] data) {
		//Create the buffer
		DoubleBuffer buffer = ByteBuffer.allocateDirect(data.length * Double.SIZE).order(ByteOrder.nativeOrder()).asDoubleBuffer();
		//Give the data to the buffer
		buffer.put(data);
		//Return the buffer
		return buffer;
	}
	
	/* The static method used to create a flipped integer buffer */
	public static ByteBuffer createFlippedBuffer(byte[] data) {
		//Get the buffer
		ByteBuffer buffer = createBuffer(data);
		//Flip the buffer
		buffer.flip();
		//Return the buffer
		return buffer;
	}
	
	/* The static method used to create a flipped short buffer */
	public static ShortBuffer createFlippedBuffer(short[] data) {
		//Get the buffer
		ShortBuffer buffer = createBuffer(data);
		//Flip the buffer
		buffer.flip();
		//Return the buffer
		return buffer;
	}
	
	/* The static method used to create a flipped integer buffer */
	public static IntBuffer createFlippedBuffer(int[] data) {
		//Get the buffer
		IntBuffer buffer = createBuffer(data);
		//Flip the buffer
		buffer.flip();
		//Return the buffer
		return buffer;
	}
	
	/* The method used to create a flipped float buffer */
	public static FloatBuffer createFlippedBuffer(float[] data) {
		//Get the buffer
		FloatBuffer buffer = createBuffer(data);
		//Flip the buffer
		buffer.flip();
		//Return the buffer
		return buffer;
	}
	
	/* The static method used to create a flipped double buffer */
	public static DoubleBuffer createFlippedBuffer(double[] data) {
		//Get the buffer
		DoubleBuffer buffer = createBuffer(data);
		//Flip the buffer
		buffer.flip();
		//Return the buffer
		return buffer;
	}
	
}