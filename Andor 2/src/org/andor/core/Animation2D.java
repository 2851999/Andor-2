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

import org.andor.utils.Timer;

public abstract class Animation2D {
	
	/* The entity */
	private RenderableObject2D entity;
	
	/* The time between each frame of the animation */
	private int timeBetweenFrame;
	
	/* The timer */
	private Timer timer;
	
	/* The current frame */
	private int currentFrame;
	
	/* Is the animation running */
	private boolean running;
	
	/* The boolean that states whether this animation should repeat */
	private boolean repeat;
	
	/* The constructor */
	public Animation2D(RenderableObject2D entity, int timeBetweenFrame) {
		//Assign the variables
		this.entity = entity;
		this.timeBetweenFrame = timeBetweenFrame;
		this.repeat = false;
		this.timer = new Timer();
		this.currentFrame = 0;
	}
	
	/* The constructor */
	public Animation2D(RenderableObject2D entity, int timeBetweenFrame, boolean repeat) {
		//Assign the variables
		this.entity = entity;
		this.timeBetweenFrame = timeBetweenFrame;
		this.repeat = repeat;
		this.timer = new Timer();
		this.currentFrame = 0;
	}
	
	/* The method used to update this animation */
	public void update() {
		//Make sure this animation is running
		if (this.running) {
			//Check the timer
			if (this.timer.hasTimePassed(this.timeBetweenFrame)) {
				//Increment the current frame
				this.currentFrame++;
				//Check the current frame
				if (this.hasFinished()) {
					//Check to see whether this animation should repeat
					if (this.repeat) {
						//Reset this animation
						this.reset();
						//Start this animation
						this.start();
					} else {
						//Stop this animation
						this.stop();
					}
				} else {
					//UPDATE THE ANIMATION HERE
					this.updateAnimation();
					//Restart the timer
					this.timer.restart();
				}
			}
		}
	}
	
	/* The method called during the course of an animation */
	public void startAnimation() {}
	public void updateAnimation() {}
	public void stopAnimation() {}
	public void resetAnimation() {}
	
	/* The method used to determine whether the animation has finished */
	public abstract boolean hasFinished();
	
	/* The method used to start this animation */
	public void start() {
		//Set running to true
		this.running = true;
		//Start the timer
		this.timer.start();
		this.startAnimation();
		this.updateAnimation();
	}
	
	/* The method used to reset this animation */
	public void reset() {
		//Reset the variables
		this.running = false;
		this.currentFrame = 0;
		this.timer.reset();
		this.resetAnimation();
	}
	
	/* The method used to pause this animation */
	public void pause() {
		//Pause the timer
		this.timer.pause();
	}
	
	/* The method used to resume this animation */
	public void resume() {
		//Resume the timer
		this.timer.resume();
	}
	
	/* The method used to restart this animation */
	public void restart() {
		this.stop();
		this.reset();
		this.start();
	}
	
	/* The method used to stop this animation */
	public void stop() {
		//Set running to false
		this.running = false;
		//Stop the timer
		this.timer.stop();
		this.stopAnimation();
	}
	
	/* The set/get methods */
	public void setEntity(RenderableObject2D entity) { this.entity = entity; }
	public void setTimeBetweenFrame(int timeBetweenFrame) { this.timeBetweenFrame = timeBetweenFrame; }
	public void setRepeat(boolean repeat) { this.repeat = repeat; }
	public RenderableObject2D getEntity() { return this.entity; }
	public long getTimeBetweenFrame() { return this.timeBetweenFrame; }
	public int getCurrentFrame() { return this.currentFrame; }
	public boolean isRunning() { return this.running; }
	public boolean getRepeat() { return this.repeat; }
	
}