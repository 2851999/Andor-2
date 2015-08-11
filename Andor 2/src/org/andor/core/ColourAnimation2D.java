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

public class ColourAnimation2D extends Animation2D {
	
	/* The colours */
	private Colour[] colours;
	
	/* The constructor */
	public ColourAnimation2D(RenderableObject2D entity, Colour[] colours, int timeBetweenFrame) {
		super(entity, timeBetweenFrame);
		//Assign the variables
		this.colours = colours;
	}
	
	/* The constructor */
	public ColourAnimation2D(RenderableObject2D entity, Colour[] colours, int timeBetweenFrame, boolean repeat) {
		super(entity, timeBetweenFrame, repeat);
		//Assign the variables
		this.colours = colours;
	}
	
	/* The method used to update this animation */
	public void updateAnimation() {
		//Set the image
		this.getEntity().setColour(this.colours[this.getCurrentFrame()]);
	}
	
	/* The method used to reset this animation */
	public void resetAnimation() {
		this.getEntity().setColour(this.colours[this.getCurrentFrame()]);
	}
	
	/* The method used to determine whether the animation has finished */
	public boolean hasFinished() {
		return this.getCurrentFrame() == this.colours.length;
	}
	
	/* The set/get methods */
	public void setColours(Colour[] colours) { this.colours = colours; }
	public Colour[] getColours() { return this.colours; }
	
}