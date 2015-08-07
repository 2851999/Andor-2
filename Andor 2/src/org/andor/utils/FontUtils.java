/* *********************************************
 * ANDOR
 * 
 * USE - EDUCATIONAL PURPOSES ONLY
 *
 * COPYRIGHT @ 2014-2015
 **********************************************/

package org.andor.utils;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.andor.core.BitmapText;
import org.andor.core.Colour;
import org.andor.core.Font;
import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureLoader;

public class FontUtils {
	
	/* The static static method used to load a bitmap font */
	public static Font loadBitmapFont(String imagePath, boolean external, int gridSize, int fontSize, Colour[] colour) {
		return new Font(new BitmapText(TextureLoader.load(imagePath, external), gridSize, fontSize, colour));
	}
	
	/* The static static method used to load a bitmap font */
	public static Font loadBitmapFont(String imagePath, boolean external, int gridSize, int fontSize, Colour colour) {
		return new Font(new BitmapText(TextureLoader.load(imagePath, external), gridSize, fontSize, colour));
	}
	
	/* The static static method used to load a bitmap font */
	public static Font loadBitmapFont(String imagePath, boolean external, int gridSize, int fontSize) {
		return new Font(new BitmapText(TextureLoader.load(imagePath, external), gridSize, fontSize));
	}
	
	/* The static method used to load a bitmap font assumes the grid size is 16 */
	public static Font loadBitmapFont(String imagePath, boolean external, int fontSize, Colour[] colour) { 
		return loadBitmapFont(imagePath, external, 16, fontSize, colour);
	}
	
	/* The static method used to load a bitmap font assumes the external value is false and the grid size is 16 */
	public static Font loadBitmapFont(String imagePath, int fontSize, Colour[] colour) { 
		return loadBitmapFont(imagePath, false, fontSize, colour);
	}
	
	/* The static method used to load a bitmap font assumes the grid size is 16 */
	public static Font loadBitmapFont(String imagePath, boolean external, int fontSize, Colour colour) { 
		return loadBitmapFont(imagePath, external, 16, fontSize, colour);
	}
	
	/* The static method used to load a bitmap font assumes the external value is false and the grid size is 16 */
	public static Font loadBitmapFont(String imagePath, int fontSize, Colour colour) { 
		return loadBitmapFont(imagePath, false, fontSize, colour);
	}
	
	/* The static method used to load a bitmap font assumes the grid size is 16 */
	public static Font loadBitmapFont(String imagePath, boolean external, int fontSize) { 
		return loadBitmapFont(imagePath, external, 16, fontSize);
	}
	
	/* The static method used to load a bitmap font assumes the external value is false and the grid size is 16 */
	public static Font loadBitmapFont(String imagePath, int fontSize) { 
		return loadBitmapFont(imagePath, false, fontSize);
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String font, Colour colour, float fontSize, int gridSize) {
		return new Font(new BitmapText(generateBitmapFontImage(createFont(font, fontSize)), gridSize, fontSize, colour));
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String font, Colour[] colours, float fontSize, int gridSize) {
		return new Font(new BitmapText(generateBitmapFontImage(createFont(font, fontSize)), gridSize, fontSize, colours));
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String font, Colour colour, float fontSize) {
		return createBitmapFont(font, colour, fontSize, 16);
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String font, Colour[] colours, float fontSize) {
		return createBitmapFont(font, colours, fontSize, 16);
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String font, float fontSize) {
		return createBitmapFont(font, Colour.WHITE, fontSize, 16);
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String path, boolean external, Colour[] colours, float fontSize, int gridSize) {
		return new Font(new BitmapText(generateBitmapFontImage(createFont(path, external, fontSize)), gridSize, fontSize, colours));
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String path, boolean external, Colour[] colours, float fontSize) {
		return createBitmapFont(path, external, colours, fontSize, 16);
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String path, boolean external, Colour colour, float fontSize, int gridSize) {
		return new Font(new BitmapText(generateBitmapFontImage(createFont(path, external, fontSize)), gridSize, fontSize, colour));
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String path, boolean external, Colour colour, float fontSize) {
		return createBitmapFont(path, external, colour, fontSize, 16);
	}
	
	/* The static method used to create a bitmap front given the name of a font */
	public static Font createBitmapFont(String path, boolean external, float fontSize) {
		return createBitmapFont(path, external, Colour.WHITE, fontSize, 16);
	}
	
	/* The static method used to generate an texture for a bitmap font */
	public static Texture generateBitmapFontImage(java.awt.Font font) {
		//Return the texture
		return BitmapFontUtils.generateBitmapFontTexture(font, Colour.WHITE);
	}
	
	/* The static method used to generate an texture for a bitmap font */
	public static Texture generateBitmapFontImage(java.awt.Font font, Colour colour) {
		//Return the texture
		return BitmapFontUtils.generateBitmapFontTexture(font, colour);
	}
	
	/* The static method to create a font */
	public static java.awt.Font createFont(String font, float size) {
		return new java.awt.Font(font , java.awt.Font.PLAIN , (int) size);
	}
	
	/* The static method to create a font from a file */
	public static java.awt.Font createFont(String path, boolean external, float size) {
		return createFont(path, external).deriveFont(size);
	}
	
	/* The static method to create a font from a file */
	public static java.awt.Font createFont(String path, boolean external) {
		//Default font
		java.awt.Font font = createFont("Arial" , 12);
		try {
			//Load font from file path
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, FileUtils.getInputStream(path, external));
		} catch (FileNotFoundException e) {
			Logger.log("FontUtils getFont()", "File not found " + path, Log.ERROR);
			e.printStackTrace();
		} catch (FontFormatException e) {
			Logger.log("FontUtils getFont()", "Font format exception " + path, Log.ERROR);
			e.printStackTrace();
		} catch (IOException e) {
			Logger.log("FontUtils getFont()", "IOException " + path, Log.ERROR);
			e.printStackTrace();
		}
		return font;
	}
	
}