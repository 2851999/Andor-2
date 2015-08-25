/* *********************************************
 * ANDOR
 * 
 * USE - EDUCATIONAL PURPOSES ONLY
 *
 * COPYRIGHT @ 2014-2015
 **********************************************/

package org.andor.core;

import org.andor.core.resource.texture.Texture;

public class TextureAnimation2D extends Animation2D {
	
	/* The textures */
	private Texture[] textures;
	private Texture tileSet;
	
	/* The data for a tile set animation */
	private int currentTile;
	private float tileSetWidth;
	private float tileSetHeight;
	private float tileWidth;
	private float tileHeight;
	private int tilesPerRow;
	private int tilesPerColumn;
	private int numberOfTiles;
	
	/* The constructors */
	public TextureAnimation2D(RenderableObject2D entity, Texture[] textures, int timeBetweenFrame) {
		super(entity, timeBetweenFrame);
		//Assign the variables
		this.textures = textures;
	}
	
	public TextureAnimation2D(RenderableObject2D entity, Texture[] textures, int timeBetweenFrame, boolean repeat) {
		super(entity, timeBetweenFrame, repeat);
		//Assign the variables
		this.textures = textures;
	}
	
	/* The constructors for a tile sheet animation */
	public TextureAnimation2D(RenderableObject2D entity, Texture tileSet, int tilesPerRow, int tilesPerColumn, int timeBetweenFrame, boolean repeat) {
		super(entity, timeBetweenFrame, repeat);
		this.tileSet = tileSet;
		this.currentTile = -1;
		this.tileSetWidth = tileSet.getWidth();
		this.tileSetHeight = tileSet.getHeight();
		this.tileWidth = this.tileSetWidth / tilesPerRow;
		this.tileHeight = this.tileSetHeight / tilesPerColumn;
		this.tilesPerRow = tilesPerRow;
		this.tilesPerColumn = tilesPerColumn;
		this.numberOfTiles = this.tilesPerRow * this.tilesPerColumn;
	}
	
	/* The method used to update this animation */
	public void updateAnimation() {
		if (textures != null) {
			//Set the texture
			this.getEntity().setTexture(this.textures[this.getCurrentFrame()]);
		} else {
			if (currentTile != this.getCurrentFrame()) {
				if (this.tileSet != null)
					this.getEntity().setTexture(this.tileSet);
				this.currentTile = this.getCurrentFrame();
				//Get the coordinates of the tile in pixels
				float x = (this.currentTile % this.tilesPerRow) * this.tileWidth;
				float y = (this.currentTile / this.tilesPerRow) * this.tileHeight;
				//The texture coordinates
				float top = y / this.tileSetHeight;
				float left = x / this.tileSetWidth;
				float bottom = top + (this.tileHeight / this.tileSetHeight);
				float right = left + (this.tileWidth / this.tileSetWidth);
				this.getEntity().getRenderData().updateTextureCoordinates(new float[] { left, top, right, top, right, bottom, left, bottom });
			}
		}
	}
	
	/* The method used to reset this animation */
	public void resetAnimation() {
		if (this.textures != null)
			this.getEntity().setTexture(this.textures[this.getCurrentFrame()]);
		else
			this.currentTile = -1;
	}
	
	/* The method used to determine whether the animation has finished */
	public boolean hasFinished() {
		if (textures != null)
			return this.getCurrentFrame() == this.textures.length;
		else
			return this.getCurrentFrame() == this.numberOfTiles;
	}
	
	/* The set/get methods */
	public void setTextures(Texture[] textures) { this.textures = textures; }
	public Texture[] getTextures() { return this.textures; }
	
}