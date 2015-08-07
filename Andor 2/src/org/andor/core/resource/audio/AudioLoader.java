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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.andor.utils.FileUtils;
import org.andor.utils.Log;
import org.andor.utils.Logger;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggDecoder;
import org.newdawn.slick.openal.WaveData;

public class AudioLoader {
	
	/* The static method used to load an audio file */
	public static AudioData load(String path, boolean external) {
		if (path.endsWith(".ogg"))
			return new AudioData(loadOgg(path, external));
		else if (path.endsWith(".wav"))
			return new AudioData(loadWave(path, external));
		else {
			Logger.log("Andor - AudioLoader load()", "Unknown file type of the file " + path, Log.ERROR);
			return null;
		}
	}
	
	/* The static method used to load a ogg vorbis audio file */
	public static OggData loadOgg(String path, boolean external) {
		//Catch any errors
		try {
			//Get the input stream
			InputStream is = FileUtils.getInputStream(path, external);
			//Get the data
			OggData data = new OggDecoder().getData(is);
			//Close the input stream
			is.close();
			//Return the data
			return data;
		} catch (IOException e) {
			Logger.log("AudioLoader loadOgg()", "Failed to load the file " + path, Log.ERROR);
			e.printStackTrace();
		}
		//An error has occurred so return nothing
		return null;
	}
	
	/* The static method used to load a wave audio file */
	public static WaveData loadWave(String path, boolean external) {
		//Catch any errors
		try {
			//The input stream
			BufferedInputStream is = new BufferedInputStream(FileUtils.getInputStream(path, external));
			//Get the data
			WaveData data = WaveData.create(is);
			//Close the stream
			is.close();
			//Return the data
			return data;
		} catch (IOException e) {
			Logger.log("AudioLoader loadWave()", "Failed to load the file " + path, Log.ERROR);
			e.printStackTrace();
		}
		//An error has occurred so return nothing
		return null;
	}
	
}