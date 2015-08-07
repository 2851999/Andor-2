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

public class FPSLimiter {
	
	/* The maximum FPS */
	private int maxFPS;
	
	/* The target frame delta */
	private int targetDelta;
	
	/* The constructor */
	public FPSLimiter(int maxFPS) {
		//Assign the variables
		this.maxFPS = maxFPS;
		this.targetDelta = 1000 / maxFPS;
	}
	
	/* The method used to update this given the current delta */
	public void update(int currentDelta) {
		if (this.maxFPS != -1) {
			int difference = targetDelta - currentDelta;
			try {
				if (difference > 0)
					Thread.sleep(difference);
				else
					Thread.sleep(targetDelta);
			} catch (InterruptedException e) {
				Logger.log("Andor - FPSLimiter", "An error occured while trying to cap the framerate", Log.ERROR);
				e.printStackTrace();
			}
		}
	}
	
}