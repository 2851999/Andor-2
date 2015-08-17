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

import org.andor.Andor;
import org.andor.Settings;
import org.andor.core.Matrix4f;
import org.andor.core.resource.shader.Shader;
import org.andor.utils.BufferUtils;
import org.andor.utils.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ForwardRenderer extends Renderer {
	
	/* The static method used to initialise this renderer */
	public ForwardRenderer() {
		super(Settings.Resource.Manager.loadShader("BasicShader"));
	}
	
	/* The static method used to render something */
	public void renderData(RenderData data, Matrix4f modelMatrix) {
		//Get the shader
		Shader currentShader = getShader();
		//Get various shader locations
		int vertexArray = currentShader.getAttributeLocation("Vertex");
		int colourArray = -1;
		int textureCoordinateArray = -1;
		int normalArray = -1;
		if (data.hasColours())
			colourArray = currentShader.getAttributeLocation("Colour");
		if (data.hasTextureCoordinates())
			textureCoordinateArray = currentShader.getAttributeLocation("TextureCoordinate");
		if (data.hasNormals())
			normalArray = currentShader.getAttributeLocation("Normal");
		//Use the shader
		currentShader.bind();
		//Make sure a camera has been set
		if (cameras.size() > 0)
			currentShader.setUniformMatrix4fv("ModelViewProjectionMatrix", BufferUtils.createFlippedBuffer(getCamera().getProjectionViewMatrix().multiply(modelMatrix).transpose().getValues()));
		else
			Logger.log("Andor - ForwardRenderer", "Main camera not set, nothing will be able to render!");
		//Check the material
		if (data.hasMaterial()) {
			currentShader.setUniformf("DiffuseColour", data.getMaterial().getDiffuseColour());
			if (data.getMaterial().hasDiffuseTexture())
				data.getMaterial().getDiffuseTexture().bind();
			else
				Andor.Resources.Textures.Blank.bind();
		} else
			Andor.Resources.Textures.Blank.bind();
		
		currentShader.setUniformf("Texture", 0);
		
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
		currentShader.unbind();
	}
	
}