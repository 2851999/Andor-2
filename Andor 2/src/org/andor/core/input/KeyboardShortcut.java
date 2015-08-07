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

package org.andor.core.input;

import java.util.List;

import org.andor.utils.ArrayUtils;

public class KeyboardShortcut {
	
	/* The name of this shortcut */
	public String name;
	
	/* The keys needed to be pressed for this shortcut */
	public List<Integer> keys;
	
	/* The current state of each key */
	public boolean[] states;
	
	/* The instance of KeyboardShortcuts */
	public KeyboardShortcuts instance;
	
	/* The constructor */
	public KeyboardShortcut(String name, int[] keys) {
		//Assign the variables
		this.name = name;
		this.keys = ArrayUtils.toIntegerList(keys);
		this.states = new boolean[keys.length];
	}
	
	/* The method used to check whether this event has been completed and call
	 * any events if necessary */
	public void check() {
		//Check to see whether this shortcut has been completed
		if (this.hasCompleted()) {
			//Make sure the instance has been assigned
			if (this.instance != null)
				//Call the event
				this.instance.callOnShortcut(this);
		}
	}
	
	/* The method used to check whether this keyboard shortcut has been completed */
	public boolean hasCompleted() {
		//The boolean that represents whether an event has occurred
		boolean completed = true;
		//Go through each state
		for (int a = 0; a < this.states.length; a++)
			completed = completed && this.states[a];
		//Return the value
		return completed;
	}
	
	/* The method called when a key on the keyboard is pressed */
	public void onKeyPressed(int code) {
		//Check all of the keys against the key code
		for (int a = 0; a < this.keys.size(); a++) {
			if (code == this.keys.get(a)) {
				//Set that state to true
				this.states[a] = true;
				//Check this shortcut
				this.check();
			}
		}
	}
	
	/* The method called when a key on the keyboard is released */
	public void onKeyReleased(int code) {
		//Check all of the keys against the key code
		for (int a = 0; a < this.keys.size(); a++) {
			if (code == this.keys.get(a))
				//Set that state to false
				this.states[a] = false;
		}
	}
	
	
}