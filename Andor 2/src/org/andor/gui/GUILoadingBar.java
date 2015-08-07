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
import org.andor.core.resource.texture.Texture;

public class GUILoadingBar extends GUIComponent {

	/* The number of stages in the loading bar */
	public int loadingStages;

	/* The current stage in the loading bar */
	public int currentLoadingStage;
	
	/* The fill */
	public GUIFill barFill;

	/* The constructor */
	public GUILoadingBar(int loadingStages, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Assign the variables
		this.loadingStages = loadingStages;
		this.currentLoadingStage = 0;
		this.renderer.colours = new Colour[] { Colour.WHITE };
		this.barFill = new GUIFill(this, Colour.WHITE);
	}
	
	/* The constructor */
	public GUILoadingBar(int loadingStages, Colour colour, float width, float height) {
		//Call the super constructor
		//If using a colour, the colour must be used to colour VBO setup properly (Same for textures)
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, Colour.WHITE), width, height));
		//Assign the variables
		this.loadingStages = loadingStages;
		this.currentLoadingStage = 0;
		this.renderer.colours = new Colour[] { colour };
		this.barFill = new GUIFill(this, Colour.WHITE);
	}
	
	/* The constructor */
	public GUILoadingBar(int loadingStages, Texture texture, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, texture, Colour.WHITE), width, height));
		//Assign the variables
		this.loadingStages = loadingStages;
		this.currentLoadingStage = 0;
		this.renderer.colours = new Colour[] { Colour.WHITE };
		this.renderer.textures = new Texture[] { texture };
		this.barFill = new GUIFill(this, Colour.WHITE);
	}
	
	/* The constructor */
	public GUILoadingBar(int loadingStages, Texture texture, Colour colour, float width, float height) {
		//Call the super constructor
		super(new RenderableObject2D(Object2DBuilder.createQuad(width, height, texture, Colour.WHITE), width, height));
		//Assign the variables
		this.loadingStages = loadingStages;
		this.currentLoadingStage = 0;
		this.renderer.textures = new Texture[] { texture };
		this.renderer.colours = new Colour[] { colour };
		this.barFill = new GUIFill(this, Colour.WHITE);
	}

	/* The overrideable method called when the current stage has changed */
	public void stageChanged() {

	}

	/* The method to update the loading bar */
	protected void updateComponent() {
		//Update the bar
		this.barFill.update();
	}

	/* The method used render the loading bar */
	protected void renderComponent() {
		//Setup the bar fill
		this.barFill.setWidth((this.getWidth() / this.loadingStages) * this.currentLoadingStage);
		this.barFill.setHeight(this.getHeight());
		//Render the loading bar
		this.barFill.render();
	}

	/* The method to increase the current stage */
	public void completedStage() {
		//Check the current stage
		if (this.currentLoadingStage != this.loadingStages) {
			//Increase the current loading stage
			this.currentLoadingStage ++;
			//Call the stage changed method
			this.stageChanged();
		}
	}

	/* Returns a boolean value which states whether the
	   loading bar has finished */
	public boolean hasCompleted() {
		//Return the correct value
		return this.currentLoadingStage == this.loadingStages;
	}

	/* Returns the percentage of the process that is completed */
	public float getPercentageComplete() {
		//Return the percentage
		return (float)((float) this.currentLoadingStage / (float) this.loadingStages) * 100;
	}
	
	/* The methods used to set/get the colour/textures for the loading bar */
	public void setBackgroundColour(Colour backgroundColour) { this.renderer.colours = new Colour[] { backgroundColour }; }
	public Colour getBackgroundColour() { return this.renderer.colours[0]; }
	public void setFillColour(Colour fillColour) { this.barFill.colour = fillColour; }
	public Colour getFillColour() { return this.barFill.colour; }
	public void setBackgroundTexture(Texture backgroundTexture) { this.renderer.textures = new Texture[] { backgroundTexture }; }
	public Texture getBackgroundTexture() { return this.renderer.textures[0]; }
	public void setFillTexture(Texture fillTexture) { this.barFill.setTexture(fillTexture);; }
	public Texture getFillTexture() { return this.barFill.texture; }
	public void setCurrentStage(int currentLoadingStage) { this.currentLoadingStage = currentLoadingStage; }
	public int getCurrentStage() { return this.currentLoadingStage; }
	
	public boolean hasBackgroundColour() { return this.renderer.colours != null; }
	public boolean hasBackgroundTexture() { return this.renderer.textures != null; }
	public boolean hasFillColour() { return this.barFill.colour != null; }
	public boolean hasFillTexture() { return this.barFill.texture != null; }
	
}