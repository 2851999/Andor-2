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

package org.andor.gui;

public interface GUIComponentListener {
	
	/* The method called when the mouse enters a gui component */
	public void onMouseEnter(GUIComponent component);
	
	/* The method called when the mouse exits a gui component */
	public void onMouseLeave(GUIComponent component);
	
	/* The method called when a gui component is clicked */
	public void onClicked(GUIComponent component);
	
}