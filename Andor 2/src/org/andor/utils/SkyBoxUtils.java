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

package org.andor.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SkyBoxUtils {
	
	/* The static method used to generate and return an array of buffered images from one skybox image */
	public static BufferedImage[] getSkyBoxImages(BufferedImage image) {
		//Get the size of each image
		int imageSize = image.getWidth() / 4;
		//Create the array of buffered images
		BufferedImage[] images = new BufferedImage[6];
		//Initialise each image
		for (int a = 0; a < images.length; a++) {
			//Create the buffered image
			images[a] = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_4BYTE_ABGR);
			//Create the graphics instance
			Graphics2D g2d = images[a].createGraphics();
			//Check which image is being written to
			if (a == 0)
				g2d.drawImage(image.getSubimage(imageSize, imageSize, imageSize, imageSize), 0, 0, null);
			else if (a == 1)
				g2d.drawImage(image.getSubimage(imageSize * 3, imageSize, imageSize, imageSize), 0, 0, null);
			else if (a == 2)
				g2d.drawImage(image.getSubimage(0, imageSize, imageSize, imageSize), 0, 0, null);
			else if (a == 3)
				g2d.drawImage(image.getSubimage(imageSize * 2, imageSize, imageSize, imageSize), 0, 0, null);
			else if (a == 4)
				g2d.drawImage(image.getSubimage(imageSize, 0, imageSize, imageSize), 0, 0, null);
			else if (a == 5)
				g2d.drawImage(image.getSubimage(imageSize, imageSize * 2, imageSize, imageSize), 0, 0, null);
		}
		//Return the images
		return images;
	}
	
}