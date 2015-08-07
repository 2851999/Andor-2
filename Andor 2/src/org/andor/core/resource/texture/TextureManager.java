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

package org.andor.core.resource.texture;

import java.util.ArrayList;
import java.util.List;

public class TextureManager {
	
	/* All of the created textures */
	private static List<Texture> textures = new ArrayList<Texture>();
	
	/* The static method used to add a texture */
	public static void add(Texture texture) {
		textures.add(texture);
	}
	
	/* The static method used to delete */
	public static void deleteAll() {
		for (int a = 0; a < textures.size(); a++)
			textures.get(a).delete();
	}
	
}