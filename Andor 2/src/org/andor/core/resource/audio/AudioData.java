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

import java.nio.ByteBuffer;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.WaveData;

public class AudioData {
	
	/* The data */
	private WaveData wave;
	private OggData ogg;
	
	/* The constructors */
	public AudioData(WaveData wave) {
		this.wave = wave;
	}
	
	public AudioData(OggData ogg) {
		this.ogg = ogg;
	}
	
	/* The method used to dispose of any stored data */
	public void dispose() {
		if (wave != null)
			wave.dispose();
	}
	
	/* The methods used to get data from the audio */
	public int getFormat() {
		if (ogg != null)
			return ogg.channels > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
		else if (wave != null)
			return wave.format;
		else
			return -1;
	}
	
	public ByteBuffer getData() {
		if (ogg != null)
			return ogg.data;
		else if (wave != null)
			return wave.data;
		else
			return null;
	}
	
	public int getSampleRate() {
		if (ogg != null)
			return ogg.rate;
		else if (wave != null)
			return wave.samplerate;
		else
			return -1;
	}
	
}