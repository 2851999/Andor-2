/* *********************************************
 * ANDOR
 * 
 * USE - EDUCATIONAL PURPOSES ONLY
 *
 * COPYRIGHT @ 2014-2015
 **********************************************/

package org.andor.core;

import java.util.ArrayList;
import java.util.List;

import org.andor.core.resource.texture.Texture;
import org.andor.utils.Logger;

public class Sprite2D extends TextureEntity2D {
	
	/* The animations */
	public List<Animation2D> animations;
	
	/* The names of the animations */
	public List<String> animationNames;
	
	/* The id's of the animations */
	public List<Integer> animationIds;
	
	/* The running animations */
	public List<Animation2D> currentAnimations;
	
	/* The constructor */
	public Sprite2D(Texture texture, Colour colour, float width, float height) {
		//Call the super constructor
		super(texture, colour, width, height);
		//Assign the variables
		this.animations = new ArrayList<Animation2D>();
		this.animationNames = new ArrayList<String>();
		this.animationIds = new ArrayList<Integer>();
		this.currentAnimations = new ArrayList<Animation2D>();
	}
	
	/* The other constructors */
	public Sprite2D(Texture texture, Colour colour) {
		this(texture, colour, texture.getWidth(), texture.getHeight());
	}
	
	public Sprite2D(Texture texture) {
		this(texture, Colour.WHITE);
	}
	
	public Sprite2D(Texture texture, float width, float height) {
		this(texture, Colour.WHITE, width, height);
	}
	
	/* The method used to update this sprite */
	public void update() {
		super.update();
		if (this.currentAnimations != null)
		//Go through each animation
		for (int a = 0; a < this.currentAnimations.size(); a++)
			//Update the current animation
			this.currentAnimations.get(a).update();
	}
	
	/* The method used to start an animation given the name */
	public void start(String name) {
		//Add the animation
		this.currentAnimations.add(this.getByName(name));
		//Start the animation
		this.currentAnimations.get(this.currentAnimations.size() - 1).start();;
	}
	
	/* The method used to start an animation given the id */
	public void start(int id) {
		//Add the animation
		this.currentAnimations.add(this.getById(id));
		//Start the animation
		this.currentAnimations.get(this.currentAnimations.size() - 1).start();;
	}
	
	/* The method used to add an animation */
	public void add(Animation2D animation, String name, int id) {
		//Add the values
		this.animations.add(animation);
		this.animationNames.add(name);
		this.animationIds.add(id);
	}
	
	/* The method used to get an animation given the name */
	public Animation2D getByName(String name) {
		//The animation
		Animation2D animation = null;
		//Get the place
		int place = this.getPositionByName(name);
		//Check the place
		if (place == -1)
			//Log an error
			Logger.log("Andor - Sprite2D getByName()", "The animation with the name " + name + " could not be found");
		else
			//Assign the animation
			animation = this.animations.get(place);
		//Return the place
		return animation;
	}
	
	/* The method used to get an animation given the id */
	public Animation2D getById(int id) {
		//The animation
		Animation2D animation = null;
		//Get the place
		int place = this.getPositionById(id);
		//Check the place
		if (place == -1)
			//Log an error
			Logger.log("Andor - Sprite2D getById()", "The animation with the id " + id + " could not be found");
		else
			//Assign the animation
			animation = this.animations.get(place);
		//Return the place
		return animation;
	}
	
	/* The method used to get the place of an animation given the name */
	public int getPositionByName(String name) {
		//The place
		int place = -1;
		//Go through the animation names
		for (int a = 0; a < this.animationNames.size(); a++) {
			//Check the current name against the one given
			if (this.animationNames.get(a).equals(name)) {
				//Set the place
				place = a;
				//Exit the loop
				break;
			}
		}
		//Return the place
		return place;
	}
	
	/* The method used to get the place of an animation given the id */
	public int getPositionById(int id) {
		//The place
		int place = -1;
		//Go through the animation ids
		for (int a = 0; a < this.animationIds.size(); a++) {
			//Check the current id against the one given
			if (this.animationIds.get(a) == id) {
				//Set the place
				place = a;
				//Exit the loop
				break;
			}
		}
		//Return the place
		return place;
	}
	
}