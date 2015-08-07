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

package org.andor.core.resource.audio;

import org.andor.core.Vector3f;
import org.lwjgl.openal.AL10;

public class AudioSource extends AudioObject {
	
	/* The various pieces of data used to generate and play sounds */
	private int sourceHandle;
	private int bufferHandle;
	
	public float pitch;
	public float gain;
	
	public boolean loop;
	
	/* The constructor */
	public AudioSource(AudioData data) {
		//Setup the variables
		this.sourceHandle = AL10.alGenSources();
		this.bufferHandle = AL10.alGenBuffers();
		this.pitch = 1.0f;
		this.gain = 1.0f;
		this.loop = false;
		//Setup OpenAL
		AL10.alBufferData(this.bufferHandle, data.getFormat(), data.getData(), data.getSampleRate());
		//Dispose of the data
		data.dispose();
		//Setup the source
		AL10.alSourcei(this.sourceHandle, AL10.AL_BUFFER, this.bufferHandle);
		//Add this to the list of audio resources
		AudioManager.add(this);
	}
	
	/* The method used to update this source */
	public void update() {
		//Get all of the needed values
		Vector3f sourcePosition = this.getPosition();
		//Vector3f sourceRotation = this.getRotation();
		Vector3f sourceVelocity = this.getVelocity();
		//Update all of the source values
		AL10.alSource3f(this.sourceHandle, AL10.AL_POSITION, sourcePosition.x, sourcePosition.y, sourcePosition.z);
		//AL10.alSource3f(this.sourceHandle, AL10.AL_ORIENTATION, sourceRotation.x, sourceRotation.y, sourceRotation.z);
		AL10.alSource3f(this.sourceHandle, AL10.AL_VELOCITY, sourceVelocity.x, sourceVelocity.y, sourceVelocity.z);
		AL10.alSourcef(this.sourceHandle, AL10.AL_PITCH, this.pitch);
		AL10.alSourcef(this.sourceHandle, AL10.AL_GAIN, this.gain);
		if (this.loop)
			AL10.alSourcei(this.sourceHandle, AL10.AL_LOOPING, AL10.AL_TRUE);
		else
			AL10.alSourcei(this.sourceHandle, AL10.AL_LOOPING, AL10.AL_FALSE);
	}
	
	/* Various source related methods */
	public void play() {
		AL10.alSourcePlay(this.sourceHandle);
	}
	
	public void stop() {
		AL10.alSourceStop(this.sourceHandle);
	}
	
	public void pause() {
		AL10.alSourcePause(this.sourceHandle);
	}
	
	public void resume() {
		AL10.alSourcePlay(this.sourceHandle);
	}
	
	public void delete() {
		AL10.alDeleteSources(this.sourceHandle);
		AL10.alDeleteBuffers(this.bufferHandle);
	}
	
	/* The setters and getters */
	public void setPitch(float pitch) { this.pitch = pitch; }
	public void setGain(float gain) { this.gain = gain; }
	public void setLoop(boolean loop) { this.loop = loop; }
	public float getPitch() { return this.pitch; }
	public float getGain() { return this.gain; }
	public boolean doesLoop() { return this.loop; }
	
}