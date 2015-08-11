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

package org.andor.core.thread;

import org.andor.utils.FPSCalculator;
import org.andor.utils.FPSLimiter;

public abstract class EngineThread extends BaseThread {
	
	/* The FPS calculator */
	private FPSCalculator fpsCalculator;
	
	/* The FPS limiter */
	private FPSLimiter fpsLimiter;
	
	/* The maximum fps */
	private int maxFPS;
	
	/* The constructor */
	public EngineThread(String name, int maxFPS) {
		super(name);
		this.maxFPS = maxFPS;
	}
	
	/* The method called when this thread starts */
	public void starting() {
		//Setup the fps calculator and limiter
		this.fpsCalculator = new FPSCalculator();
		this.fpsLimiter = new FPSLimiter(this.maxFPS);
	}
	
	/* The method called every time this thread is executed */
	public void tick() {
		//Update the FPS calculator
		this.fpsCalculator.update();
		//Update this thrad
		//Attempt to cap the framerate (Not 100% accurate)
		this.fpsLimiter.update((int) this.fpsCalculator.getDelta());
	}
	
	/* The method called to update this thread */
	public abstract void update();
	
	/* The method called when this thread is stopping */
	public void stopping() {
		
	}
	
	/* The methods used to return some values */
	public int getFPS() { return this.fpsCalculator.getFPS(); }
	public long getDelta() { return this.fpsCalculator.getDelta(); }
	
}