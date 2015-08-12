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

import org.andor.utils.ShaderUtils;

public class ShaderLoader {
	
	/* The static methods used to load a complete shader */
	public static Shader load(String path, boolean external) {
		//Load the shader and return it
		return ShaderUtils.createShader(path, external);
	}
	
	public static Shader load(String path, String vertexExtension, String fragmentExtension, boolean external) {
		//Load the shader and return it
		return ShaderUtils.createShader(path, vertexExtension, fragmentExtension, external);
	}
	
}