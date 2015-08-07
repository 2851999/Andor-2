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

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.andor.core.Colour;
import org.andor.core.Vector2f;
import org.andor.core.Vector3f;
import org.andor.utils.Log;
import org.andor.utils.Logger;
import org.andor.utils.ShaderUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
	
	/* The program and the shaders */
	private int program;
	private int vertexShader;
	private int fragmentShader;
	
	/* The map of uniforms */
	private HashMap<String, Integer> uniforms;
	
	/* The map of attributes */
	private HashMap<String, Integer> attributes;
	
	/* The constructor */
	public Shader(int vertexShader, int fragmentShader) {
		//Add this shader to the list of shaders
		ShaderManager.add(this);
		//Setup this shader
		this.program = GL20.glCreateProgram();
		this.vertexShader = vertexShader;
		this.fragmentShader = fragmentShader;
		this.uniforms = new HashMap<String, Integer>();
		this.attributes = new HashMap<String, Integer>();
		//Attach the shaders
		this.attach(this.vertexShader);
		this.attach(this.fragmentShader);
	}
	
	/* The method used to attach a shader */
	public void attach(int shader) {
		//Attach the shader to the program
		GL20.glAttachShader(this.program, shader);
		GL20.glLinkProgram(this.program);
		//Check for an error
		if (GL20.glGetProgrami(this.program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			//Log an error message
			Logger.log("Andor - ShaderUtils createShader()", "Error linking the shader", Log.ERROR);
			Logger.log("Andor - ShaderInformation", ShaderUtils.getProgramLogInformation(this.program), Log.ERROR);
		}
		//Validate the program
		GL20.glValidateProgram(this.program);
	}
	
	/* The methods used to bind and unbind this shader */
	public void bind() { GL20.glUseProgram(this.program); }
	public void unbind() { GL20.glUseProgram(0); }
	
	/* The method used to delete this shader */
	public void delete() {
		GL20.glDeleteShader(this.vertexShader);
		GL20.glDeleteShader(this.fragmentShader);
		GL20.glDeleteProgram(this.program);
	}
	
	/* The method used to detach a shader */
	public void detach(int shader) {
		//Detach the shader from the program
		GL20.glDetachShader(this.program , shader);
	}
	
	/* The method used to add a uniform */
	public void addUniform(String key, String name) {
		//Add the uniform to the hash map
		this.uniforms.put(key, GL20.glGetUniformLocation(this.program, name));
	}
	
	/* The method used to add an attribute */
	public void addAttribute(String key, String name) {
		//Add the attribute to the hash map
		this.attributes.put(key, GL20.glGetAttribLocation(this.program, name));
	}
	
	/* The methods used to set specific values in this shader */
	public void setUniformf(String variableName, float v1) {
		GL20.glUniform1f(this.getUniformLocation(variableName), v1);
	}
	
	public void setUniformf(String variableName, float v1, float v2) {
		GL20.glUniform2f(this.getUniformLocation(variableName), v1, v2);
	}
	
	public void setUniformf(String variableName, float v1, float v2, float v3) {
		GL20.glUniform3f(this.getUniformLocation(variableName), v1, v2, v3);
	}
	
	public void setUniformf(String variableName, float v1, float v2, float v3, float v4) {
		GL20.glUniform4f(this.getUniformLocation(variableName), v1, v2, v3, v4);
	}
	
	public void setUniformf(String variableName, Vector2f v) {
		GL20.glUniform2f(this.getUniformLocation(variableName), v.x, v.y);
	}
	
	public void setUniformf(String variableName, Vector3f v) {
		GL20.glUniform3f(this.getUniformLocation(variableName), v.x, v.y, v.z);
	}
	
	public void setUniformf(String variableName, Colour colour) {
		setUniformf(variableName, colour.r, colour.g, colour.b, colour.a);
	}
	
	public void setUniformi(String variableName, int v1) {
		GL20.glUniform1i(this.getUniformLocation(variableName), v1);
	}
	
	public void setUniformi(String variableName, int v1, int v2) {
		GL20.glUniform2i(this.getUniformLocation(variableName), v1, v2);
	}
	
	public void setUniformi(String variableName, int v1, int v2, int v3) {
		GL20.glUniform3i(this.getUniformLocation(variableName), v1, v2, v3);
	}
	
	public void setUniformi(String variableName, IntBuffer values) {
		GL20.glUniform1iv(this.getUniformLocation(variableName), values);
	}
	
	public void setUniformf(String variableName, FloatBuffer values) {
		GL20.glUniform1fv(this.getUniformLocation(variableName), values);
	}
	public void setUniformMatrix4fv(String variableName, FloatBuffer values) {
		GL20.glUniformMatrix4fv(this.getUniformLocation(variableName), false, values);
	}
	
	public int getUniformLocation(String name) {
		return this.uniforms.get(name);
	}
	
	public int getAttributeLocation(String name) {
		return this.attributes.get(name);
	}
	
}