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

import java.io.BufferedReader;
import java.io.IOException;

import org.andor.core.resource.shader.Shader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderUtils {
	
	/* The static method used to create a complete shader */
	public static Shader createShader(String path, boolean external) {
		//Load the shader and return it
		return new Shader(createShader(path + ".vs", external, GL20.GL_VERTEX_SHADER), createShader(path + ".fs", external, GL20.GL_FRAGMENT_SHADER));
	}
	
	/* The static method used to create a complete shader */
	public static Shader createShader(String path, String vertexExtension, String fragmentExtension, boolean external) {
		//Load the shader and return it
		return new Shader(createShader(path + vertexExtension, external, GL20.GL_VERTEX_SHADER), createShader(path + fragmentExtension, external, GL20.GL_FRAGMENT_SHADER));
	}
	
	/* The static method used to create a shader and return it */
	public static int createShader(String path, boolean external, int shaderType) {
		//The string builder
		StringBuilder builder = new StringBuilder();
		//Append all of the code
		appendCode(path, external, builder);
		//The shader
		int shader = GL20.glCreateShader(shaderType);
		//Load shader
		GL20.glShaderSource(shader, builder.toString());
		GL20.glCompileShader(shader);
		//Check for an error
		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			//Log an error message
			Logger.log("Andor - ShaderUtils createShader()", "Error compiling the shader " + path, Log.ERROR);
			Logger.log("Andor - ShaderInformation", getShaderLogInformation(shader), Log.ERROR);
		}
		//Check to see whether the shader was created
		if (shader == 0)
			//Log an error
			Logger.log("Andor - ShaderUtils createShader()", "Error creating shader " + path, Log.ERROR);
		//Return the shader
		return shader;
	}
	
	/* The static method used to append a shaders code to a string builder */
	public static void appendCode(String path, boolean external, StringBuilder builder) {
		//Get the buffered reader
		BufferedReader reader = FileUtils.getBufferedReader(path, external);
		try {
			//The current line
			String line;
			//Go through the file
			while ((line = reader.readLine()) != null) {
				//Check to see whether this line contains a shader preprocessor
				if (line.contains("#")) {
					//Remove any whitespace on the current line
					line = line.trim();
					//Check the preprocessor
					if (line.startsWith("#include")) {
						//Split up the line
						String[] split = line.split(" ");
						//Append the code in that file at the current position
						appendCode(path.replace(FileUtils.getFile(path).getName(), "") + split[1], external, builder);
					}
				} else
					builder.append(line);
			}
			//Close the reader
			reader.close();
		} catch (IOException e) {
			Logger.error("ShaderUtils appendCode()", "Error while reading the file " + path);
			e.printStackTrace();
		}
	}
	
	/* The static method used to get any log information from a shader */
	public static String getShaderLogInformation(int shader) {
		//Return the information
		return GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH));
	}
	
	/* The static method used to get any log information from a program */
	public static String getProgramLogInformation(int shader) {
		//Return the information
		return GL20.glGetProgramInfoLog(shader, GL20.glGetProgrami(shader, GL20.GL_INFO_LOG_LENGTH));
	}
	
}