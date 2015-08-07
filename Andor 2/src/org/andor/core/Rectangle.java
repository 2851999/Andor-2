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

public class Rectangle {

	/* The rectangles x position */
	public float x;

	/* The rectangles y position */
	public float y;

	/* The rectangles width */
	public float width;

	/* The rectangles height */
	public float height;

	/* The constructor */
	public Rectangle() {
		//Set the variables
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}

	/* The constructor with the position, width and height given */
	public Rectangle(float x , float y , float width , float height) {
		//Set the variables
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/* The method to check whether a point is contained within the rectangle */
	public boolean contains(float x , float y) {
		//Return whether the coordinates are contained within this rectangle
		return (x > this.x && y > this.y && x < this.x + this.width && y < this.y + this.height);
	}
	
	/* The method to check whether a point is intersects the rectangle */
	public boolean intersects(float x , float y) {
		//Return whether the coordinates are intersect this rectangle
		return (x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + this.height);
	}

	/* The method to check whether another rectangle intersects this rectangle */
	public boolean intersects(Rectangle other) {
		//Check if the rectangle's points are contained in this rectangle
		if (this.intersects(other.x , other.y)
				|| this.intersects(other.x + other.width , other.y)
				|| this.intersects(other.x + other.width , other.y + other.height)
				|| this.intersects(other.x , other.y + other.height)) {
			return true;
		} else
			return false;
	}

	/* The 'get' and 'set' methods */
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setPosition(float x , float y) { this.x = x; this.y = y; }
	public void setWidth(float width) { this.width = width; }
	public void setHeight(float height) { this.height = height; }
	public void setSize(float width , float height) { this.width = width; this.height = height; }

	public float getX() { return this.x; }
	public float getY() { return this.y; }
	public float getWidth() { return this.width; }
	public float getHeight() { return this.height; }

}