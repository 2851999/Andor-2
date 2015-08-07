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

import org.andor.core.Font;

public class GUILabel extends GUIComponent {
	
	/* The text */
	private String text;
	
	/* The constructor */
	public GUILabel(String text) {
		//Call the super constructor
		super(null);
		//Assign the variables
		this.text = text;
		this.setWidth(this.renderer.font.getWidth(text));
		this.setHeight(this.renderer.font.getHeight(text));
	}
	
	/* The constructor */
	public GUILabel(String text, Font font) {
		//Call the super constructor
		super(null);
		//Assign the variables
		this.text = text;
		this.renderer.font = font;
		this.setWidth(font.getWidth(text));
		this.setHeight(font.getHeight(text));
	}
	
	/* The method used to render this component */
	protected void renderComponent() {
		//Render the text
		this.renderText(this.text);
	}
	
	/* The methods used to set/return values */
	public void setText(String text) {
		this.text = text;
		this.setWidth(this.renderer.font.getWidth(text));
		this.setHeight(this.renderer.font.getHeight(text));
	}
	public void setFont(Font font) {
		this.renderer.font = font;
		this.setWidth(font.getWidth(text));
		this.setHeight(font.getHeight(text));
	}
	public String getText() { return this.text; }
	
}