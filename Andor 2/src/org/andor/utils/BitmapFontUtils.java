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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.andor.core.Colour;
import org.andor.core.resource.texture.Texture;

public class BitmapFontUtils {
	
	/* The static methods used to generate a bitmap font image given a font */
	public static Texture generateBitmapFontTexture(Font font, Colour colour) {
		return Texture.load(generateBitmapFontImage(font, colour));
	}
	
	public static BufferedImage generateBitmapFontImage(Font font, Colour colour) {
		//Calculate the cell size and then assign the font size
		float cellSize = font.getSize2D();
		font = font.deriveFont(cellSize / 1.7f);
		//32 - 127
		//Define the sizes
		int gridSize = 16;
		int startChar = 32;
		int endChar = 127;
		//Get the width
		//Setup the image
		BufferedImage image = new BufferedImage((int) (cellSize * gridSize), (int) (cellSize * gridSize), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = image.createGraphics();
		//Assign the font
		g2d.setFont(font);
		g2d.setColor(new Color(colour.r, colour.g, colour.b, colour.a));
		//Get the font metrics
		FontMetrics metrics = g2d.getFontMetrics(font);
		Rectangle2D bounds = metrics.getStringBounds("" + (char) startChar, g2d);
		float boundsWidth = (float) bounds.getWidth();
		float boundsHeight = (float) bounds.getHeight();
		//Go through each character
		for (int a = startChar; a < endChar; a++) {
			//The current position
			float x = cellSize / 2 - (boundsWidth / 2);
			float y = cellSize / 2 - (boundsHeight / 2);
			//Calculate the current position
			float posX = ((int) a % gridSize) * cellSize;
			//Calculate the cell's y position
			float posY = (float) ((Math.floor((int) a / gridSize)) * cellSize);
			//Render the text
			g2d.drawString("" + (char) a, posX + x, posY + y + g2d.getFontMetrics(font).getAscent());
		}
		//Return the image
		return image;
	}
	
}