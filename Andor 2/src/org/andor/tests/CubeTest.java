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
import org.andor.core.Colour;
import org.andor.core.Matrix4f;
import org.andor.core.Object3DBuilder;
import org.andor.core.RenderableObject3D;
import org.andor.core.ScreenResolution;
import org.andor.core.SkyBox;
import org.andor.core.Vector3f;
import org.andor.core.input.Keyboard;
import org.andor.core.input.Mouse;
import org.andor.core.render.Renderer;
import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureParameters;
import org.andor.core.resource.texture.TextureSet;
import org.andor.utils.MathUtils;
import org.andor.utils.OpenGLUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class CubeTest extends BaseGame {
	
	public volatile Camera3D camera;
	public volatile RenderableObject3D cube;
	public volatile RenderableObject3D bigCube;
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
		
		TextureSet textures = new TextureSet();
		Texture grassSide = textures.load(path + "Grass_Side.png", true);
		Texture grass = textures.load(path + "Grass.png", true);
		Texture dirt = textures.load(path + "Dirt.png", true);
		Texture texture = textures.join();
		//Create the cube
		cube = new RenderableObject3D(Object3DBuilder.createCube(1, 1, 1, new Texture[] {
				grassSide, grassSide, grassSide, grassSide,
				grass, dirt
		}, Colour.WHITE));
		cube.getRenderData().getMaterial().setDiffuseTexture(texture);
		bigCube = new RenderableObject3D(Object3DBuilder.createCube(10, 10, 10, new Colour[] { new Colour(Colour.RED, 0.3f), new Colour(Colour.GREEN, 0.3f), new Colour(Colour.BLUE, 0.3f) }));
		
		Mouse.lock();
	}
	
	/* The method called to update the game */
	public void update() {
		cube.rotation.add(0.1f * getDelta());
		cube.update();
		bigCube.rotation.add(-0.1f * getDelta());
		bigCube.update();
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
		this.cube.render();
		this.bigCube.render();
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
		if (Mouse.isLocked()) {
			camera.rotation.add(new Vector3f((float) yOffset * 0.5f, (float) xOffset * 0.5f, 0));
			this.camera.rotation.x = MathUtils.clamp(this.camera.rotation.x, -90, 90);
		}
	}
	
	public static void main(String[] args) {
		new CubeTest();
	}
	
}