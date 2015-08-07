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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.ByteBuffer;

import org.andor.Settings;
import org.andor.core.input.InputManager;
import org.andor.core.input.Mouse;
import org.andor.utils.BufferUtils;
import org.andor.utils.OpenGLUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Window {
	
	/* The window instance */
	private static long instance;
	
	/* Callback references */
	private static GLFWKeyCallback keyCallback;
	private static GLFWCharCallback charCallback;
	private static GLFWMouseButtonCallback mouseButtonCallback;
	private static GLFWCursorPosCallback mousePosCallback;
	private static GLFWScrollCallback scrollCallback;
	private static GLFWWindowSizeCallback windowSizeCallback;
	private static GLFWCursorEnterCallback cursorEnterCallback;
	
	/* The method used to create this window */
	public static void create() {
		//Initialise GLFW
		GLFW.glfwInit();
		GLFW.glfwDefaultWindowHints();
		
		//Setup the hints
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, OpenGLUtils.getValue(Settings.Window.Resizable));
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, OpenGLUtils.getValue(! Settings.Window.Undecorated));
		GLFW.glfwWindowHint(GLFW.GLFW_FLOATING, OpenGLUtils.getValue(Settings.Window.Floating));
		
		if (Settings.Video.Samples != 0)
			GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, Settings.Video.Samples);
		
		setVSync(Settings.Video.VSync);
		
		//The monitor, assign if using fullscreen
		long monitor = 0;
		if (Settings.Window.Fullscreen) {
			monitor = GLFW.glfwGetPrimaryMonitor();
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, GLFW.GLFW_DONT_CARE);
			//Assign the settings
			Settings.Window.Resolution = Settings.Video.Resolution;
		}
		
		//Create the instance
		instance = GLFW.glfwCreateWindow(Settings.Window.Resolution.getWidth(), Settings.Window.Resolution.getHeight(), Settings.Window.Title, monitor, 0L);
		
		//Move to centre
		centre();
		
		//Make the OpenGL context current
		GLFW.glfwMakeContextCurrent(instance);
		//Create the OpenGL context
		GLContext.createFromCurrent();
		//Enable VSync if necessary
		setVSync(Settings.Video.VSync);
		
		//Create input
		GLFW.glfwSetKeyCallback(instance, keyCallback = new GLFWKeyCallback() {
			public void invoke(long window, int key, int scancode, int action, int mods) {
				//Check the action
				if (action == GLFW.GLFW_PRESS || (Settings.Input.KeyboardEventsRepeat && action == GLFW.GLFW_REPEAT)) {
					//Call the event
					InputManager.callOnKeyPressed(key);
				} else if (action == GLFW.GLFW_RELEASE) {
					//Call the event
					InputManager.callOnKeyReleased(key);
					InputManager.callOnKeyTyped(key);
				}
			}
		});
		
		GLFW.glfwSetCharCallback(instance, charCallback = new GLFWCharCallback() {
			public void invoke(long window, int codepoint) {
				//Call the event
				InputManager.callOnChar(codepoint, (char) codepoint);
			}
		});
		
		GLFW.glfwSetMouseButtonCallback(instance, mouseButtonCallback = new GLFWMouseButtonCallback() {
			public void invoke(long window, int button, int action, int mods) {
				//Check the current event
				if (action == GLFW.GLFW_PRESS) {
					//Check the buttons
					if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
						Mouse.leftButtonDown = true;
					if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
						Mouse.middleButtonDown = true;
					if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
						Mouse.rightButtonDown = true;
					//Call an event
					InputManager.callOnMousePressed(button);
				} else if (action == GLFW.GLFW_RELEASE) {
					//Check the buttons
					if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
						Mouse.leftButtonDown = false;
					if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
						Mouse.middleButtonDown = false;
					if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
						Mouse.rightButtonDown = false;
					//Call an event
					InputManager.callOnMouseReleased(button);
					InputManager.callOnMouseClicked(button);
				}
			}
		});
		
		GLFW.glfwSetCursorPosCallback(instance, mousePosCallback = new GLFWCursorPosCallback() {
			public void invoke(long window, double x, double y) {
				//Get the change in x and y positions
				double xOffset = x - Mouse.lastX;
				double yOffset = y - Mouse.lastY;
				//Check to see whether there has been a previous event
				if (Mouse.lastX == -1) {
					//Ignore the previously set values
					xOffset = 0;
					yOffset = 0;
				}
				//Set the last and new position values
				Mouse.lastX = x;
				Mouse.lastY = y;
				
				//Call a mouse moved event
				InputManager.callOnMouseMoved(x, y, xOffset, yOffset);
				
				//Check to see whether the mouse button is pressed
				if (Mouse.leftButtonDown)
					//Call a mouse dragged event
					InputManager.callOnMouseDragged(x, y, xOffset, yOffset);
			}
		});
		
		GLFW.glfwSetCursorEnterCallback(instance, cursorEnterCallback = new GLFWCursorEnterCallback() {
			public void invoke(long instance, int entered) {
				if (entered == GL11.GL_TRUE)
					InputManager.callOnMouseEnter();
				else
					InputManager.callOnMouseLeave();
			}
		});
		
		GLFW.glfwSetScrollCallback(instance, scrollCallback = new GLFWScrollCallback() {
			public void invoke(long window, double xOffset, double yOffset) {
				//Call an event
				InputManager.callOnScroll(xOffset, yOffset);
			}
		});
		
		GLFW.glfwSetWindowSizeCallback(instance, windowSizeCallback = new GLFWWindowSizeCallback() {
			public void invoke(long instance, int width, int height) {
				//Create the old window size
				Vector2f oldSize = new Vector2f(Settings.Window.Width, Settings.Window.Height);
				//Assign the size
				Settings.Window.Width = width;
				Settings.Window.Height = height;
				//Create the new window size
				Vector2f newSize = new Vector2f(Settings.Window.Width, Settings.Window.Height);
				//Call an event
				InputManager.callOnWindowSizeChanged(oldSize, newSize);
				
				//Update OpenGL's viewport
				OpenGLUtils.setupViewport(0, 0, Settings.Window.Width, Settings.Window.Height);
			}
		});
		
		//The two buffers in which to hold the width and height
		ByteBuffer widthBuffer = BufferUtils.createByteBuffer(4);
		ByteBuffer heightBuffer = BufferUtils.createByteBuffer(4);
		
		//Get the window's size
		GLFW.glfwGetWindowSize(instance, widthBuffer, heightBuffer);
		
		//Assign the window's width and height variables
		Settings.Window.Width = widthBuffer.getInt();
		Settings.Window.Height = heightBuffer.getInt();
		
		//Update OpenGL's viewport
		OpenGLUtils.setupViewport(0, 0, Settings.Window.Width, Settings.Window.Height);
	}
	
	/* The static method used to update the display */
	public static void updateDisplay() {
		//Swap the buffers
		GLFW.glfwSwapBuffers(instance);
		//Poll for window events
		GLFW.glfwPollEvents();
	}
	
	/* The static method used to set the undecorated property */
	public static void setUndecorated(boolean undecorated) {
		//Set the value
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, OpenGLUtils.getValue(! undecorated));
	}
	
	/* The static method used to set the vsync property */
	public static void setVSync(boolean vsync) {
		//Check the value
		if (vsync)
			GLFW.glfwSwapInterval(1);
		else
			GLFW.glfwSwapInterval(0);
	}
	
	/* The static method to centre the window */
	public static void centre() {
		//Get the screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Set the position of the window
		setPosition((screenSize.width / 2) - (Settings.Window.Resolution.getWidth() / 2), (screenSize.height / 2) - (Settings.Window.Resolution.getHeight() / 2));
	}
	
	/* The static method used to return the centre x value */
	public static float getCentreX() {
		//Get the screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Set the position of the window
		return (screenSize.width / 2) - (Settings.Window.Resolution.getWidth() / 2);
	}
	
	/* The static method used to return the centre x value */
	public static float getCentreY() {
		//Get the screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Set the position of the window
		return (screenSize.height / 2) - (Settings.Window.Resolution.getHeight() / 2);
	}
	
	/* The static method used to set the position of this window */
	public static void setPosition(float x, float y) {
		if (! Settings.Window.Fullscreen)
			//Set the position of the window
			GLFW.glfwSetWindowPos(instance, (int) x, (int) y);
	}
	
	/* The static method used to show this window */
	public static void show() {
		GLFW.glfwShowWindow(instance);
	}
	
	/* The static method used to hide this window */
	public static void hide() {
		GLFW.glfwHideWindow(instance);
	}
	
	/* The static method used to determine whether this window should close */
	public static boolean shouldClose() {
		//Return whether close is requested
		return OpenGLUtils.getBoolean(GLFW.glfwWindowShouldClose(instance));
	}
	
	/* The static method used to close this window */
	public static void destroy() {
		//Release the callbacks
		keyCallback.release();
		charCallback.release();
		mouseButtonCallback.release();
		mousePosCallback.release();
		scrollCallback.release();
		windowSizeCallback.release();
		cursorEnterCallback.release();
		//Destroy the window
		GLFW.glfwDestroyWindow(instance);
	}
	
	/* The getters */
	public static long getInstance() { return instance; }
	
}