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

package org.andor.core;

import org.andor.utils.ScreenUtils;

public class ScreenResolution {
	
	/* Some default resolutions that can be used */
	public static final ScreenResolution RES_NATIVE = new ScreenResolution(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
	public static final ScreenResolution RES_640x480 = new ScreenResolution(640, 480);
	public static final ScreenResolution RES_800x600 = new ScreenResolution(800, 600);
	public static final ScreenResolution RES_1280x720 = new ScreenResolution(1280, 720);
	public static final ScreenResolution RES_1366x768 = new ScreenResolution(1366, 768);
	public static final ScreenResolution RES_1920x1080 = new ScreenResolution(1920, 1080);
	public static final ScreenResolution RES_2560x1440 = new ScreenResolution(2560, 1440);
	public static final ScreenResolution RES_3840x2160 = new ScreenResolution(3840, 2160);
	public static final ScreenResolution RES_720P = RES_1280x720;
	public static final ScreenResolution RES_1080P = RES_1920x1080;
	public static final ScreenResolution RES_1440P = RES_2560x1440;
	public static final ScreenResolution RES_4K = RES_3840x2160;
	
	/* The width and height of this resolution */
	private int width;
	private int height;
	
	/* The constructor */
	public ScreenResolution(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/* The setters and getters */
	public void set(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	
	/* Other utility methods */
	public String toString() { return "(" + this.width + "," + this.height + ")"; }
	
}