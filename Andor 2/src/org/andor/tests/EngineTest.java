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

package org.andor.tests;

import org.andor.Settings;
import org.andor.core.BaseGame;
import org.andor.core.Camera2D;
import org.andor.core.Colour;
import org.andor.core.Font;
import org.andor.core.Matrix4f;
import org.andor.core.Object2DBuilder;
import org.andor.core.RenderableObject2D;
import org.andor.core.ScreenResolution;
import org.andor.core.input.Controller;
import org.andor.core.input.ControllerAxis;
import org.andor.core.input.ControllerButton;
import org.andor.core.input.InputManager;
import org.andor.core.input.bindings.ControlBinding;
import org.andor.core.input.bindings.ControlBindings;
import org.andor.core.input.bindings.ControlListenerInterface;
import org.andor.core.render.ForwardRenderer;
import org.andor.core.render.RenderData;
import org.andor.core.resource.audio.AudioListener;
import org.andor.core.resource.audio.AudioLoader;
import org.andor.core.resource.audio.AudioSource;
import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureParameters;
import org.andor.gui.GUIButton;
import org.andor.utils.ControllerUtils;
import org.andor.utils.FontUtils;
import org.andor.utils.Logger;
import org.andor.utils.OpenGLUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class EngineTest extends BaseGame implements ControlListenerInterface {
	
	public ControlBindings bindings;
	public RenderableObject2D object;
	public Camera2D camera;
	public Font font;
	public GUIButton button;
	public AudioListener listener;
	public AudioSource source;
	
	/* The method called to initialise the game */
	public void initialise() {
		this.addListener();
		this.bindings = new ControlBindings();
		
		TextureParameters.DEFAULT.filter = GL11.GL_NEAREST_MIPMAP_LINEAR;
		
		Settings.Window.Resolution = ScreenResolution.RES_720P;
	}
	
	/* The method called when the engine has started */
	public void started() {
		int[] controllers = ControllerUtils.listAvailableControllerIndices(10);
		Controller controller = new Controller(controllers[controllers.length - 1]);
		InputManager.addController(controller);
		ControlBinding binding = new ControlBinding("Test", 0);
		binding.setControllerAxis(controller.getAxes()[0]);
		bindings.add(binding);
		bindings.add(this);
		
		Texture wood = Settings.Resource.Manager.loadTexture("wood.png");
		
		RenderData data = Object2DBuilder.createQuad(256, 256, wood, Colour.ARRAY_RGB);
//		data.setup(new Vertex2D[] {
//				new Vertex2D(new Vector2f(10, 10), Colour.RED, new Vector2f(0, 0)),
//				new Vertex2D(new Vector2f(200, 200), Colour.GREEN, new Vector2f(1, 0)),
//				new Vertex2D(new Vector2f(100, 300), Colour.BLUE, new Vector2f(0, 1))
//		}, new short[] { 0, 1, 2 });
		data.getMaterial().setDiffuseTexture(wood);
		
		object = new RenderableObject2D(data);
		object.setPosition(Settings.Window.Width / 2 - 64, Settings.Window.Height / 2 - 128);
		object.setSize(256, 256);
		object.setScale(0.5f, 0.5f);
		
		object.getRenderData().updateColour(Colour.ARRAY_BLUE);
		
		camera = new Camera2D(new Matrix4f().initOrtho(0, Settings.Window.Width, Settings.Window.Height, 0, -1, 1));
		camera.update();
		ForwardRenderer.add(this.camera);
		
		font = FontUtils.createBitmapFont("Segoe UI", Colour.ARRAY_SUNSET, 40);
		font.bitmapFont.update();
		
		this.button = new GUIButton("Click Me", new Colour[] { new Colour(Colour.LIGHT_BLUE, 0.1f), new Colour(Colour.LIGHT_BLUE, 0.3f) }, 200, 40);
		this.button.setPosition(Settings.Window.Width / 2 - 100, Settings.Window.Height - 100);
		
		this.listener = new AudioListener();
		this.source = new AudioSource(AudioLoader.load("H:/Storage/Users/Joel/Desktop/test.ogg", true));
		this.listener.update();
		this.source.update();
		this.source.play();
	}
	
	/* The method called to update the game */
	public void update() {
		object.setRotation(object.getRotation() + 3.48f * getDelta());
		object.update();
		this.button.update();
	}
	
	/* The method called to render the game */
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		object.render();
		//Logger.debug("" + getFPS() + " " + (((SystemInformation.getTotalMemory() - SystemInformation.getFreeMemory()) / 1024) / 1024) + "/" + ((SystemInformation.getMaxMemory() / 1024) / 1024));
		OpenGLUtils.enableTexture2D();
		OpenGLUtils.setupRemoveAlpha();
		font.render("HELLO WORLD", Settings.Window.Width / 2 - font.getWidth("HELLO WORLD") / 2, 10);
		this.button.render();
	}
	
	/* The method called when the engine is stopping */
	public void stopping() {
		
	}
	
	/* The controller events */
	public void onControllerButtonPressed(ControllerButton button) {
		//Logger.debug("Controller " + button.getController().getIndex() + "     Button " + button.getIndex() + " pressed");
	}
	public void onControllerButtonReleased(ControllerButton button) {
		//Logger.debug("Controller " + button.getController().getIndex() + "     Button " + button.getIndex() + " released");
	}
	public void onControllerAxisChange(ControllerAxis axis) {
		//Logger.debug("Controller " + axis.getController().getIndex() + "     Axis " + axis.getIndex() + " changed to " + axis.getValue());
	}
	
	/* The event called when a binding changes its state */
	public void onBindingChange(ControlBinding binding) {
		Logger.debug("" + binding.isPressed() + "  " + binding.getValue());
		if (binding.getId() == 0) {
			Logger.debug("" + binding.isPressed() + "  " + binding.getValue());
		}
	}
	
	public void onKeyPressed(int code) {
		if (code == GLFW.GLFW_KEY_ESCAPE)
			this.requestStop();
	}
	
	public static void main(String[] args) {
		new EngineTest();
	}
	
}