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
import org.andor.core.Object3DBuilder;
import org.andor.core.RenderableObject3D;
import org.andor.core.ScreenResolution;
import org.andor.core.SkyBox;
import org.andor.core.Vector3f;
import org.andor.core.input.Keyboard;
import org.andor.core.input.Mouse;
import org.andor.core.render.Renderer;
import org.andor.core.resource.ResourceManager;
import org.andor.core.resource.audio.AudioSource;
import org.andor.core.resource.audio.SoundSystem;
import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureParameters;
import org.andor.utils.MathUtils;
import org.andor.utils.OpenGLUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class SoundTest extends BaseGame {
	
	public Camera3D camera;
	public RenderableObject3D cube1;
	public RenderableObject3D cube2;
	public RenderableObject3D cube3;
	public RenderableObject3D cube4;
	public SoundSystem sound;
	public AudioSource source1;
	public AudioSource source2;
	public AudioSource source3;
	public AudioSource source4;
	
	public boolean wireframe;
	
	public void initialise() {
		wireframe = false;
		
		Settings.Window.Resolution = ScreenResolution.RES_720P;
		Settings.Audio.SoundEffectVolume = 100;
		Settings.Audio.MusicVolume = 50;
		TextureParameters.DEFAULT_FILTER = GL11.GL_LINEAR_MIPMAP_LINEAR;
		Settings.Video.Samples = 16;
	}
	
	public void started() {
		this.addListener();
		String path = "H:/Andor2/Tests/Sound/";
		camera = Camera3D.createPerspective(90, 1, 1000);
		camera.setFlying(true);
		Renderer.addCamera(this.camera);
		
		ResourceManager manager = new ResourceManager(path, true);
		
		this.camera.setSkyBox(new SkyBox(path + "skybox3.png", true, 100));
		
		this.cube1 = createCube(manager.loadTexture("wood.png"));
		this.cube1.position.y = -1;
		this.cube1.update();
		
		this.cube2 = createCube(manager.loadTexture("wood2.jpg"));
		this.cube2.position.x = 8;
		this.cube2.position.y = -1;
		this.cube2.update();
		
		this.cube3 = createCube(manager.loadTexture("grass.png"));
		this.cube3.position.x = 0;
		this.cube3.position.y = -1;
		this.cube3.position.z = 8;
		this.cube3.update();
		
		this.sound = new SoundSystem();
		this.sound.createListener(this.camera);
		this.sound.playAsSoundEffect("Source1", manager.loadAudio("test.ogg"), new Vector3f(0, -1, 0));
		this.sound.playAsSoundEffect("Source2", manager.loadAudio("test2.ogg"), new Vector3f(8, -1, 0));
		this.sound.playAsSoundEffect("Source3", manager.loadAudio("test3.ogg"), new Vector3f(0, -1, 8));
		this.sound.playAsMusic("Source4", manager.loadAudio("test4.ogg"));
		
		Mouse.lock();
	}
	
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
	
	public void render() {
		OpenGLUtils.clearBuffers();
		OpenGLUtils.enableDepthTest();
		OpenGLUtils.enableTexture2D();
		OpenGLUtils.setupRemoveAlpha();
		
		this.sound.update();
		this.camera.useView();
		this.cube1.render();
		this.cube2.render();
		this.cube3.render();
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
	
	public RenderableObject3D createCube(Texture texture) {
		RenderableObject3D object = new RenderableObject3D(Object3DBuilder.createCube(1, 1, 1, texture, Colour.WHITE));
		object.getRenderData().getMaterial().setDiffuseTexture(texture);
		return object;
	}
	
	public static void main(String[] args) {
		new SoundTest();
	}
	
}