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

package org.andor.core.input.bindings;

import java.util.ArrayList;
import java.util.List;

public class ControlBindings {
	
	/* The list of bindings */
	private List<ControlBinding> bindings;
	
	/* The list of listeners */
	private List<ControlListenerInterface> listeners;
	
	/* The constructor */
	public ControlBindings() {
		//Assign the variables
		this.bindings = new ArrayList<ControlBinding>();
		this.listeners = new ArrayList<ControlListenerInterface>();
	}
	
	/* The method used to call an event */
	public void callOnBindingChange(ControlBinding binding) {
		//Go through all of the listeners and call the event
		for (int a = 0; a < this.listeners.size(); a++)
			this.listeners.get(a).onBindingChange(binding);
	}
	
	/* The methods used to add and remove bindings/listeners */
	public void add(ControlBinding binding) {
		if (! binding.hasBindings())
			binding.setBindings(this);
		this.bindings.add(binding); 
	}
	
	public void add(ControlListenerInterface listener) { this.listeners.add(listener); }
	public void remove(ControlBinding binding) { this.bindings.remove(binding); }
	public void remove(ControlListenerInterface listener) { this.listeners.remove(listener); }
	
}