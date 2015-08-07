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

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardUtils {
	
	/* The static method to get text off of the clip board */
	public static String getText() {
		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException e) {
			//Log an error
			Logger.log("Andor - ClipboardUtils getText()", "An exception has occurred when getting text from  the system clipboard", Log.ERROR);
			e.printStackTrace();
		} catch (UnsupportedFlavorException e) {
			//Log an error
			Logger.log("Andor - ClipboardUtils getText()", "An exception has occurred when getting text from  the system clipboard", Log.ERROR);
			e.printStackTrace();
		} catch (IOException e) {
			//Log an error
			Logger.log("Andor - ClipboardUtils getText()", "An exception has occurred when getting text from  the system clipboard", Log.ERROR);
			e.printStackTrace();
		}
		return "";
	}
	
	/* The static method to set the text of the clip board */
	public static void setText(String text) {
		//Set the contents of the clip board
		StringSelection selection = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
	}
	
}