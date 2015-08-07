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

package org.andor.core.resource.shader;

import java.util.ArrayList;
import java.util.List;

public class ShaderManager {
	
	/* The currently added shaders */
	private static List<Shader> shaders = new ArrayList<Shader>();
	
	/* The static method used to add a shader */
	public static void add(Shader shader) {
		shaders.add(shader);
	}
	
	/* The static method used to delete all of the shaders */
	public static void deleteAll() {
		for (int a = 0; a < shaders.size(); a++)
			shaders.get(a).delete();
	}
	
}