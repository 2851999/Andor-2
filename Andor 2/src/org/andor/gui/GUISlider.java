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
import org.andor.core.Object2DBuilder;
import org.andor.core.RenderableObject2D;
import org.andor.core.Vector2f;
import org.andor.core.input.ControllerAxis;
import org.andor.core.input.ControllerButton;
import org.andor.core.input.InputListenerInterface;
import org.andor.core.input.InputManager;
import org.andor.core.resource.texture.Texture;

public class GUISlider extends GUIComponent implements InputListenerInterface {
	
	/* The horizontal slider direction */
	public static final int DIRECTION_HORIZONTAL = 1;

	/* The vertical slider direction */
	public static final int DIRECTION_VERTICAL = 2;

	/* The variable which states which direction the slider should slide */
	public int sliderDirection;

	/* The slider button */
	public GUIButton sliderButton;

	/* The current slider value */
	public float sliderValue;
	
	/* The constructor */
	public GUISlider(GUIButton sliderButton , int sliderDirection, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Assign the variables
		this.sliderButton = sliderButton;
		this.sliderDirection = sliderDirection;
		this.sliderValue = 0;
		this.renderer.colours = new Colour[] { Colour.WHITE };
		this.attach(this.sliderButton);
		InputManager.addListener(this);
	}
	
	/* The constructor */
	public GUISlider(Colour colour, GUIButton sliderButton , int sliderDirection, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Assign the variables
		this.sliderButton = sliderButton;
		this.sliderDirection = sliderDirection;
		this.sliderValue = 0;
		this.renderer.colours = new Colour[] { colour };
		this.attach(this.sliderButton);
		InputManager.addListener(this);
	}
	
	/* The constructor */
	public GUISlider(Texture texture, GUIButton sliderButton , int sliderDirection, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, texture, Colour.WHITE), width, height));
		//Assign the variables
		this.sliderButton = sliderButton;
		this.sliderDirection = sliderDirection;
		this.sliderValue = 0;
		this.renderer.colours = new Colour[] { Colour.WHITE };
		this.renderer.textures = new Texture[] { texture };
		this.attach(this.sliderButton);
		InputManager.addListener(this);
	}
	
	/* The constructor */
	public GUISlider(Colour colour, Texture texture, GUIButton sliderButton , int sliderDirection, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, texture, Colour.WHITE), width, height));
		//Assign the variables
		this.sliderButton = sliderButton;
		this.sliderDirection = sliderDirection;
		this.sliderValue = 0;
		this.renderer.colours = new Colour[] { colour };
		this.renderer.textures = new Texture[] { texture };
		this.attach(this.sliderButton);
		InputManager.addListener(this);
	}
	
	/* The methods used to set the colour/texture and check whether they are set/return them */
	public void setColour(Colour colour) { this.renderer.colours = new Colour[] { colour }; }
	public void setTexture(Texture texture) { this.renderer.textures = new Texture[] { texture }; }
	public Colour getColour() { return this.renderer.colours[0]; }
	public Texture getTexture() { return this.renderer.textures[0]; }
	public boolean hasColour() { return this.renderer.colours != null; }
	public boolean hasTexture() { return this.renderer.textures != null; }
	
	/* The method called to update this component */
	protected void updateComponent() {
		//Update the button
		this.sliderButton.update();
	}
	
	/* The method called to render this component */
	protected void renderComponent() {
		float width = this.getWidth();
		float height = this.getHeight();
		float buttonWidth = this.sliderButton.getWidth();
		float buttonHeight = this.sliderButton.getHeight();
		//Check the slider direction
		if (this.sliderDirection == DIRECTION_VERTICAL) {
			//Clamp the boundaries
			if (this.sliderButton.position.y < 0)
				this.sliderButton.position.y = 0;
			else if (this.sliderButton.position.y > height - buttonHeight)
				this.sliderButton.position.y = height - buttonHeight;
			//Make sure the button is in the middle
			this.sliderButton.position.x = -buttonWidth / 2 + width / 2;
		} else if (this.sliderDirection == DIRECTION_HORIZONTAL) {
			//Clamp the boundaries
			if (this.sliderButton.position.x < 0)
				this.sliderButton.position.x = 0;
			else if (this.sliderButton.position.x > width - buttonWidth)
				this.sliderButton.position.x = width - buttonWidth;
			//Make sure the button is in the middle
			this.sliderButton.position.y = -buttonHeight / 2 + height / 2;
		}
		//Render the button
		this.sliderButton.render();
	}
	
	/* The keyboard events */
	public void onKeyPressed(int code) {}
	public void onKeyReleased(int code) {}
	public void onKeyTyped(int code) {}
	public void onChar(int code, char character) {}
	
	/* The mouse events */
	public void onMousePressed(int button) {}
	public void onMouseReleased(int button) {}
	public void onMouseClicked(int button) {}
	public void onMouseMoved(double x, double y, double xOffset, double yOffset) {}
	public void onMouseDragged(double x, double y, double xOffset, double yOffset) {
		//Make sure this is visible and active
		if (this.visible && this.active) {
			float width = this.getWidth();
			float height = this.getHeight();
			//Get this sider's position
			Vector2f p = this.getPosition();
			//Check the direction of this slider
			if (this.sliderDirection == DIRECTION_VERTICAL) {
				if (this.sliderButton.mouseHoveringInside) {
					if (y > p.y && y < p.y + height) {
						this.sliderButton.position.y += yOffset;
						//Set the slider value
						this.sliderValue = (this.sliderButton.position.y / (height - this.sliderButton.getHeight())) * 100;
					}
				}
			} else if (this.sliderDirection == DIRECTION_HORIZONTAL) {
				if (this.sliderButton.mouseHoveringInside) {
					if (x > p.x && x < p.x + width) {
						this.sliderButton.position.x += xOffset;
						//Set the slider value
						this.sliderValue = (this.sliderButton.position.y / (height - this.sliderButton.getHeight())) * 100;
					}
				}
			}
			//Clamp the slider value
			if (this.sliderValue < 0)
				this.sliderValue = 0;
			else if (this.sliderValue > 100)
				this.sliderValue = 100;
		}
	}
	public void onMouseEnter() {}
	public void onMouseLeave() {}
	
	/* The scroll event */
	public void onScroll(double xOffset, double yOffset) {}
	
	/* The window events */
	public void onWindowSizeChanged(Vector2f oldSize, Vector2f newSize) {}
	
	/* The controller events */
	public void onControllerButtonPressed(ControllerButton button) {}
	public void onControllerButtonReleased(ControllerButton button) {}
	public void onControllerAxisChange(ControllerAxis axis) {}
	
	/* The get methods */
	public int getSliderDirection() { return this.sliderDirection; }
	public GUIButton getSliderButton() { return this.sliderButton; }
	public float getSliderValue() { return this.sliderValue; }
}