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

public class Timer {
	
	/* The boolean that represents whether this timer is running */
	private boolean running;
	
	/* The boolean that represents whether this timer is paused */
	private boolean paused;
	
	/* The boolean that represents whether this timer has stopped */
	private boolean stopped;
	
	/* The start time of this timer */
	private long startTime;
	
	/* The end time of this timer */
	private long endTime;
	
	/* The time this timer started the last pause */
	private long pauseStart;
	
	/* The time this timer was paused in total */
	private long pauseTotal;
	
	/* The constructor */
	public Timer() {
		//Assign the default values
		reset();
	}
	
	/* The method used to start this timer */
	public void start() {
		//Set the start time
		this.startTime = Time.getMiliseconds();
		//Set the correct variables
		this.running = true;
		this.stopped = false;
	}
	
	/* The method used to pause this timer */
	public void pause() {
		//Set the correct pause time
		this.pauseStart = Time.getMiliseconds();
		//Set the correct variables
		this.paused = true;
	}
	
	/* The method used to resume this timer */
	public void resume() {
		//Add on the correct amount of time that this timer was paused for
		this.pauseTotal += Time.getMiliseconds() - this.pauseStart;
		//Set the correct variables
		this.paused = false;
	}
	
	/* The method used to stop this timer */
	public void stop() {
		//Set the correct end time
		this.endTime = Time.getMiliseconds();
		//Set the correct variables
		this.running = false;
		this.stopped = false;
	}
	
	/* The method used to reset this timer */
	public void reset() {
		//Assign the variables
		this.running = false;
		this.paused = false;
		this.stopped = false;
		this.startTime = 0;
		this.endTime = 0;
		this.pauseStart = 0;
		this.pauseTotal = 0;
	}
	
	/* The method used to restart this timer */
	public void restart() {
		//Reset and start the timer
		this.reset();
		this.start();
	}
	
	/* The method used to get the current time on this timer */
	public long getTime() {
		//Check the current state of the timer
		if (this.stopped)
			//Return the amount time
			return this.endTime - this.startTime - this.pauseTotal;
		else if (this.paused)
			//Return the amount of time
			return this.pauseStart - this.startTime - this.pauseTotal;
		else
			//Return the amount of time
			return Time.getMiliseconds() - this.startTime - this.pauseTotal;
	}
	
	/* The method used to check whether a certain amount of time has passed */
	public boolean hasTimePassed(long time) {
		//Make sure this timer has actually been started at least once
		if (this.running || this.paused || this.stopped)
			//Return the correct value
			return getTime() >= time;
		else
			return false;
	}
	
	/* The methods used to return whether number of seconds, minutes and hours has passed */
	public boolean hasTimePassedSeconds(float seconds) { return hasTimePassed((long) seconds * 1000); }
	public boolean hasTimePassedMinutes(float minutes) { return hasTimePassedSeconds(minutes * 60); }
	public boolean hasTimePassedHours(float hours) { return hasTimePassedMinutes(hours * 60); }
	
	/* The methods used to return the number of seconds, minutes and hours that has passed */
	public float getSeconds() { return (float) getTime() / 1000f; }
	public float getMinutes() { return getSeconds() / 60f; }
	public float getHours() { return getMinutes() / 60f; }
	
	/* The method used to return the current state of this timer */
	public boolean isRunning() { return this.running; }
	public boolean isPaused() { return this.paused; }
	public boolean hasStopped() { return this.hasStopped(); }
	
}