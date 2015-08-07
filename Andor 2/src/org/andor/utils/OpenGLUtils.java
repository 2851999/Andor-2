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

import org.lwjgl.opengl.GL11;

public class OpenGLUtils {
	
	/* The other static utility methods */
	public static void clearColourBuffer() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static void clearDepthBuffer() {
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void clearBuffers() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void enableDepthTest() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public static void disableDepthTest() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	public static void enableTexture2D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static void disableTexture2D() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public static void enableWireframeMode() {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}
	
	public static void disableWireframeMode() {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}
	
	public static void setupRemoveAlpha() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA , GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/* The static method used to setup the viewport */
	public static void setupViewport(int x, int y, int width, int height) {
		GL11.glScissor(x, y, width, height);
		GL11.glViewport(x, y, width, height);
	}
	
	/* The static method to get the OpenGL version */
	public static String getVersion() {
		return GL11.glGetString(GL11.GL_VERSION);
	}
	
	/* The static method used to take a boolean and turn it into an OpenGL value */
	public static int getValue(boolean value) {
		//Check the value
		if (value)
			return GL11.GL_TRUE;
		else
			return GL11.GL_FALSE;
	}
	
	/* The static method used to take an OpenGL boolean and turn it into a boolean */
	public static boolean getBoolean(int value) {
		//Check the value
		if (value == GL11.GL_TRUE)
			return true;
		else
			return false;
	}
	
}
