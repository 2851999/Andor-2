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

import org.andor.Settings;
import org.andor.core.Colour;
import org.andor.core.Object2DBuilder;
import org.andor.core.RenderableObject2D;
import org.andor.core.Vector2f;
import org.andor.core.input.ControllerAxis;
import org.andor.core.input.ControllerButton;
import org.andor.core.input.InputListenerInterface;
import org.andor.core.input.InputManager;
import org.andor.core.input.Mouse;
import org.andor.core.resource.texture.Texture;

public class GUIWindow extends GUIComponent implements InputListenerInterface {
	
	/* The window's title */
	public String windowTitle;
	
	/* The top bar */
	public GUIFill topBar;
	
	/* The close button */
	public GUIButton closeButton;
	
	/* The boolean that states whether the mouse is currently pressed within the top bar */
	private boolean mousePressed;
	
	/* The constructor */
	public GUIWindow(String windowTitle, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Assign the variables
		this.windowTitle = windowTitle;
		this.topBar = new GUIFill(this);
		this.topBar.setSize(this.getWidth(), 20);
		this.mousePressed = false;
		InputManager.addListener(this);
	}
	
	/* The constructor */
	public GUIWindow(String windowTitle, Colour colour, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Assign the variables
		this.renderer.colours = new Colour[] { colour };
		this.windowTitle = windowTitle;
		this.topBar = new GUIFill(this);
		this.topBar.setSize(this.getWidth(), 20);
		this.mousePressed = false;
		InputManager.addListener(this);
	}
	
	/* The constructor */
	public GUIWindow(String windowTitle, Texture texture, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Assign the variables
		this.renderer.colours = new Colour[] { Colour.WHITE };
		this.renderer.textures = new Texture[] { texture };
		this.windowTitle = windowTitle;
		this.topBar = new GUIFill(this);
		this.topBar.setSize(this.getWidth(), 20);
		this.mousePressed = false;
		InputManager.addListener(this);
	}
	
	/* The constructor */
	public GUIWindow(String windowTitle, Colour colour, Texture texture, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Assign the variables
		this.renderer.colours = new Colour[] { colour };
		this.renderer.textures = new Texture[] { texture };
		this.windowTitle = windowTitle;
		this.topBar = new GUIFill(this);
		this.topBar.setSize(this.getWidth(), 20);
		this.mousePressed = false;
		InputManager.addListener(this);
	}
	
	/* The method used to centre this window */
	public void centre() {
		//Place this window into the centre of the window
		this.setPosition(new Vector2f((Settings.Window.Width / 2) - (this.getWidth() / 2), (Settings.Window.Height / 2) - (this.getHeight() / 2)));
	}
	
	/* The method used to assign the colour */
	public void setColour(Colour colour) { this.renderer.colours = new Colour[] { colour }; }
	
	/* The method used to assign the texture */
	public void setTexture(Texture texture) { this.renderer.textures = new Texture[] { texture }; }
	
	public Colour getColour() { return this.renderer.colours[0]; }
	public Texture getTexture() { return this.renderer.textures[0]; }
	public boolean hasColour() { return this.renderer.colours != null; }
	public boolean hasTexture() { return this.renderer.textures != null; }
	
	/* The method used to assign the top bar colour */
	public void setTopColour(Colour colour) { this.topBar.colour = colour; }
	
	/* The method used to assign the texture */
	public void setTopTexture(Texture texture) { this.topBar.texture = texture; }
	
	public Colour getTopColour() { return this.topBar.colour; }
	public Texture getTopTexture() { return this.topBar.texture; }
	public boolean hasTopColour() { return this.topBar.colour != null; }
	public boolean hasTopTexture() { return this.topBar.texture != null; }
	
	/* The method called to update this component */
	protected void updateComponent() {
		//Update the close button
		if (this.closeButton != null) {
			this.closeButton.update();
			if (this.closeButton.clicked)
				this.close();
		}
	}
	
	/* The method called to render this component */
	protected void renderComponent() {
		//Fill the top bar
		this.topBar.render();
		//Render the text
		this.renderTextAtCentre(this.windowTitle, new Vector2f(0, -(this.getHeight() / 2 - this.topBar.getHeight() / 2)));
		//Render the close button
		if (this.closeButton != null)
			this.closeButton.render();
	}
	
	/* The method used to close this window */
	public void close() {
		this.visible = false;
		this.active = false;
	}
	
	/* The keyboard events */
	public void onKeyPressed(int code) {}
	public void onKeyReleased(int code) {}
	public void onKeyTyped(int code) {}
	public void onChar(int code, char character) {}
	
	/* The mouse events */
	public void onMousePressed(int button) {
		//Make sure this is visible and active
		if (this.visible && this.active) {
			if (this.topBar.getBounds().contains((float) Mouse.lastX, (float) Mouse.lastY))
				this.mousePressed = true;
		}
	}
	public void onMouseReleased(int button) {
		//Make sure this is visible and active
		if (this.visible && this.active)
			this.mousePressed = false;
	}
	public void onMouseClicked(int button) {}
	public void onMouseMoved(double x, double y, double xOffset, double yOffset) {}
	public void onMouseDragged(double x, double y, double xOffset, double yOffset) {
		//Make sure this is visible and active
		if (this.visible && this.active) {
			if (this.topBar.getBounds().contains((float) (x - xOffset), (float) (y - yOffset)) && this.mousePressed)
				this.position.add(new Vector2f((float) xOffset, (float) yOffset));
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
	
	public void setCloseButton(GUIButton closeButton) {
		this.closeButton = closeButton;
		this.closeButton.position = new Vector2f(this.getWidth() - closeButton.getWidth(), 0);
		this.attach(closeButton);
	}
	
}