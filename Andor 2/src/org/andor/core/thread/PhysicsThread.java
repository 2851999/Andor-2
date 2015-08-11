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

import org.andor.Settings;
import org.andor.physics.PhysicsEngine;

public class PhysicsThread extends EngineThread {
	
	/* The constructor */
	public PhysicsThread() {
		super("Physics", Settings.Physics.MaxFPS);
	}
	
	/* The method called every time this thread is executed */
	public void update() {
		//Update the physics engine
		PhysicsEngine.update((int) this.getDelta());
	}
	
}