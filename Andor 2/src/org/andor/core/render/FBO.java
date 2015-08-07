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

import org.andor.utils.BufferUtils;
import org.andor.utils.Log;
import org.andor.utils.Logger;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class FBO {
	
	/* The target */
	public int target;
	
	/* The pointer to this FBO */
	public int pointer;
	
	/* The various textures within this FBO */
	public List<RenderTexture> textures;
	
	/* The constructor */
	public FBO(int target) {
		//Assign the variables
		this.target = target;
		this.pointer = GL30.glGenFramebuffers();
		this.textures = new ArrayList<RenderTexture>();
	}
	
	/* The method used to add a texture */
	public void add(RenderTexture texture) {
		//Add the texture
		this.textures.add(texture);
	}
	
	/* The method used to setup this FBO */
	public void setup() {
		//Bind this framebuffer
		GL30.glBindFramebuffer(this.target, this.pointer);
		//Go through the textures
		for (int a = 0; a < this.textures.size(); a++)
			//Point this FBO to the texture
			GL30.glFramebufferTexture2D(this.target, this.textures.get(a).getAttachment(), this.textures.get(a).getParameters().getTarget(), this.textures.get(a).getPointer(), 0);
		//Check the status of the FBO
		int status = GL30.glCheckFramebufferStatus(this.target);
		//Check for any errors
		if(status != GL30.GL_FRAMEBUFFER_COMPLETE)
			Logger.log("Andor - FBO setup()", "Can't initialise the FBO", Log.ERROR);
		this.unbind();
	}
	
	/* The method used to bind this FBO ready to write to it */
	public void bind() {
		//Bind the FBO
		GL30.glBindFramebuffer(this.target, this.pointer);
		//The buffers
		int[] buffers = new int[this.textures.size()];
		//Go through the textures
		for (int a = 0; a < this.textures.size(); a++) {
			//Check the current texture
			if (this.textures.get(a).getAttachment() != GL30.GL_DEPTH_ATTACHMENT)
				//Assign the next texture
				buffers[a] = this.textures.get(a).getAttachment();
		}
		GL20.glDrawBuffers(BufferUtils.createFlippedBuffer(buffers));
	}
	
	/* The method used to unbind the FBO */
	public void unbind() {
		//Unbind the FBO
		GL30.glBindFramebuffer(this.target, 0);
	}
	
	/* The getter and setters */
	public RenderTexture getTexture(int texture) { return this.textures.get(texture); }
	
}