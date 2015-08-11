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

import org.andor.Settings;
import org.andor.core.render.Renderer;
import org.andor.core.resource.shader.Shader;
import org.andor.core.resource.texture.TextureCubemap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

public class SkyBox extends TextureCubemap {
	
	/* The shader */
	public static Shader shader;
	
	/* The box */
	public RenderableObject3D box;
	
	/* The constructor */
	public SkyBox(String path, String[] fileNames, boolean external, float size) {
		super(path, fileNames[0], fileNames[1], fileNames[2], fileNames[3], fileNames[4], fileNames[5], external);
		this.box = new RenderableObject3D(Object3DBuilder.createCube(size, size, size));
		this.box.setTexture(this);
		setupShader();
	}
	
	/* The constructor given a single image */
	public SkyBox(String path, boolean external, float size) {
		super(path, external);
		this.box = new RenderableObject3D(Object3DBuilder.createCube(size, size, size));
		this.box.setTexture(this);
		setupShader();
	}
	
	/* The static method used to setup the shader if needed */
	public static void setupShader() {
		//Setup the shader if needed
		if (shader == null) {
			shader = Settings.Resource.Manager.loadShader("SkyBoxShader");
			shader.bind();
			shader.addUniform("ModelViewProjectionMatrix", "mvp");
			shader.addUniform("Texture", "skyBox");
			shader.addAttribute("Vertex", "vertex");
			shader.unbind();
		}
	}
	
	/* The method used to render this skybox */
	public void renderSkybox() {
		Renderer.addShader(shader);
		this.box.update();
		GL11.glEnable(GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS);
		Renderer.render(this.box.getRenderData(), this.box.getModelMatrix());
		Renderer.removeShader();
	}
	
}