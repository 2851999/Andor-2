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
import java.util.List;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALContext;

public class AudioManager {
	
	/* All of the created audio sources */
	private static List<AudioSource> sources = new ArrayList<AudioSource>();
	
	/* The audio context */
	private static ALContext context;
	
	/* The static method used to add an audio source */
	public static void add(AudioSource source) {
		sources.add(source);
	}
	
	/* The static method used to delete all of the audio objects */
	public static void deleteAll() {
		for (int a = 0; a < sources.size(); a++)
			sources.get(a).delete();
	}
	
	/* The static method used to initialise the audio system */
	public static void create() {
		context = ALContext.create();
		context.makeCurrent();
	}
	
	/* The static method used to destroy the audio system */
	public static void destroy() {
		AL.destroy(context);
	}
	
}