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

import org.andor.core.Object3D;
import org.andor.core.Vector2f;
import org.andor.core.Vector3f;
import org.andor.utils.BufferUtils;
import org.lwjgl.openal.AL10;

public class AudioListener extends AudioObject {
	
	/* The default constructor */
	public AudioListener() {}
	
	/* The other constructors */
	public AudioListener(Object3D object) { super(object); }
	public AudioListener(Vector2f position) { super(position); }
	public AudioListener(Vector3f position) { super(position); }
	
	/* The method used to update this listener */
	public void update() {
		if (AudioManager.hasContext()) {
			//Get the needed values
			Vector3f listenerPosition = this.getPosition();
			Vector3f listenerRotation = this.getRotation();
			Vector3f listenerVelocity = this.getVelocity();
	        float xDirection = 1f * (float) Math.sin(Math.toRadians(listenerRotation.y));
	        float zDirection = -1f * (float) Math.cos(Math.toRadians(listenerRotation.y));
			//Setup OpenAL
			AL10.alListener3f(AL10.AL_POSITION, -listenerPosition.x, -listenerPosition.y, -listenerPosition.z);
			AL10.alListener3f(AL10.AL_VELOCITY, listenerVelocity.x, listenerVelocity.y, listenerVelocity.z);
			AL10.alListenerfv(AL10.AL_ORIENTATION, BufferUtils.createFlippedBuffer(new float[] { xDirection, 0, zDirection, 0, 1f, 0 }));
		}
	}
	
}