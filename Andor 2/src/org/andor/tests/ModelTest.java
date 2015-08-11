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
import org.andor.core.Camera3D;
import org.andor.core.Matrix4f;
import org.andor.core.ScreenResolution;
import org.andor.core.SkyBox;
import org.andor.core.Vector3f;
import org.andor.core.input.Keyboard;
import org.andor.core.input.Mouse;
import org.andor.core.model.Model;
import org.andor.core.render.Renderer;
import org.andor.core.resource.texture.TextureParameters;
import org.andor.processor.XMLDocument;
import org.andor.processor.collada.Collada;
import org.andor.processor.collada.ColladaParser;
import org.andor.utils.OpenGLUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class ModelTest extends BaseGame {
	
	public volatile Camera3D camera;
	public volatile Model model;
	public boolean wireframe;
	
	/* The method called to initialise the game */
	public void initialise() {
		this.addListener();
		
		TextureParameters.DEFAULT.filter = GL11.GL_LINEAR_MIPMAP_LINEAR;
		Settings.Window.Resolution = ScreenResolution.RES_720P;
		//Settings.Video.Samples = 16;
		
		wireframe = false;
	}
	
	/* The method called when the engine has started */
	public void started() {
		camera = new Camera3D(new Matrix4f().initPerspective(80, (float) Settings.Window.Width / (float) Settings.Window.Height, 1f, 1000f));
		camera.setFlying(true);
		Renderer.addCamera(this.camera);
		
		String path = "H:/Andor/";
		this.camera.setSkyBox(new SkyBox(path + "skybox6.jpg", true, 100));
		
		//Collada collada = ColladaParser.parse(new XMLDocument("H:/Storage/Users/Joel/Desktop/box.dae", true));
		Collada collada = ColladaParser.parse(new XMLDocument("H:/Storage/Users/Joel/Desktop/Sponza/sponza.dae", true));
		model = collada.convert().createModel();
		model.scale.multiply(0.1f);
		model.update();
		
		Mouse.lock();
	}
	
	/* The method called to update the game */
	public void update() {
		if (Keyboard.isPressed(GLFW.GLFW_KEY_W))
			camera.moveForward(0.01f * getDelta());
		if (Keyboard.isPressed(GLFW.GLFW_KEY_S))
			camera.moveBackward(0.01f * getDelta());
		if (Keyboard.isPressed(GLFW.GLFW_KEY_A))
			camera.moveLeft(0.01f * getDelta());
		if (Keyboard.isPressed(GLFW.GLFW_KEY_D))
			camera.moveRight(0.01f * getDelta());
	}
	
	/* The method called to render the game */
	public void render() {
		OpenGLUtils.clearBuffers();
		OpenGLUtils.enableDepthTest();
		OpenGLUtils.enableTexture2D();
		OpenGLUtils.setupRemoveAlpha();
		
		this.camera.useView();
		this.model.render();
	}
	
	public void onKeyPressed(int code) {
		if (code == GLFW.GLFW_KEY_ESCAPE)
			this.requestStop();
		if (code == GLFW.GLFW_KEY_M) {
			wireframe = !wireframe;
			if (wireframe)
				OpenGLUtils.enableWireframeMode();
			else
				OpenGLUtils.disableWireframeMode();
		}
	}
	public void onMouseMoved(double x, double y, double xOffset, double yOffset) {
		//Make sure the mouse is locked
		if (Mouse.isLocked())
			//Change the camera's rotation
			camera.rotation.add(new Vector3f((float) yOffset * 0.5f, (float) xOffset * 0.5f, 0));
	}
	
	public static void main(String[] args) {
		new ModelTest();
	}
	
}