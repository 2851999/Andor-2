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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	/* The static method used to get the file path of a file */
	public static String getPath(String file) {
		return new File(file).getPath().toString();
	}
	
	/* The static method used to get a file */
	public static File getFile(String path) {
		return new File(getPath(path));
	}
	
	/* The static method used to check a file exists */
	public static boolean doesExist(String path) {
		return getFile(path).exists();
	}
	
	/* The static method used to check a file is a file */
	public static boolean isFile(String path) {
		return getFile(path).isFile();
	}
	
	/* The static method used to check if a file is a directory */
	public static boolean isDirectory(String path) {
		return getFile(path).isDirectory();
	}
	
	/* The static method used to check if a file can be read */
	public static boolean canRead(String path) {
		return getFile(path).canRead();
	}
	
	/* The static method used to check if a file can be written */
	public static boolean canWrite(String path) {
		return getFile(path).canWrite();
	}
	
	/* The static method used to delete a file and return whether it was successful */
	public static boolean delete(String path) {
		return getFile(path).delete();
	}
	
	/* The static method used to crate a directory and return whether it was successful */
	public static boolean createDirectory(String path) {
		return getFile(path).mkdir();
	}
	
	/* The static method used to crate a directory and return whether it was successful */
	public static boolean createDirectories(String path) {
		return getFile(path).mkdirs();
	}
	
	/* The static method used to get an input stream for a file and return it */
	public static InputStream getInputStream(String path, boolean external) {
		//Catch any errors
		try {
			//Check the external value
			if (external)
				return new FileInputStream(getPath(path));
			else
				return FileUtils.class.getResourceAsStream(path);
		} catch (FileNotFoundException e) {
			//Log an error
			Logger.log("Andor - FileUtils getInputStream()", "File not found: " + path);
			e.printStackTrace();
		}
		//An error occurred so return nothing
		return null;
	}
	
	/* The static method used to get a buffered reader for a file and return it */
	public static BufferedReader getBufferedReader(String path, boolean external) {
		//Catch any errors
		try {
			//Check to see whether the file is external from the project
			if (external) {
				//Get the file
				File file = getFile(path);
				//Make sure the file can be read
				if (file.exists()) {
					if (file.isFile()) {
						if (file.canRead()) {
							//Get the file reader instance
							FileReader fileReader = new FileReader(getPath(path));
							//Create the buffered reader for the file reader
							return new BufferedReader(fileReader);
						} else
							Logger.log("Andor - FileUtils getBufferedReader()" , "Cannot read the file with the path: " + path, Log.ERROR);
					} else
						Logger.log("Andor - FileUtils getBufferedReader()" , "The path is not a file: " + path, Log.ERROR);
				} else
					Logger.log("Andor - FileUtils getBufferedReader()" , "The path does not exist: " + path, Log.ERROR);
			} else {
				//Get the file reader instance
				InputStreamReader fileReader = new InputStreamReader(FileUtils.class.getResourceAsStream(path));
				//Create the buffered reader and return it
				return new BufferedReader(fileReader);
			}
		} catch (Exception e) {
			Logger.log("Andor - FileUtils getBufferedReader()" , "There was an exception when reading the file: " + path, Log.ERROR);
			e.printStackTrace();
		}
		//Something went wrong so return nothing
		return null;
	}
	
	/* The static method used to get a buffered reader for a file and return it */
	public static BufferedWriter getBufferedWriter(String path) {
		//Catch any errors
		try {
			//Get the file
			File file = getFile(path);
			//Make sure the file can be written (e.g. Has the right access rights), if it is already
			//there, otherwise allow it anyway
			if (file.canWrite() || ! file.exists()) {
				//Create the file writer
				FileWriter fileWriter = new FileWriter(getPath(path));
				//Create the buffered writer
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				//Return the buffered writer
				return bufferedWriter;
			} else {
				//Log an error
				Logger.log("Andor - FileUtils getBufferedWriter()" , "The file can not be written (Does it have the right access permissions?): " + path, Log.ERROR);
			}
		} catch (IOException e) {
			//Log an error
			Logger.log("Andor - FileUtils getBufferedWriter()" , "There was an exception when writing the file: " + path, Log.ERROR);
			e.printStackTrace();
		}
		//Something went wrong so return nothing
		return null;
	}
	
	/* The static method used to read a file's text given
	 * the buffered reader */
	public static List<String> read(BufferedReader reader) {
		//The file text
		List<String> fileText = new ArrayList<String>();
		try {
		//The current line
		String currentLine = "";
		//Go through the file
		while ((currentLine = reader.readLine()) != null)
			//Add the current line to the file text
			fileText.add(currentLine);
		} catch (IOException e) {
			Logger.log("Andor - FileUtils read()" , "An exception has occured", Log.ERROR);
		}
		//Return the file text
		return fileText;
	}
	
	/* The static method used to read a file's text given
	 * the file's path and whether it is in a folder */
	public static List<String> read(String path, boolean external) {
		//The text
		List<String> text = null;
		//Catch any errors
		try {
			//Get the reader
			BufferedReader reader = getBufferedReader(path, external);
			//Read the text
			text = read(reader);
			//Close the file
			reader.close();
		} catch (IOException e) {
			//Log an error
			Logger.log("Andor - FileUtils read()" , "There was an exception when reading the file: " + path + " (InputStream couldn't be closed)", Log.ERROR);
			e.printStackTrace();
		}
		//Return the file text
		return text;
	}
	
	/* The static method used to read an internal text file */
	public static List<String> readInternal(String path) {
		return read(path, false);
	}
	
	/* The static method used to read an external text file */
	public static List<String> readExternal(String path) {
		return read(path, true);
	}
	
	/* The static method used to write a text file given the BufferedWriter and file text */
	public static void write(BufferedWriter bufferedWriter, String[] fileText) throws IOException {
		//Make sure there is text to save (Prevents IndexOutOfBoundsException)
		if (fileText.length > 0) {
			//Write the first line
			bufferedWriter.write(fileText[0]);
			//Go through each line of the file text
			for (int a = 1; a < fileText.length; a++) {
				//Start a new line
				bufferedWriter.newLine();
				//Write the current line
				bufferedWriter.write(fileText[a]);
			}
		}
	}
	
	/* The static method used to write a text file */
	public static void write(String path, String[] fileText) {
		//Catch any errors
		try {
			//Get the file
			File file = getFile(path);
			//Make sure the file can be written (e.g. Has the right access rights), if it is already
			//there, otherwise allow it anyway
			if (file.canWrite() || ! file.exists()) {
				//Create the file writer
				FileWriter fileWriter = new FileWriter(getPath(path));
				//Create the buffered writer
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				//Write the file
				write(bufferedWriter, fileText);
				//Close the buffered writer
				bufferedWriter.close();
				//Close the file writer
				fileWriter.close();
			} else {
				//Log an error
				Logger.log("Andor - FileUtils write()" , "The file can not be written (Does it have the right access permissions?): " + path, Log.ERROR);
			}
		} catch (Exception e) {
			//Log an error
			Logger.log("Andor - FileUtils write()" , "There was an exception when writing the file: " + path, Log.ERROR);
			e.printStackTrace();
		}
	}
	
	/* The static method used to copy a file and return whether it was successful */
	public static boolean copy(String copyFrom, String copyTo) {
		//Catch any errors
		try {
			//Copy the file
			Files.copy(getFile(copyFrom).toPath(), getFile(copyTo).toPath(), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			//Log an error
			Logger.log("Andor - FileUtils copy()" , "There was an exception when copying the file from " + copyFrom + " to " + copyTo, Log.ERROR);
			e.printStackTrace();
			return false;
		}
	}
	
	/* The static method used to move a file and return whether it was successful */
	public static boolean move(String moveFrom, String moveTo) {
		//Catch any errors
		try {
			//Copy the file
			Files.move(getFile(moveFrom).toPath(), getFile(moveTo).toPath(), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			//Log an error
			Logger.log("Andor - FileUtils move()" , "There was an exception when moving the file from " + moveFrom + " to " + moveTo, Log.ERROR);
			e.printStackTrace();
			return false;
		}
	}
	
	/* The static method to copy a directory and its contents and return whether it was successful */
	public static boolean copyDirectory(String copyFrom, String copyTo) {
		//The boolean that states whether the operation was successful
		boolean wasSuccessful = true;
		//The list's that contains all of the files and directories in the directory
		List<String> files = new ArrayList<String>();
		List<String> directories = new ArrayList<String>();
		//Add all of the files and directories to the lists
		listAll(copyFrom, "", files, directories);
		//Make all of the needed directories
		for (int a = 0; a < directories.size(); a++)
			//Create the current directory
			wasSuccessful = createDirectories(copyTo + directories.get(a));
		//Try and copy all of the files
		for (int a = 0; a < files.size(); a++)
			//Copy the current file
			wasSuccessful = copy(copyFrom + files.get(a), copyTo + files.get(a));
		//Return whether the copy went successfully
		return wasSuccessful;
	}
	
	/* The static method to move a directory and its contents and return whether it was successful */
	public static boolean moveDirectory(String moveFrom, String moveTo) {
		//The boolean that states whether the operation was successful
		boolean wasSuccessful = true;
		//The list's that contains all of the files and directories in the directory
		List<String> files = new ArrayList<String>();
		List<String> directories = new ArrayList<String>();
		//Add all of the files and directories to the lists
		listAll(moveFrom, "", files, directories);
		//Make all of the needed directories
		for (int a = 0; a < directories.size(); a++)
			//Create the current directory
			wasSuccessful = createDirectories(moveTo + directories.get(a));
		//Try and move all of the files
		for (int a = 0; a < files.size(); a++)
			//Copy the current file
			wasSuccessful = move(moveFrom + files.get(a), moveTo + files.get(a));
		//Return whether the move went successfully
		return wasSuccessful;
	}
	
	/* The static method to delete a directory and its contents and return whether it was successful */
	public static boolean deleteDirectory(String path) {
		//The boolean that states whether the operation was successful
		boolean wasSuccessful = true;
		//The list's that contains all of the files and directories in the directory
		List<String> files = new ArrayList<String>();
		List<String> directories = new ArrayList<String>();
		//Add all of the files and directories to the lists
		listAll(path, "", files, directories);
		//Try and delete all of the files
		for (int a = 0; a < files.size(); a++)
			//Delete the current file
			wasSuccessful = delete(path + files.get(a));
		//Delete all of the directories (Backwards as directories
		//can't be deleted with anything inside of them, even other
		//directories
		for (int a = directories.size() - 1; a >= 0; a--)
			//Delete the current directory
			wasSuccessful = delete(path + directories.get(a));
		//Delete the directory itself
		wasSuccessful = delete(path);
		//Return whether the move went successfully
		return wasSuccessful;
	}
	
	/* The static method used to add all of the file, and folder paths to a list */
	public static void listAll(String originalFolderPath, String folderPath, List<String> files, List<String> directories) {
		//List all of the files in the current directory
		String[] filesInCurrentDir = list(originalFolderPath + folderPath);
		//Look at all of the files in the current directory
		for (int a = 0; a < filesInCurrentDir.length; a++) {
			//Get the current file path
			String filePath = folderPath + "/" + filesInCurrentDir[a];
			//Check to see whether the current file is a file or a directory
			if (isDirectory(originalFolderPath + "/" + filePath)) {
				//Add the current directory to the directories list
				directories.add(filePath);
				//Another directory has been found, so recall this method on the newly found directory
				listAll(originalFolderPath, filePath, files, directories);
			} else
				//A file has been found so add it to the files list
				files.add(filePath);
		}
	}
	
	/* The static method used to list all the files and folders in a directory */
	public static String[] list(String path) {
		//Return the list of files in the specified path
		return getFile(path).list();
	}
	
}