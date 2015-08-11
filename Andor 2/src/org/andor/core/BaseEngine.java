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

import java.util.ArrayList;
import java.util.List;

import org.andor.Andor;
import org.andor.Settings;
import org.andor.core.input.InputManager;
import org.andor.core.render.ForwardRenderer;
import org.andor.core.render.Renderer;
import org.andor.core.resource.ResourceManager;
import org.andor.core.resource.audio.AudioManager;
import org.andor.core.thread.PhysicsThread;
import org.andor.utils.FPSCalculator;
import org.andor.utils.FPSLimiter;
import org.andor.utils.FontUtils;
import org.andor.utils.OpenGLUtils;
import org.andor.utils.SystemInformation;

public abstract class BaseEngine {
	
	/* The boolean that allow a stop to be requested */
	private boolean stopRequested;
	
	/* The FPS calculator */
	private FPSCalculator fpsCalculator;
	
	/* The FPS limiter */
	private FPSLimiter fpsLimiter;
	
	/* The camera used for rendering the information */
	private Camera2D camera;
	
	/* The game loop interfaces */
	public static List<GameLoopInterface> interfaces = new ArrayList<GameLoopInterface>();
	
	/* The physics thread */
	private PhysicsThread physicsThread;
	
	/* The default constructor */
	public BaseEngine() {
		//Start the engine
		initialiseEngine();
		startEngine();
	}
	
	/* The method called to initialise the game engine */
	private void initialiseEngine() {
		//No stop requested by default
		this.stopRequested = false;
		//Setup the fps calculator and limiter
		this.fpsCalculator = new FPSCalculator();
		//Notify the game
		initialise();
		this.fpsLimiter = new FPSLimiter(Settings.Video.MaxFPS);
		//Create the window
		Window.create();
		this.camera = Camera2D.createOrtho(-1, 1);
		//Setup the resource manager
		Settings.Resource.Manager = new ResourceManager(Settings.Resource.Path, Settings.Resource.External);
		Settings.Resource.Manager.setShadersPath(Settings.Resource.ShadersPath);
		Settings.Resource.Manager.setTexturesPath(Settings.Resource.TexturesPath);
		//Setup the renderer
		ForwardRenderer.initialise();
		//Setup the audio system
		AudioManager.create();
		//Load the resources
		Andor.Resources.Textures.Blank = Settings.Resource.Manager.loadTexture("blank.png");
		Andor.Resources.Fonts.Default = FontUtils.createBitmapFont("Segoe UI", 16);
		//Setup the physics thread
		this.physicsThread = new PhysicsThread();
	}
	
	/* The method called to start the game engine */
	private void startEngine() {
		//Notify the game
		started();
		this.physicsThread.start();
		//The main logic loop
		while (! Window.shouldClose() && ! this.stopRequested) {
			//Update the FPS calculator
			this.fpsCalculator.update();
			//Update and render the game
			update();
			this.interfaceUpdate();
			render();
			this.interfaceRender();
			
			//Check to see whether the debugging information should be rendered
			if (Settings.Debugging.ShowInformation)
				this.renderInformation();
			
			//Update the window
			Window.updateDisplay();
			//Check for changes in the input system
			InputManager.checkInput();
			
			//Attempt to cap the framerate (Not 100% accurate)
			this.fpsLimiter.update((int) getDelta());
		}
		//Stop the game engine
		stopEngine();
	}
	
	/* The method called to stop the game engine */
	private void stopEngine() {
		//Notify the game
		stopping();
		//Stop the physics thread
		this.physicsThread.stop();
		//Delete all of the resources
		ResourceManager.deleteAll();
		//Destroy the audio system
		AudioManager.destroy();
		//Destroy the window
		Window.destroy();
	}
	
	/* The method used to render some information */
	public void renderInformation() {
		//Setup
		Renderer.addCamera(camera);
		OpenGLUtils.setupRemoveAlpha();
		OpenGLUtils.enableTexture2D();
		//Render the FPS
		Andor.Resources.Fonts.Default.render("DEBUGGING", 0, 0);
		Andor.Resources.Fonts.Default.render("Andor Version: " + Andor.VERSION + " (" + Andor.VERSION_NAME + ")", 0, 24);
		Andor.Resources.Fonts.Default.render("Andor Build: " + Andor.BUILD_TYPE, 0, 38);
		Andor.Resources.Fonts.Default.render("Current FPS: " + this.getFPS(), 0, 52);
		Andor.Resources.Fonts.Default.render("Current Delta: " + this.getDelta(), 0, 66);
		Andor.Resources.Fonts.Default.render("Memory Usage: " + (((SystemInformation.getTotalMemory() - SystemInformation.getFreeMemory()) / 1024) / 1024) + "/" + ((SystemInformation.getMaxMemory() / 1024) / 1024), 0, 80);
		Andor.Resources.Fonts.Default.render("Window Size: " + Settings.Window.Width + "x" + Settings.Window.Height, 0, 94);
		Andor.Resources.Fonts.Default.render("VSync: " + Settings.Video.VSync, 0, 108);
		Andor.Resources.Fonts.Default.render("Current Physics FPS: " + this.physicsThread.getFPS(), 0, 122);
		Andor.Resources.Fonts.Default.render("Current Physics Delta: " + this.physicsThread.getDelta(), 0, 136);
		OpenGLUtils.disableTexture2D();
		Renderer.removeCamera();
	}
	
	/* The method used to request the engine to stop */
	public void requestStop() { this.stopRequested = true; }
	
	/* The method's used to retrieve some performance data */
	public FPSCalculator getFPSCalculator() { return this.fpsCalculator; }
	public long getDelta() { return this.fpsCalculator.getDelta(); }
	public float getFPS() { return this.fpsCalculator.getFPS(); }
	
	/* The method called to initialise the game */
	public abstract void initialise();
	
	/* The method called when the engine has started */
	public abstract void started();
	
	/* The method called to update the game */
	public abstract void update();
	
	/* The method called to render the game */
	public abstract void render();
	
	/* The method called when the engine is stopping */
	public abstract void stopping();
	
	/* The static method used to add an interface */
	public static void add(GameLoopInterface i) {
		interfaces.add(i);
	}
	
	/* The method used to update all of the game loop interfaces */
	public void interfaceUpdate() {
		for (int a = 0; a < interfaces.size(); a++)
			interfaces.get(a).update();
	}
	
	public void interfaceRender() {
		for (int a = 0; a < interfaces.size(); a++)
			interfaces.get(a).render();
	}
	
}