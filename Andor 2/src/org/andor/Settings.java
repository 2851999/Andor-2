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

package org.andor;

import org.andor.core.ScreenResolution;
import org.andor.core.resource.ResourceManager;

public class Settings {
	
	/* The window's settings */
	public static class Window {
		/* The title of the window */
		public static String Title = "Andor " + Andor.VERSION + " (" + Andor.VERSION_NAME + ")";
		/* The resolution of the window */
		public static ScreenResolution Resolution = ScreenResolution.RES_800x600;
		/* The fullscreen setting */
		public static boolean Fullscreen = false;
		/* The borderless setting (Used when Fullscreen = true) */
		public static boolean Borderless = false;
		/* The undecorated setting (Used when Fullscreen = false) */
		public static boolean Undecorated = false;
		/* The resizable setting */
		public static boolean Resizable = false;
		/* The floating setting (Always on top - Useful for debugging) */
		public static boolean Floating = false;
		/* The actual size of the window */
		public static int Width; //These will be set upon the creation of the window
		public static int Height;
	}
	
	/* The video settings */
	public static class Video {
		/* The VSync setting */
		public static boolean VSync = true;
		/* The maximum FPS */
		public static int MaxFPS = -1;
		/* The screen resolution (Used when Fullscreen = true) */
		public static ScreenResolution Resolution = ScreenResolution.RES_NATIVE;
		/* The maximum number of samples to do using anisotropic filtering */
		public static int MaxAnisotropicSamples = 16;
		/* The MSAA samples 0 if not used */
		public static int Samples = 0;
	}
	
	/* The input settings */
	public static class Input {
		/* The boolean that states whether events with the mouse should be able to repeat */
		public static boolean MouseEventsRepeat = false;
		/* The boolean that states whether events the the keyboard should be able to repeat */
		public static boolean KeyboardEventsRepeat = true;
	}
	
	/* The audio settings */
	public static class Audio {
		
	}
	
	/* The debugging settings */
	public static class Debugging {
		/* The boolean that states whether debugging information should be rendered */
		public static boolean ShowInformation = true;
	}
	
	/* The resource settings */
	public static class Resource {
		/* The path to the engine's resources */
		public static String Path = "/resources";
		/* The paths to other various resources */
		public static String ShadersPath = "/shaders";
		public static String TexturesPath = "/textures";
		/* The boolean that states whether the resources are external */
		public static boolean External = false;
		/* The engine's resource manager */
		public static ResourceManager Manager;
	}
	
}