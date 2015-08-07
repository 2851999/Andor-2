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

package org.andor.core.resource;

import org.andor.core.render.RenderData;
import org.andor.core.resource.audio.AudioData;
import org.andor.core.resource.audio.AudioLoader;
import org.andor.core.resource.audio.AudioManager;
import org.andor.core.resource.shader.Shader;
import org.andor.core.resource.shader.ShaderManager;
import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureLoader;
import org.andor.core.resource.texture.TextureManager;
import org.andor.core.resource.texture.TextureParameters;
import org.andor.utils.ShaderUtils;

public class ResourceManager {
	
	/* The path this resource manager loads resources from */
	private String path;
	
	/* The other paths */
	private String shadersPath;
	private String texturesPath;
	private String audioPath;
	
	/* The boolean that states whether this resource manager loads externally */
	private boolean external;
	
	/* The constructor */
	public ResourceManager(String path, boolean external) {
		this.path = path;
		this.external = external;
		this.shadersPath = "";
		this.texturesPath = "";
		this.audioPath = "";
	}
	
	/* The method used to load and return a shader resource */
	public Shader loadShader(String path) {
		//Return the shader
		return ShaderUtils.createShader(this.path + this.shadersPath + "/" + path, this.external);
	}
	
	/* The methods used to load and return a texture resource */
	public Texture loadTexture(String path) {
		//Return the texture
		return TextureLoader.load(this.path + this.texturesPath + "/" + path, this.external);
	}
	
	public Texture loadTexture(String path, TextureParameters parameters) {
		//Return the texture
		return TextureLoader.load(this.path + this.texturesPath + "/" + path, this.external, parameters);
	}
	
	/* The methods used to load and return an audio resource */
	public AudioData loadAudio(String path) {
		//Return the audio
		return AudioLoader.load(this.path + this.audioPath + "/" + path, this.external);
	}
	
	/* The setters and getters */
	public void setPath(String path) { this.path = path; }
	public void setShadersPath(String shadersPath) { this.shadersPath = shadersPath; }
	public void setTexturesPath(String texturesPath) { this.texturesPath = texturesPath; }
	public void setAudioPath(String audioPath) { this.audioPath = audioPath; }
	public void setExternal(boolean external) { this.external = external; }
	public String getPath() { return this.path; }
	public String getShadersPath() { return this.shadersPath; }
	public String getTexturesPath() { return this.texturesPath; }
	public String getAudioPath() { return this.audioPath; }
	public boolean getExternal() { return this.external; }
	
	/* The static method used to free all resources */
	public static void deleteAll() {
		ShaderManager.deleteAll();
		TextureManager.deleteAll();
		AudioManager.deleteAll();
		RenderData.deleteAll();
	}
	
}