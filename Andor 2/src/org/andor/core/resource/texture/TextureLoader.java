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

package org.andor.core.resource.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.andor.utils.FileUtils;
import org.andor.utils.Log;
import org.andor.utils.Logger;

public class TextureLoader {
	
	/* The static methods used to load a texture and return it */
	public static Texture load(String path, boolean external) {
		return load(path, external, TextureParameters.DEFAULT);
	}
	
	public static Texture load(String path, boolean external, TextureParameters parameters) {
		return load(path, external, parameters, true);
	}
	
	public static Texture load(String path, boolean external, TextureParameters parameters, boolean applyParameters) {
		return Texture.load(loadBufferedImage(path, external), parameters, applyParameters);
	}
	
	public static BufferedImage loadBufferedImage(String path, boolean external) {
		//Catch any errors
		try {
			return ImageIO.read(FileUtils.getInputStream(path, external));
		} catch (IOException e) {
			Logger.log("Andor - TextureLoader loadBufferedImage()", "An error has occurred while loading the image " + path, Log.ERROR);
			e.printStackTrace();
		}
		//An error has occurred so return nothing
		return null;
	}
	
	/* The static methods used to write a texture */
	public static void writeBufferedImage(String path, String format, BufferedImage image) {
		//Catch any errors
		try {
			ImageIO.write(image, format, FileUtils.getFile(path));
		} catch (IOException e) {
			Logger.log("Andor - TextureLoader writeBufferedImage()", "An error has occurred while writing the image " + path, Log.ERROR);
			e.printStackTrace();
		}
	}
	
}