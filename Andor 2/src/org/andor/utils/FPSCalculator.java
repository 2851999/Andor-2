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

package org.andor.utils;

public class FPSCalculator {
	
	/* The different modes of calculating the FPS */
	public static final int MODE_FPS_PER_FRAME = 1;
	public static final int MODE_FPS_PER_SECOND = 2;
	public static final int MODE_FPS_OFF = 3;
	
	/* The current FPS mode */
	private int mode;
	
	/* The time of the last frame (Milliseconds) */
	private long lastFrameTime;
	
	/* The time of the last frame (Milliseconds) used for calculating the delta time */
	private long lastDeltaFrameTime;
	
	/* The current delta time */
	private long currentDeltaTime;
	
	/* The current FPS */
	private int currentFPS;
	
	/* The current FPS's count */
	private int fpsCount;
	
	/* The value that represents whether this has started recording the frame times */
	private boolean started;
	
	/* The constructor */
	public FPSCalculator() {
		this(MODE_FPS_PER_SECOND);
	}
	
	/* The constructor with the mode given */
	public FPSCalculator(int mode) {
		//Assign the variables
		this.mode = mode;
		this.currentDeltaTime = 0;
		this.currentFPS = 0;
		this.started = false;
	}
	
	/* The method called to update this, should be called every frame */
	public void update() {
		//Check to see whether this has started
		if (! this.started) {
			//Assign the times
			this.lastFrameTime = Time.getMiliseconds();
			this.lastDeltaFrameTime = Time.getMiliseconds();
			//Set started to true
			this.started = true;
		}
		//Make sure the FPS calculation is turned on
		if (this.mode != MODE_FPS_OFF) {
			//The current time
			long current = Time.getMiliseconds();
			//Check the mode
			if (this.mode == MODE_FPS_PER_FRAME) {
				//Recalculate the current delta
				this.currentDeltaTime = current - this.lastDeltaFrameTime;
				//Set the time this update occurred
				this.lastDeltaFrameTime = current;
				//Make sure the current delta is not 0 (Prevents DivideByZero Exception)
				if (this.currentDeltaTime != 0)
					//Calculate the current FPS
					this.currentFPS = (int) (1000 / this.currentDeltaTime);
			} else if (this.mode == MODE_FPS_PER_SECOND) {
				//Recalculate the current delta
				this.currentDeltaTime = current - this.lastDeltaFrameTime;
				//Set the time this update occurred
				this.lastDeltaFrameTime = current;
				//Add 1 to the current FPS count
				this.fpsCount++;
				//Check to see whether the require amount of time has passed
				if ((current - this.lastFrameTime) >= 1000) {
					//Set the time this update occurred
					this.lastFrameTime = current;
					//Set the current FPS
					this.currentFPS = fpsCount;
					//Reset the FPS count
					this.fpsCount = 0;
				}
			}
		}
	}
	
	/* The method used to reset this */
	public void reset() {
		this.lastFrameTime = Time.getMiliseconds();
		this.lastDeltaFrameTime = Time.getMiliseconds();
		this.currentDeltaTime = 0;
		this.currentFPS = 0;
	}
	
	/* The setter and getter methods */
	public void setMode(int mode) { this.mode = mode; }
	public int getMode() { return this.mode; }
	public long getDelta() { return this.currentDeltaTime; }
	public int getFPS() { return this.currentFPS; }
	
}