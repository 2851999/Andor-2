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

import java.awt.image.BufferedImage;

import org.andor.utils.SkyBoxUtils;
import org.lwjgl.opengl.GL13;

public class TextureCubemap extends Texture {
	
	/* The constructor */
	public TextureCubemap() {
		super(new TextureParameters(GL13.GL_TEXTURE_CUBE_MAP));
	}
	
	/* The other constructors */
	public TextureCubemap(BufferedImage[] images) {
		this();
		this.load(images);
	}
	
	public TextureCubemap(String path, String front, String back, String left, String right, String top, String bottom, boolean external) {
		this();
		this.load(path, front, back, left, right, top, bottom, external);
	}
	
	public TextureCubemap(String path, boolean external) {
		this();
		this.load(path, external);
	}
	
	/* The methods used to load the textures for this cubemap */
	public void load(BufferedImage[] images) {
		this.bind();
		Texture.load(images[1], new TextureParameters(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z));
		Texture.load(images[0], new TextureParameters(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z));
		Texture.load(images[2], new TextureParameters(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X));
		Texture.load(images[3], new TextureParameters(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X));
		Texture.load(images[5], new TextureParameters(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y));
		Texture.load(images[4], new TextureParameters(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y));
		//Apply the parameters
		this.applyParameters(false, false);
	}
	
	public void load(String path, String front, String back, String left, String right, String top, String bottom, boolean external) {
		this.load(new BufferedImage[] { TextureLoader.loadBufferedImage(path + front, external),
				  TextureLoader.loadBufferedImage(path + back, external),
				  TextureLoader.loadBufferedImage(path + left, external),
				  TextureLoader.loadBufferedImage(path + right, external),
				  TextureLoader.loadBufferedImage(path + top, external),
				  TextureLoader.loadBufferedImage(path + bottom, external) });
	}
	
	public void load(String path, boolean external) {
		this.load(SkyBoxUtils.getSkyBoxImages(TextureLoader.loadBufferedImage(path, external)));
	}
	
}