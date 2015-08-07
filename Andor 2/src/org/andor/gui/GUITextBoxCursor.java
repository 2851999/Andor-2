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

package org.andor.gui;

import org.andor.core.Colour;
import org.andor.core.Object2D;
import org.andor.core.Object2DBuilder;
import org.andor.core.Vector2f;
import org.andor.core.resource.texture.Texture;
import org.andor.utils.Timer;

public class GUITextBoxCursor extends Object2D {
	
	/* The text box instance */
	public GUITextBox textBox;
	
	/* The renderer */
	public GUIComponentRenderer renderer;
	
	/* The timer */
	public Timer timer;
	
	/* The time between blink */
	public long timeBetweenBlink;
	
	/* The boolean that states whether this cursor should be shown */
	public boolean cursorShown;
	
	/* The colour and texture */
	public Colour colour;
	public Texture texture;
	
	/* The default constructor */
	public GUITextBoxCursor() {
		
	}
	
	/* The constructor */
	public GUITextBoxCursor(GUITextBox textBox, float thickness) {
		//Assign the variables
		this.setWidth(thickness);
		//Setup
		setup(textBox);
	}
	
	/* The method used to setup this cursor */
	public void setup(GUITextBox textBox) {
		//Assign the variables
		this.textBox = textBox;
		this.setHeight(this.textBox.renderer.font.getHeight("A"));
		this.renderer = new GUIComponentRenderer(Object2DBuilder.createQuad(this.getWidth(), this.getHeight(), Colour.WHITE));
		this.renderer.colours = new Colour[] { Colour.BLACK };
		this.timer = new Timer();
		this.timer.start();
		this.timeBetweenBlink = 600;
		this.cursorShown = false;
		//Set the colour and texture
		if (this.colour != null)
			this.setColour(colour);
		if (this.texture != null)
			this.setTexture(texture);
	}
	
	/* The methods used to set the colour/texture and check whether they are set/return them */
	public void setColour(Colour colour) { this.renderer.colours = new Colour[] { colour }; }
	public void setTexture(Texture texture) { this.renderer.textures = new Texture[] { texture }; }
	public Colour getColour() { return this.renderer.colours[0]; }
	public Texture getTexture() { return this.renderer.textures[0]; }
	public boolean hasColour() { return this.renderer.colours != null; }
	public boolean hasTexture() { return this.renderer.textures != null; }
	
	/* The method used to render this cursor */
	public void render() {
		//Check the timer
		if (this.timer.hasTimePassed(this.timeBetweenBlink)) {
			//Toggle cursor shown
			this.cursorShown = ! this.cursorShown;
			//Restart the timer
			this.timer.restart();
		}
		//Make sure the cursor should be shown
		if (this.cursorShown) {
			//Get the position of the text box
			Vector2f p = this.textBox.getPosition();
			//The position values for the cursor
			float x = 1 + p.x + (this.textBox.renderer.font.getWidth(this.textBox.renderText.substring(0, this.textBox.cursorIndex - this.textBox.viewIndexStart)));
			float y = (p.y + (this.textBox.getHeight() / 2)) - (this.getHeight() / 2);
			//Set the cursor's position
			this.position = new Vector2f(x, y);
			//Render the cursor
			this.renderer.render(this, this.textBox.active);
		}
	}
	
	/* The method used to show this cursor */
	public void showCursor() {
		//Restart the timer
		this.timer.restart();
		//Show the cursor
		this.cursorShown = true;
	}
	
	/* The set/get methods */
	public void setTimeBetweenBlink(long timeBetweenBlink) { this.timeBetweenBlink = timeBetweenBlink; }
	public long getTimeBetweenBlink() { return this.timeBetweenBlink; }
	public boolean isCursorShowing() { return this.cursorShown; }
	
}