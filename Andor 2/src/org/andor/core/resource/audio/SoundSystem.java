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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.andor.core.Object3D;
import org.andor.core.Vector2f;
import org.andor.core.Vector3f;

public class SoundSystem {
	
	/* The main listener */
	private AudioListener listener;
	
	/* The list of currently playing audio sources */
	private HashMap<String, AudioSource> sources;
	
	/* The list of sources currently playing */
	private List<AudioSource> playing;
	
	/* The default constructor */
	public SoundSystem() {
		this.sources = new HashMap<String, AudioSource>();
		this.playing = new ArrayList<AudioSource>();
	}
	
	/* The methods used to create the listener */
	public void createListener() { this.listener = new AudioListener(); }
	public void createListener(Object3D object) { this.listener = new AudioListener(object); }
	public void createListener(Vector2f position) { this.listener = new AudioListener(position); }
	
	/* The methods used to add an audio source */
	public void addSource(String key, AudioSource source) { this.sources.put(key, source); }
	public void addSource(String key, AudioData data, int type) { this.addSource(key, new AudioSource(data, type)); }
	public void addSoundEffect(String key, AudioData data) { this.addSource(key, data, AudioSource.TYPE_SOUND_EFFECT); }
	public void addMusic(String key, AudioData data) { this.addSource(key, data, AudioSource.TYPE_MUSIC); }
	
	public void addSource(String key, AudioData data, int type, Vector2f position) { this.addSource(key, new AudioSource(data, type, position)); }
	public void addSoundEffect(String key, AudioData data, Vector2f position) { this.addSource(key, data, AudioSource.TYPE_SOUND_EFFECT, position); }
	public void addMusic(String key, AudioData data, Vector2f position) { this.addSource(key, data, AudioSource.TYPE_MUSIC, position); }
	
	public void addSource(String key, AudioData data, int type, Vector3f position) { this.addSource(key, new AudioSource(data, type, position)); }
	public void addSoundEffect(String key, AudioData data, Vector3f position) { this.addSource(key, data, AudioSource.TYPE_SOUND_EFFECT, position); }
	public void addMusic(String key, AudioData data, Vector3f position) { this.addSource(key, data, AudioSource.TYPE_MUSIC, position); }
	
	/* The methods used to play, pause, resume and stop playing audio */
	public void playAsSoundEffect(String key, AudioData data) {
		this.addSoundEffect(key, data);
		this.play(key);
	}
	public void playAsMusic(String key, AudioData data) {
		this.addMusic(key, data);
		this.play(key);
	}
	public void playAsSoundEffect(String key, AudioData data, Vector2f position) {
		this.addSoundEffect(key, data, position);
		this.play(key);
	}
	public void playAsMusic(String key, AudioData data, Vector2f position) {
		this.addMusic(key, data, position);
		this.play(key);
	}
	public void playAsSoundEffect(String key, AudioData data, Vector3f position) {
		this.addSoundEffect(key, data, position);
		this.play(key);
	}
	public void playAsMusic(String key, AudioData data, Vector3f position) {
		this.addMusic(key, data, position);
		this.play(key);
	}
	public void play(String key) {
		AudioSource source = this.getSource(key);
		source.play();
		this.playing.add(source);
	}
	public void pause(String key) { this.getSource(key).pause(); }
	public void resume(String key) { this.getSource(key).resume(); }
	public void stop(String key) {
		AudioSource source = this.getSource(key);
		source.stop();
		this.playing.remove(source);
	}
	
	/* The method used to update the positions of all of the objects in this sound system */
	public void update() {
		//Update the listener
		if (this.listener != null)
			this.listener.update();
		//Go through the sources
		for (int a = 0; a < this.playing.size(); a++) {
			//Check to see whether the source is still playing
			if (this.playing.get(a).isPlaying())
				//Update the source
				this.playing.get(a).update();
			else
				this.playing.remove(a);
		}
	}
	
	/* The method used to get an AudioSource given its key */
	public AudioSource getSource(String key) {
		return this.sources.get(key);
	}
	
}