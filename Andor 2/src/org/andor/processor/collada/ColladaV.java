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

package org.andor.processor.collada;

import org.w3c.dom.Node;

public class ColladaV {
	
	/* The values */
	public int[] values;
	
	/* The constructor */
	public ColladaV() {
		
	}
	
	/* The method used to return an array of values given an offset */
	public int[] getValues(int offset, int count, int valuesCount) {
		//The array
		int stride = values.length / count / valuesCount;
		int[] v = new int[count * valuesCount];
		int n = 0;
		//Go through and set the values
		for (int a = offset; a < values.length; a+=stride) {
			v[n] = this.values[a];
			n++;
		}
		//Return the values
		return v;
	}
	
	/* The method used for parsing */
	public void parse(Node parent) {
		//Get the values from the node
		String[] split = parent.getTextContent().split(" ");
		//Create the values object
		this.values = new int[split.length];
		//Assign the values
		for (int a = 0; a < this.values.length; a++)
			this.values[a] = Integer.parseInt(split[a]);
	}
	
}