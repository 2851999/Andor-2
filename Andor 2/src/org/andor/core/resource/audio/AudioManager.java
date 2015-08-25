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

import org.andor.utils.Log;
import org.andor.utils.Logger;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;

public class AudioManager {
	
	/* All of the created audio sources */
	private static List<AudioSource> sources = new ArrayList<AudioSource>();
	
	/* The audio context and device */
	public static ALDevice device;
	public static ALContext context;
	
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
		try {
			device = ALDevice.create(null);
			context = ALContext.create(device);
		} catch (RuntimeException e) {
			Logger.log("Andor - AudioManager", "A runtime error has occurred, most likely due to not having any audio devices enabled", Log.DEBUG);
		}
	}
	
	/* The static method used to destroy the audio system */
	public static void destroy() {
		if (context != null) {
			context.destroy();
			device.destroy();
		}
	}
	
	/* The static method used to update the volume of all created sources */
	public static void updateVolume() {
		for (int a = 0; a < sources.size(); a++)
			sources.get(a).updateVolume();
	}
	
	/* The static method that returns whether the context has been created */
	public static boolean hasContext() {
		return context != null;
	}
	
}