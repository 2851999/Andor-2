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

import javax.swing.JOptionPane;

public class MessageBox {
		
	/* The static method used to show an information box */
	public static void showInformationMessage(String title , String message) {
		JOptionPane.showMessageDialog(null , message , title , JOptionPane.INFORMATION_MESSAGE);
	}
	
	/* The static method used to show a warning box */
	public static void showWarningMessage(String title , String message) {
		JOptionPane.showMessageDialog(null , message , title , JOptionPane.WARNING_MESSAGE);
	}
	
	/* The static method used to show an error box */
	public static void showErrorMessage(String title , String message) {
		JOptionPane.showMessageDialog(null , message , title , JOptionPane.ERROR_MESSAGE);
	}
	
	/* The static method used to show a yes/no option and returns whether the answer was yes */
	public static boolean showYesNoOption(String title , String message) {
		return JOptionPane.showConfirmDialog(null , message , title , JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
	}
	
	/* The static method used to get input from the user */
	public static String getInput(String message) {
		return JOptionPane.showInputDialog(message);
	}
	
}