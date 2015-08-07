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

public class Font {
	
	/* The bitmap font */
	public BitmapText bitmapFont;
	
	/* The constructor with the font */
	public Font(BitmapText bitmapFont) {
		//Assign the variables
		this.bitmapFont = bitmapFont;
	}
	
	/* The method used to render some text */
	public void render(String text, float x, float y) {
		//Set the position
		this.bitmapFont.position = new Vector2f(x, y);
		this.bitmapFont.update();
		//Check the text
		if (this.bitmapFont.getCurrentText() == null || ! this.bitmapFont.getCurrentText().equals(text))
			this.bitmapFont.update(text);
		//Render the text
		this.bitmapFont.render();
	}
	
	public void renderAtCentre(String text, Object2D object) {
		this.renderAtCentre(text, object, new Vector2f(0, 0));
	}
	
	public void renderAtCentre(String text, Object2D object, Vector2f offset) {
		//Get the width and height of the text
		float textWidth = this.getWidth(text);
		float textHeight = this.getHeight(text);
		//Get the centre of the object
		Vector2f centre = object.getCentre();
		//Calculate the position to render the text
		float textX = centre.x - (textWidth / 2);
		float textY = centre.y - (textHeight / 2);
		//Add the offset
		textX += offset.x;
		textY += offset.y;
		//Render the text
		this.render(text, textX, textY);
	}
	
	/* The method used to set the font size */
	public void setSize(float size) {
		this.bitmapFont.fontSize = size;
	}
	
	/* The method used to get the width/height of some text */
	public float getWidth(String text) {
		return this.bitmapFont.getWidth(text);
	}
	
	public float getHeight(String text) {
		return this.bitmapFont.getHeight(text);
	}
	
}