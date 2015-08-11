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

import org.andor.utils.Log;
import org.andor.utils.Logger;

public abstract class BaseThread implements Runnable {
	
	/* The name of this thread */
	private String name;
	
	/* The thread */
	private Thread thread;
	
	/* The boolean that controls whether this thread is running */
	private boolean alive;
	
	/* The constructor */
	public BaseThread(String name) {
		this.name = name;
		//Create the thread
		this.thread = new Thread(this);
	}
	
	/* The method used to start this thread */
	public void start() {
		this.starting();
		alive = true;
		this.thread.start();
	}
	
	/* The method inherited from Runnable */
	public void run() {
		//Keep going while this thread is active
		while (alive) {
			//Execute the thread
			this.tick();
		}
		//Stop the thread
		this.stopping();
	}
	
	/* The method used to stop this thread */
	public void stop() {
		alive = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			Logger.log("Andor - BaseThread stop()", "Error joining thread " + this.name, Log.ERROR);
			e.printStackTrace();
		}
	}
	
	/* The method called when this thread starts */
	public abstract void starting();
	
	/* The method called every time this thread is executed */
	public abstract void tick();
	
	/* The method called when this thread is stopping */
	public abstract void stopping();
	
	/* The setters and getters */
	public String getName() { return this.name; }
	public Thread getThread() { return this.thread; }
	
}