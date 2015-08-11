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

package org.andor.core.render;

import java.util.ArrayList;
import java.util.List;

import org.andor.core.Camera;
import org.andor.core.Matrix4f;
import org.andor.core.resource.shader.Shader;
import org.andor.utils.Logger;

public class Renderer {
	
	/* The different renderer instances */
	public static ForwardRenderer forward;
	
	/* The list of camera's */
	public static List<Camera> cameras;
	
	/* The shaders */
	public Shader shader;
	public static List<Shader> customShaders;
	
	/* The static method used to initialise the rendering system */
	public static void initialise() {
		//Assign the variables
		forward = new ForwardRenderer();
		cameras = new ArrayList<Camera>();
		customShaders = new ArrayList<Shader>();
	}
	
	/* The static method used to setup a shader for rendering */
	public static void setupShader(Shader shader) {
		//Add all of the needed uniforms and attributes
		shader.bind();
		shader.addUniform("ModelViewProjectionMatrix", "mvp");
		shader.addUniform("Texture", "texture");
		shader.addAttribute("Vertex", "vertex");
		shader.addAttribute("Colour", "colour");
		shader.addAttribute("TextureCoordinate", "textureCoordinate");
		shader.addAttribute("Normal", "normal");
		shader.unbind();
	}
	
	/* The static method used to render an object */
	public static void render(RenderData data, Matrix4f modelMatrix) {
		getRenderer().renderData(data, modelMatrix);
	}
	
	/* The static method used to get the renderer instance that is currently in use */
	public static Renderer getRenderer() {
		return forward;
	}
	
	/* The static method used to get the camera that is currently in use */
	public static Camera getCamera() {
		//Make sure a camera has been set
		if (cameras.size() > 0)
			return cameras.get(cameras.size() - 1);
		else {
			Logger.log("Andor - Renderer", "Main camera not set, nothing will be able to render!");
			return null;
		}
	}
	
	/* The method used to add/remove a camera or custom shader */
	public static void addCamera(Camera camera) { cameras.add(camera); }
	public static void removeCamera() { cameras.remove(cameras.size() - 1); }
	public static void addShader(Shader shader) { customShaders.add(shader); }
	public static void removeShader() { customShaders.remove(customShaders.size() - 1); }
	
	/* The constructor */
	public Renderer(Shader shader) {
		//Assign the variables
		this.shader = shader;
		//Setup the shader
		setupShader(this.shader);
	}
	
	/* The method used to render an object */
	public void renderData(RenderData data, Matrix4f modelMatrix) {
		
	}
	
	/* The method used to get the shader that should be used */
	public Shader getShader() {
		if (customShaders.size() <= 0)
			return shader;
		else
			return customShaders.get(customShaders.size() - 1);
	}
	
}