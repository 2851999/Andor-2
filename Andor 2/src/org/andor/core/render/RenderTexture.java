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

import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureParameters;
import org.lwjgl.opengl.GL11;

public class RenderTexture extends Texture {
	
	/* The values needed to define this texture */
	public int internalFormat;
	public int format;
	public int attachment;
	public int type;
	
	/* The constructor */
	public RenderTexture(int width, int height, int internalFormat, int format, int attachment, int type, TextureParameters parameters) {
		super(parameters);
		//Assign the variables
		this.internalFormat = internalFormat;
		this.format = format;
		this.attachment = attachment;
		this.type = type;
		//Bind the image
		GL11.glBindTexture(parameters.getTarget(), this.getPointer());
		//Setup the texture
		GL11.glTexImage2D(parameters.getTarget(), 0, this.internalFormat, width, height, 0, this.format, this.type, (java.nio.ByteBuffer) null);
		//Apply the parameters
		this.getParameters().apply(this.getPointer(), false);
	}
	
	/* The getter and setters */
	public void setInternalFormat(int internalFormat) { this.internalFormat = internalFormat; }
	public void setFormat(int format) { this.format = format; }
	public void setAttachment(int attachment) { this.attachment = attachment; }
	public void setType(int type) { this.type = type; }
	public int getInternalFormat() { return this.internalFormat; }
	public int getFormat() { return this.format; }
	public int getAttachment() { return this.attachment; }
	public int getType() { return this.type; }
	
}