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
import org.andor.core.Font;
import org.andor.core.Object2DBuilder;
import org.andor.core.RenderableObject2D;
import org.andor.core.Vector2f;
import org.andor.core.input.ControllerAxis;
import org.andor.core.input.ControllerButton;
import org.andor.core.input.InputListenerInterface;
import org.andor.core.input.InputManager;
import org.andor.core.input.Keyboard;
import org.andor.core.input.KeyboardShortcut;
import org.andor.core.input.KeyboardShortcutListener;
import org.andor.core.input.KeyboardShortcuts;
import org.andor.core.input.Mouse;
import org.andor.core.resource.texture.Texture;
import org.andor.utils.ClipboardUtils;
import org.andor.utils.MaskUtils;
import org.lwjgl.glfw.GLFW;

public class GUITextBox extends GUIComponent implements InputListenerInterface, KeyboardShortcutListener {
	
	/* The text within this text box */
	public String text;

	/* The text that is rendered */
	public String renderText;

	/* The boolean that states whether this text box is currently selected */
	public boolean selected;

	/* The boolean that states whether this text box is masked */
	public boolean masked;

	/* The mask */
	public String mask;

	/* The default text */
	public String defaultText;

	/* The font of the default text */
	public Font defaultTextFont;

	/* The cursor's position within the text */
	public int cursorIndex;

	/* The cursor */
	public GUITextBoxCursor cursor;

	/* The viewing start and end index */
	public int viewIndexStart;
	public int viewIndexEnd;

	/* The boolean that states whether there is a selection */
	public boolean isSelection;

	/* The selection's start and end index */
	public int selectionIndexStart;
	public int selectionIndexEnd;

	/* The selection */
	public GUITextBoxSelection selection;
	
	/* The shortcuts */
	public KeyboardShortcuts shortcuts;
	
	/* The constructor - should set either textures or colour when using this */
	public GUITextBox(float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Setup all of the variables
		this.setup();
	}
	
	/* The constructor */
	public GUITextBox(Colour colour, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Set the values
		this.renderer.colours = new Colour[] { colour };
		//Setup all of the variables
		this.setup();
	}
	
	/* The constructor */
	public GUITextBox(Texture texture, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, texture, Colour.WHITE), width, height));
		//Set the values
		this.renderer.textures = new Texture[] { texture };
		//Setup all of the variables
		this.setup();
	}
	
	/* The constructor */
	public GUITextBox(Texture texture, Colour colour, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, texture, Colour.WHITE), width, height));
		//Set the values
		this.renderer.textures = new Texture[] { texture };
		this.renderer.colours = new Colour[] { colour };
		//Setup all of the variables
		this.setup();
	}
	
	/* The methods used to set the colour/texture and check whether they are set/return them */
	public void setColour(Colour colour) { this.renderer.colours = new Colour[] { colour }; }
	public void setTexture(Texture texture) { this.renderer.textures = new Texture[] { texture }; }
	public Colour getColour() { return this.renderer.colours[0]; }
	public Texture getTexture() { return this.renderer.textures[0]; }
	public boolean hasColour() { return this.renderer.colours != null; }
	public boolean hasTexture() { return this.renderer.textures != null; }
	
	/* The method used to setup all of the variables */
	public void setup() {
		//Add this to the list of input listeners
		InputManager.addListener(this);
		//Assign the variables
		this.text = "";
		this.renderText = "";
		this.selected = false;
		this.masked = false;
		this.mask = "*";
		this.defaultText = "";
		this.cursorIndex = 0;
		this.cursor = new GUITextBoxCursor(this, 1f);
		this.viewIndexStart = 0;
		this.viewIndexEnd = 0;
		this.isSelection = false;
		this.selectionIndexStart = 0;
		this.selectionIndexEnd = 0;
		this.selection = new GUITextBoxSelection(this);
		this.shortcuts = new KeyboardShortcuts();
		this.shortcuts.addListener(this);
		this.shortcuts.add(new KeyboardShortcut("Shift-Left", new int[] { GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_LEFT }));
		this.shortcuts.add(new KeyboardShortcut("Shift-Left", new int[] { GLFW.GLFW_KEY_RIGHT_SHIFT, GLFW.GLFW_KEY_LEFT }));
		this.shortcuts.add(new KeyboardShortcut("Shift-Right", new int[] { GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_RIGHT }));
		this.shortcuts.add(new KeyboardShortcut("Shift-Right", new int[] { GLFW.GLFW_KEY_RIGHT_SHIFT, GLFW.GLFW_KEY_RIGHT }));
		this.shortcuts.add(new KeyboardShortcut("Cut", new int[] { GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_X }));
		this.shortcuts.add(new KeyboardShortcut("Cut", new int[] { GLFW.GLFW_KEY_RIGHT_CONTROL, GLFW.GLFW_KEY_X }));
		this.shortcuts.add(new KeyboardShortcut("Copy", new int[] { GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_C }));
		this.shortcuts.add(new KeyboardShortcut("Copy", new int[] { GLFW.GLFW_KEY_RIGHT_CONTROL, GLFW.GLFW_KEY_C }));
		this.shortcuts.add(new KeyboardShortcut("Paste", new int[] { GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_V }));
		this.shortcuts.add(new KeyboardShortcut("Paste", new int[] { GLFW.GLFW_KEY_RIGHT_CONTROL, GLFW.GLFW_KEY_V }));
	}
	
	/* The method used to update this component */
	protected void updateComponent() {
		
	}
	
	/* The method used to render this component */
	protected void renderComponent() {
		//The font that should be used
		Font f = this.renderer.font;
		//Check to see whether the default text should be used
		if (this.shouldUseDefaultText()) {
			//Set the render text
			this.renderText = this.defaultText;
			//Check to see whether the default text font should be used
			if (this.defaultTextFont != null)
				f = this.defaultTextFont;
		} else {
			//Update the text to render
			this.updateRenderText();
			this.clipRenderText();
		}
		//Get the position of this component
		Vector2f p = this.getPosition();
		//Get the position to render the text at
		float textX = p.x + 1;
		float textY = (p.y + (this.getHeight() / 2)) - (f.getHeight(this.renderText) / 2);
		//Render the text
		f.render(this.renderText, textX, textY);
		//Make sure this is selected
		if (this.selected)
			//Render the cursor
			this.cursor.render();
		//Render the selection
		this.selection.render();
	}
	
	/* The method used to update the render text */
	public void updateRenderText() {
		if (! this.shouldUseDefaultText()) {
			//Try and prevent any problems with the view index being out of bounds by accident
			if (this.viewIndexStart < 0)
				this.viewIndexStart = 0;
			if (this.viewIndexEnd > this.text.length())
				this.viewIndexEnd = this.text.length();
			//Set the text
			this.renderText = this.text.substring(this.viewIndexStart, this.viewIndexEnd);
			//Check to see whether the text should be masked
			if (this.masked)
				//Mask the text
				this.renderText = MaskUtils.mask(this.renderText, this.mask);
		}
	}
	
	/* The method used to clip the render text */
	public void clipRenderText() {
		float width = this.getWidth();
		//Keep going until the render text fits
		while (this.renderer.font.getWidth(this.renderText) >= width - 2 && this.cursorIndex != this.viewIndexStart) {// && this.cursorIndex <= this.viewIndexEnd) {
			//Change the viewing index
			this.viewIndexStart++;
			//Update the render text
			this.updateRenderText();
		}
		//Keep going until the render text fits
		while (this.renderer.font.getWidth(this.renderText) >= width - 2 && this.cursorIndex <= this.viewIndexEnd) {
			//Change the viewing index
			this.viewIndexEnd--;
			//Update the render text
			this.updateRenderText();
		}
	}
	
	/* The method used to move the cursor to a specific place given the x
	 * coordinate */
	public void moveCursor(double x) {
		//Get the closest index
		this.cursorIndex = getIndex(x);
		//Show the cursor
		this.cursor.showCursor();
	}
	
	/* The method used to get an index from a certain x position in the render text */
	public int getIndex(double x) {
		//Update the textToRender for rendering since we need to look at what the user can see
		this.updateRenderText();
		//Get the text to render as an array
		char[] textValue = this.renderText.toCharArray();
		//The current string
		String currentString = "";
		//The new cursor place
		int newPlace = 0;
		//Go through the texts
		for (int a = 0; a < textValue.length; a++) {
			//Add onto the current string
			currentString += textValue[a];

			//The x position to look for
			double lookX = 0;

			//The width of the string
			double widthOfString = this.renderer.font.getWidth(currentString);
			//The width of the last character
			double widthOfLastCharacter = this.renderer.font.getWidth(currentString.substring(currentString.length() - 1));

			//Add onto lookX the position this text box starts rendering the text
			lookX += this.position.x + 1;
			//Add onto lookX the width of the string - (the width of the last character / 2)
			lookX += widthOfString - (widthOfLastCharacter / 2);

			//Check the width of the string against the x position
			if (lookX < x)
				//Add 1 to the new place
				newPlace++;
			else
				//Exit the loop
				break;
		}
		//Assign the index relative to all of the text in this text box
		newPlace += this.viewIndexStart;
		//Return the new place
		return newPlace;
	}
	
	/* The method used to reset the current selection */
	public void resetSelection() {
		//Set the values
		this.isSelection = false;
		this.selectionIndexStart = 0;
		this.selectionIndexEnd = 0;
	}
	
	/* The method used to return the selection if there is one */
	public String getSelection() {
		//Check to see whether there is a selection
		if (this.isSelection) {
			//Check the start and end values
			if (this.selectionIndexStart < this.selectionIndexEnd)
				//Return the string
				return this.text.substring(this.selectionIndexStart, this.selectionIndexEnd);
			else
				//Return the string
				return this.text.substring(this.selectionIndexEnd, this.selectionIndexStart);
		} else
			//Return an empty string
			return "";
	}
	
	/* The method used to return the selection of the render text if there is one */
	public String getRenderTextSelection() {
		//Check to see whether there is a selection
		if (this.isSelection) {
			//Update the render text
			this.updateRenderText();
			this.clipRenderText();
			int sis = this.selectionIndexStart;
			if (sis < this.viewIndexStart)
				sis = this.viewIndexStart;
			else if (sis > this.viewIndexEnd)
				sis = this.viewIndexEnd;
			int sie = this.selectionIndexEnd;
			if (sie < this.viewIndexStart)
				sie = this.viewIndexStart;
			else if (sie > this.viewIndexEnd)
				sie = this.viewIndexEnd;
			//Check the start and end values
			if (this.selectionIndexStart <= this.selectionIndexEnd)
				//Return the string
				return this.renderText.substring(sis - this.viewIndexStart, sie - this.viewIndexStart);
			else
				//Return the string
				return this.renderText.substring(sie - this.viewIndexStart, sis - this.viewIndexStart);
		} else
			//Return an empty string
			return "";
	}
	
	/* The method used to delete the current selection */
	public void deleteSelection() {
		//Get the front and back part of the text
		String front = "";
		String back = "";
		//Check the index values
		if (this.selectionIndexStart < this.selectionIndexEnd) {
			front = this.text.substring(0, this.selectionIndexStart);
			back = this.text.substring(this.selectionIndexEnd);
		} else {
			front = this.text.substring(0, this.selectionIndexEnd);
			back = this.text.substring(this.selectionIndexStart);
		}
		//Calculate the amount of text that was removed
		int amountRemoved = this.text.length() - (front + back).length();
		//Make sure a gap isn't left
		int a = 0;
		while (a < amountRemoved) {
			if (this.viewIndexStart > 0)
				this.viewIndexStart--;
			a++;
		}
		//Set the text
		this.text = front + back;
		//If the selection is at the end it is likely the cursor will now be out of bounds
		if (this.cursorIndex > this.text.length())
			this.cursorIndex = this.text.length();
		//Reset the selection
		this.resetSelection();
	}
	
	/* The method called when the mouse enter's this component */
	protected void componentOnMouseEnter() { }
	
	/* The method called when the mouse leave's this component */
	protected void componentOnMouseLeave() { }
	
	/* The method called when the component is clicked */
	protected void componentOnClicked() {
		//Set selected to true
		this.selected = true;
		//Show the cursor
		this.cursor.showCursor();
	}
	
	/* The keyboard events */
	public void onKeyPressed(int code) {
		//Check to see whether this component is selected
		if (this.visible && this.active && this.selected) {
			//Check the key code
			if (code == GLFW.GLFW_KEY_BACKSPACE) {
				//Check to see whether there is a selection
				if (this.isSelection) {
					//Remove the selection
					this.deleteSelection();
				} else {
					//Make sure there is text to be removed and the cursor index isn't at the beginning
					if (this.text.length() > 0 && this.cursorIndex > 0) {
						//Split up the text using the cursor index
						String front = this.text.substring(0, this.cursorIndex);
						String back = this.text.substring(this.cursorIndex);
						//Remove the last letter
						this.text = front.substring(0, front.length() - 1) + back;
						if (this.cursorIndex == this.viewIndexStart) {
							//Decrease the cursor index
							this.cursorIndex--;
							if (this.viewIndexStart > 0)
								this.viewIndexStart--;
							this.viewIndexEnd--;
						} else {
							//Decrease the cursor index
							this.cursorIndex--;
							this.viewIndexEnd--;
							//Keep some text visible if there is more
							if (this.viewIndexStart > 0)
								this.viewIndexStart--;
							else
								this.viewIndexEnd++;
						}
					}
				}
			} else if (code == GLFW.GLFW_KEY_DELETE) {
				//Check to see whether there is a selection
				if (this.isSelection) {
					//Remove the selection
					this.deleteSelection();
				} else {
					//Make sure there is text to be removed and the cursor index isn't at the end
					if (this.text.substring(this.cursorIndex).length() > 0 && this.cursorIndex < this.text.length()) {
						//Split up the text using the cursor index
						String front = this.text.substring(0, this.cursorIndex);
						String back = this.text.substring(this.cursorIndex);
						//Remove the last letter
						this.text = front + back.substring(1);
						//Make sure there isn't some unseen text
						if (! (this.viewIndexEnd <= this.text.length())) {
							//Decrement the view's end index
							this.viewIndexEnd--;
							//Decrement the view's start index (Keeps text at the beginning and end)
							this.viewIndexStart--;
						}
						//Keep some text visible if there is more and there is no more text at the end (Finished deleting)
						//if (this.viewIndexStart > 0 && this.cursorIndex == this.viewIndexEnd)
							//this.viewIndexStart--;
					}
				}
			} else if (code == GLFW.GLFW_KEY_LEFT && ! Keyboard.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT) && ! Keyboard.isPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
				//Remove any selection
				this.resetSelection();
				//Make sure the cursor's current index is more than 0
				if (this.cursorIndex > 0) {
					//Check the cursor index and viewing index
					if (this.cursorIndex == this.viewIndexStart) {
						//Check to see whether there is any unseen text
						if (this.viewIndexStart > 0) {
							//Decrement the cursor index
							this.cursorIndex--;
							//Decrement the start of the viewing index
							this.viewIndexStart--;
						}
					} else {
						//Decrement the cursor index
						this.cursorIndex--;
					}
				}
			} else if (code == GLFW.GLFW_KEY_RIGHT && ! Keyboard.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT) && ! Keyboard.isPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
				//Remove any selection
				this.resetSelection();
				//Make sure the cursor's current index is less than the length of the text
				if (this.cursorIndex < this.text.length()) {
					//Check the cursor index and viewing index
					if (this.cursorIndex == this.viewIndexEnd) {
						//Check to see whether there is any unseen text
						if (this.viewIndexEnd > 0) {
							//Increment the cursor index
							this.cursorIndex++;
							//Increment the end of the viewing index
							this.viewIndexEnd++;
						}
					} else {
						//Increment the cursor index
						this.cursorIndex++;
					}
				}
			}
			//Show the cursor
			this.cursor.showCursor();
		}
	}
	public void onKeyReleased(int code) {}
	public void onKeyTyped(int code) {}
	public void onChar(int code, char character) {
		//Check to see whether this component is selected
		if (this.visible && this.active && this.selected) {
			//Check the key code
			if (code == GLFW.GLFW_KEY_BACKSPACE) {
			} else if (code == GLFW.GLFW_KEY_DELETE) {
			} else if (code == GLFW.GLFW_KEY_LEFT && ! Keyboard.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT) && ! Keyboard.isPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
			} else if (code == GLFW.GLFW_KEY_RIGHT && ! Keyboard.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT) && ! Keyboard.isPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
			} else {
				//Make sure the key that was pressed's character should be added to the text
				if (this.isDefined(character)) {
					//Check to see whether there is a selection
					if (this.isSelection) {
						//Remove the selection
						this.deleteSelection();
					}
					//Split up the text using the cursor index
					String front = this.text.substring(0, this.cursorIndex);
					String back = this.text.substring(this.cursorIndex);
					//Add the character onto the text
					this.text = front + character + back;
					//Check the viewing index and cursor index
					if (this.viewIndexStart == this.cursorIndex && this.viewIndexStart > 0) {
						//Increase the cursor index
						this.cursorIndex++;
					} else {
						//Increase the cursor index
						this.cursorIndex++;
						this.viewIndexEnd++;
					}
				}
			}
			//Show the cursor
			this.cursor.showCursor();
		}
	}
	
	/* The mouse events */
	public void onMousePressed(int button) {
		//Check to see whether the mouse wasn't clicked within this component
		if (! this.mouseHoveringInside)
			//Set selected to false
			this.selected = false;
		else if (this.selected && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			this.moveCursor(Mouse.lastX);
			//Reset the selection
			this.resetSelection();
		}
	}
	public void onMouseReleased(int button) {}
	public void onMouseClicked(int button) {}
	public void onMouseMoved(double x, double y, double xOffset, double yOffset) {}
	public void onMouseDragged(double x, double y, double xOffset, double yOffset) {
		//Make sure this is selected
		if (this.visible && this.active && this.selected) {
			//Check to see whether there is a selection
			if (! this.isSelection) {
				//Set the selection values
				this.selectionIndexStart = this.getIndex(x);
				this.selectionIndexEnd = this.selectionIndexStart;
				this.isSelection = true;
			} else {
				//Set the new selection index end
				this.selectionIndexEnd = this.getIndex(x);
				//Move the cursor
				this.moveCursor(x);
				
				//Check the index values
				if (this.viewIndexStart == this.cursorIndex) {
					//Keep some text visible if there is more
					if (this.cursorIndex > 0)
						this.cursorIndex--;
					if (this.viewIndexStart > 0)
						this.viewIndexStart--;
					if (this.selectionIndexEnd > 0)
						this.selectionIndexEnd--;
				}
				if (this.viewIndexEnd == this.cursorIndex) {
					//Keep some text visible if there is more
					if (this.cursorIndex < this.text.length())
						this.cursorIndex++;
					if (this.viewIndexEnd < this.text.length())
						this.viewIndexEnd++;
					if (this.selectionIndexEnd < this.text.length())
						this.selectionIndexEnd++;
				}
			}
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
	
	/* The method called when a keyboard shortcut has been completed */
	public void onShortcut(KeyboardShortcut e) {
		//Check the name of the shortcut
		if (e.name.equals("Shift-Left")) {
			//Make sure the cursor's current index is more than 0
			if (this.cursorIndex > 0) {
				//Check the cursor index and viewing index
				if (this.cursorIndex == this.viewIndexStart) {
					//Check to see whether there is any unseen text
					if (this.viewIndexStart > 0) {
						//Decrement the cursor index
						this.cursorIndex--;
						//Decrement the start of the viewing index
						this.viewIndexStart--;
					}
				} else {
					//Decrement the cursor index
					this.cursorIndex--;
				}
			}
			if (! this.isSelection) {
				this.isSelection = true;
				this.selectionIndexStart = this.cursorIndex + 1;
				this.selectionIndexEnd = this.selectionIndexStart;
			}
			if (this.selectionIndexEnd > 0)
				this.selectionIndexEnd--;
		} else if (e.name.equals("Shift-Right")) {
			//Make sure the cursor's current index is less than the length of the text
			if (this.cursorIndex < this.text.length()) {
				//Check the cursor index and viewing index
				if (this.cursorIndex == this.viewIndexEnd) {
					//Check to see whether there is any unseen text
					if (this.viewIndexEnd > 0) {
						//Increment the cursor index
						this.cursorIndex++;
						//Increment the end of the viewing index
						this.viewIndexEnd++;
					}
				} else {
					//Increment the cursor index
					this.cursorIndex++;
				}
			}
			if (! this.isSelection) {
				this.isSelection = true;
				this.selectionIndexStart = this.cursorIndex - 1;
				this.selectionIndexEnd = this.selectionIndexStart;
			}
			if (this.selectionIndexEnd < this.text.length())
				this.selectionIndexEnd++;
		} else if (e.name.equals("Cut")) {
			if (this.isSelection) {
				//Set the clipboard's text
				ClipboardUtils.setText(this.getSelection());
				//Delete the current selection
				this.deleteSelection();
			}
		} else if (e.name.equals("Paste")) {
			if (this.isSelection) {
				//Delete the current selection
				this.deleteSelection();
			}
			//Split up the text using the cursor index
			String front = this.text.substring(0, this.cursorIndex);
			String back = this.text.substring(this.cursorIndex);
			//Add the text from the clipboard onto the text
			this.text = front + ClipboardUtils.getText() + back;
			//Put the cursor onto the end of the new text
			this.cursorIndex = this.text.length() - back.length();
			this.viewIndexEnd = this.text.length();
		} else if (e.name.equals("Copy")) {
			if (this.isSelection)
				//Set the clipboard's text
				ClipboardUtils.setText(this.getSelection());
		}
	}
	
	/* The method used to check to see whether a character is defined */
	private boolean isDefined(char character) {
		return ((character != '\u0000') && (Character.isDefined(character)) && (! Character.isISOControl(character)));
	}
	
	/* The method used to check whether the default text should be used */
	public boolean shouldUseDefaultText() {
		return ! this.selected && this.text.length() == 0 && this.defaultText.length() > 0;
	}
	
	/* The set/get methods */
	public void setText(String text) {
		this.text = text;
		this.viewIndexEnd = text.length();
	}
	public void setRenderText(String renderText) { this.renderText = renderText; }
	public void setSelected(boolean selected) { this.selected = selected; }
	public void setMasked(boolean masked) { this.masked = masked; }
	public void setMask(String mask) { this.mask = mask; }
	public void setDefaultText(String defaultText) { this.defaultText = defaultText; }
	public void setDefaultTextFont(Font defaultTextFont) { this.defaultTextFont = defaultTextFont; }
	public void setCursorIndex(int cursorIndex) { this.cursorIndex = cursorIndex; }
	public String getText() { return this.text; }
	public String getRenderText() { return this.renderText; }
	public boolean isSelected() { return this.selected; }
	public boolean isMasked() { return this.masked; }
	public String getMask() { return this.mask; }
	public String getDefaultText() { return this.defaultText; }
	public Font getDefaultTextFont() { return this.defaultTextFont; }
	public int getCursorIndex() { return this.cursorIndex; }
	
}