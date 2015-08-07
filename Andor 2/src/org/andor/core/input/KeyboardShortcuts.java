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

import java.util.ArrayList;
import java.util.List;

public class KeyboardShortcuts extends InputListener {
	
	/* The listeners */
	public List<KeyboardShortcutListener> listeners;
	
	/* The shortcuts */
	public List<KeyboardShortcut> shortcuts;
	
	/* The constructor */
	public KeyboardShortcuts() {
		//Assign the variables
		this.listeners = new ArrayList<KeyboardShortcutListener>();
		this.shortcuts = new ArrayList<KeyboardShortcut>();
		this.addListener();
	}
	
	/* The method used to add a shortcut */
	public void add(KeyboardShortcut shortcut) {
		//Make this the shortcut's instance
		shortcut.instance = this;
		//Add it to the list
		this.shortcuts.add(shortcut);
	}
	
	/* The method used to add a listener */
	public void addListener(KeyboardShortcutListener listener) {
		//Add it to the list
		this.listeners.add(listener);
	}
	
	/* The method used to call an onShortcut() event */
	public void callOnShortcut(KeyboardShortcut e) {
		//Go through each listener
		for (int a = 0; a < this.listeners.size(); a++)
			//Call the method in the current listener
			this.listeners.get(a).onShortcut(e);
	}
	
	/* The method called when a key on the keyboard is pressed */
	public void onKeyPressed(int code) {
		//Call the method in all of the shortcuts
		for (int a = 0; a < this.shortcuts.size(); a++)
			this.shortcuts.get(a).onKeyPressed(code);
	}
	
	/* The method called when a key on the keyboard is released */
	public void onKeyReleased(int code) {
		//Call the method in all of the shortcuts
		for (int a = 0; a < this.shortcuts.size(); a++)
			this.shortcuts.get(a).onKeyReleased(code);
	}
	
}