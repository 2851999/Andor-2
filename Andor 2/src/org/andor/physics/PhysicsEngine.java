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

package org.andor.physics;

import java.util.ArrayList;
import java.util.List;

import org.andor.core.BaseObject;

public class PhysicsEngine {
	
	/* The objects added to the physics engine */
	private static List<BaseObject> objects = new ArrayList<BaseObject>();
	
	/* The static method used to update all of the objects */
	public static void update(int delta) {
		//Calculate the delta in seconds
		float deltaSeconds = ((float) delta) / 1000f;
		//Go through the objects
		for (int a = 0; a < objects.size(); a++)
			objects.get(a).update(deltaSeconds);
	}
	
	/* The static methods used to add or remove objects */
	public static void add(BaseObject object) { objects.add(object); }
	public static void remove(BaseObject object) { objects.remove(object); }
	
	
}