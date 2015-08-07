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

package org.andor.core.resource.texture;

import org.andor.Settings;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

public class TextureParameters {
	
	/* The default values */
	public static int DEFAULT_TARGET = GL11.GL_TEXTURE_2D;
	public static int DEFAULT_FILTER = GL11.GL_NEAREST;
	public static int DEFAULT_CLAMP  = GL12.GL_CLAMP_TO_EDGE;
	public static boolean DEFAULT_SHOULD_CLAMP = false;
	
	/* The various pre-made texture parameters */
	public static TextureParameters DEFAULT = new TextureParameters();
	
	/* The values */
	public int target;
	public int filter;
	public int clamp;
	public boolean shouldClamp;
	
	/* The default constructor */
	public TextureParameters() {
		//Assign the default values
		this.target = DEFAULT_TARGET;
		this.filter = DEFAULT_FILTER;
		this.clamp  = DEFAULT_CLAMP;
		this.shouldClamp = DEFAULT_SHOULD_CLAMP;
	}
	
	/* The other constructors */
	public TextureParameters(int target, int filter, boolean shouldClamp) {
		this.target = target;
		this.filter = filter;
		this.clamp  = DEFAULT_CLAMP;
		this.shouldClamp = shouldClamp;
	}
	
	public TextureParameters(int target, int filter, int clamp) {
		this.target = target;
		this.filter = filter;
		this.clamp  = clamp;
		this.shouldClamp = true;
	}
	
	public TextureParameters(int target, int filter, int clamp, boolean shouldClamp) {
		this.target = target;
		this.filter = filter;
		this.clamp  = clamp;
		this.shouldClamp = shouldClamp;
	}
	
	/* The method used to apply all of the texture parameters to a texture */
	public void apply(int texture, boolean bind, boolean unbind) {
		if (bind)
			GL11.glBindTexture(this.target, texture);
		//Apply the filter
		GL11.glTexParameteri(this.target, GL11.GL_TEXTURE_MAG_FILTER, this.filter);
		GL11.glTexParameteri(this.target, GL11.GL_TEXTURE_MIN_FILTER, this.filter);
		//Apply the clamp if necessary
		if (this.shouldClamp) {
			GL11.glTexParameteri(this.target, GL11.GL_TEXTURE_WRAP_S, this.clamp);
			GL11.glTexParameteri(this.target, GL11.GL_TEXTURE_WRAP_T, this.clamp);
		}
		//Check to see whether the filter uses mipmapping
		if (filter == GL11.GL_NEAREST_MIPMAP_NEAREST ||
			filter == GL11.GL_NEAREST_MIPMAP_LINEAR  ||
			filter == GL11.GL_LINEAR_MIPMAP_NEAREST  ||
			filter == GL11.GL_LINEAR_MIPMAP_LINEAR) {
			//Generate mipmaps
			GL30.glGenerateMipmap(this.target);
			GL11.glTexParameterf(this.target, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, Settings.Video.MaxAnisotropicSamples);
		}
		if (unbind)
			GL11.glBindTexture(this.target, 0);
	}
	
	/* The other apply methods with the bind and unbind values preset */
	public void apply(int texture, boolean unbind) { this.apply(texture, true, unbind); }
	public void apply(int texture) { this.apply(texture, true, false); }
	
	/* The setters and getters */
	public TextureParameters setTarget(int target) { this.target = target; return this; }
	public TextureParameters setFilter(int filter) { this.filter = filter; return this; }
	public TextureParameters setClamp(int clamp) { this.clamp = clamp; return this; }
	public TextureParameters setShouldClamp(boolean shouldClamp) { this.shouldClamp = shouldClamp; return this; }
	public int getTarget() { return this.target; }
	public int getFilter() { return this.filter; }
	public int getClamp() { return this.clamp; }
	public boolean shouldClamp() { return this.shouldClamp; }
	
}