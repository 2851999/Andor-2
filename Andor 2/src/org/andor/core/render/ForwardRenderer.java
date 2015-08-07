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

import org.andor.Andor;
import org.andor.Settings;
import org.andor.core.Camera;
import org.andor.core.Matrix4f;
import org.andor.core.resource.shader.Shader;
import org.andor.utils.BufferUtils;
import org.andor.utils.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ForwardRenderer {
	
	/* The main shader */
	public static Shader shader;
	
	/* The camera's */
	public static List<Camera> cameras = new ArrayList<Camera>();
	
	/* The static method used to initialise this renderer */
	public static void initialise() {
		//Setup the shader
		shader = Settings.Resource.Manager.loadShader("BasicShader");
		shader.bind();
		shader.addUniform("ModelViewProjectionMatrix", "mvp");
		shader.addUniform("Texture", "texture");
		shader.addAttribute("Vertex", "vertex");
		shader.addAttribute("Colour", "colour");
		shader.addAttribute("TextureCoordinate", "textureCoordinate");
		shader.addAttribute("Normal", "normal");
		shader.unbind();
	}
	
	/* The static method used to render something */
	public static void render(RenderData data, Matrix4f modelMatrix) {
		//Get various shader locations
		int vertexArray = shader.getAttributeLocation("Vertex");
		int colourArray = shader.getAttributeLocation("Colour");
		int textureCoordinateArray = shader.getAttributeLocation("TextureCoordinate");
		int normalArray = shader.getAttributeLocation("Normal");
		//Use the shader
		shader.bind();
		//Make sure a camera has been set
		if (cameras.size() > 0)
			shader.setUniformMatrix4fv("ModelViewProjectionMatrix", BufferUtils.createFlippedBuffer(cameras.get(cameras.size() - 1).getProjectionViewMatrix().multiply(modelMatrix).transpose().getValues()));
		else
			Logger.log("Andor - ForwardRenderer", "Main camera not set, nothing will be able to render!");
		//Check the material
		if (data.hasMaterial()) {
			if (data.getMaterial().hasDiffuseTexture())
				data.getMaterial().getDiffuseTexture().bind();
			else
				Andor.Resources.Textures.Blank.bind();
		} else
			Andor.Resources.Textures.Blank.bind();
		
		shader.setUniformf("Texture", 0);
		
		//Bind the vao and enable the arrays
		GL30.glBindVertexArray(data.getVAO());
		if (data.hasVertices())
			GL20.glEnableVertexAttribArray(vertexArray);
		if (data.hasColours())
			GL20.glEnableVertexAttribArray(colourArray);
		if (data.hasTextureCoordinates())
			GL20.glEnableVertexAttribArray(textureCoordinateArray);
		if (data.hasNormals())
			GL20.glEnableVertexAttribArray(normalArray);
		if (data.hasIndices()) {
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, data.getIndicesVBO());
			GL11.glDrawElements(data.getRenderMode(), data.getIndicesCount(), GL11.GL_UNSIGNED_SHORT, 0);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		} else
			GL11.glDrawArrays(data.getRenderMode(), 0, data.getNumberOfVertices());
		if (data.hasVertices())
			GL20.glDisableVertexAttribArray(vertexArray);
		if (data.hasColours())
			GL20.glDisableVertexAttribArray(colourArray);
		if (data.hasTextureCoordinates())
			GL20.glDisableVertexAttribArray(textureCoordinateArray);
		if (data.hasNormals())
			GL20.glDisableVertexAttribArray(normalArray);
		
		//Check the material
		if (data.hasMaterial()) {
			if (data.getMaterial().hasDiffuseTexture())
				data.getMaterial().getDiffuseTexture().unbind();
			else
				Andor.Resources.Textures.Blank.unbind();
		} else
			Andor.Resources.Textures.Blank.unbind();
		
		GL30.glBindVertexArray(0);
		shader.unbind();
	}
	
	/* The methods used to add or remove a camera */
	public static void add(Camera camera) { cameras.add(camera); }
	public static void remove(Camera camera) { cameras.remove(camera); }
	public static void removeCamera() { cameras.remove(cameras.size() - 1); }
	
}