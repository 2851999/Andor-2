/* *********************************************
 * ANDOR
 * 
 * USE - EDUCATIONAL PURPOSES ONLY
 *
 * COPYRIGHT @ 2014-2015
 **********************************************/

package org.andor.core;

import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureSet;

public class SkyBox {
	
	/* The texture coordinates */
	public static final float[] TEXTURE_COORDINATES = {
		//BACK
		1f / 4f, 1f / 3f,
		0f, 1f / 3f,
		0f, 2f / 3f,
		1f / 4f, 2f / 3f,
		
		//LEFT
		1f / 4f, 2f / 3f,
		2f / 4f, 2f / 3f,
		2f / 4f, 1f / 3f,
		1f / 4f, 1f / 3f,
		
		//FORWARD
		2f / 4f, 1f / 3f,
		3f / 4f, 1f / 3f,
		3f / 4f, 2f / 3f,
		2f / 4f, 2f / 3f,
		
		//BOTTOM
		2f / 4f, 1f,
		1f / 4f, 1f,
		1f / 4f, 2f / 3f,
		2f / 4f, 2f / 3f,
		
		//RIGHT
		3f / 4f, 2f / 3f,
		1, 2f / 3f,
		1, 1f / 3f,
		3f / 4f, 1f / 3f,
		
		//TOP
		2f / 4f, 1f / 3f,
		1f / 4f, 1f / 3f,
		1f / 4f, 0f,
		2f / 4f, 0f
	};
	
	/* The texture set used for this skybox */
	public TextureSet textureSet;
	
	/* The texture */
	public Texture texture;
	
	/* The box */
	public RenderableObject3D box;
	
	/* The constructor */
	public SkyBox(String filePath, String[] fileNames, boolean external, float size) {
		//Create the texture set
		this.textureSet = new TextureSet();
		//The textures
		Texture[] textures = new Texture[6];
		//Add all of the textures
		textures[0] = this.textureSet.load(filePath + fileNames[0], external);
		textures[1] = this.textureSet.load(filePath + fileNames[1], external);
		textures[2] = this.textureSet.load(filePath + fileNames[2], external);
		textures[3] = this.textureSet.load(filePath + fileNames[3], external);
		textures[4] = this.textureSet.load(filePath + fileNames[4], external);
		textures[5] = this.textureSet.load(filePath + fileNames[5], external);
		//Load the texture
		this.texture = this.textureSet.join();
		//Setup the cube
		this.box = new RenderableObject3D(Object3DBuilder.createCube(size, size, size, textures, Colour.WHITE));
		//Assign the texture
		this.box.getRenderData().getMaterial().setDiffuseTexture(this.texture);
	}
	
	/* The constructor */
	public SkyBox(Texture texture, float size) {
		//Setup the cube
		this.box = new RenderableObject3D(Object3DBuilder.createCube(size, size, size, texture, Colour.WHITE));
		this.texture = texture;
		//Assign the texture coordinates
		this.box.getRenderData().updateTextureCoordinates(TEXTURE_COORDINATES);
		//Assign the texture
		this.box.getRenderData().getMaterial().setDiffuseTexture(this.texture);
	}
	
	/* The method used to render this skybox */
	public void renderSkybox() {
		//Render this
		this.box.update();
		this.box.render();
	}
	
}