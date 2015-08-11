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

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class FloatArray {
	
	/* The id of this data */
	public String id;
	
	/* The count */
	public int count;
	
	/* The values */
	public float[] values;
	
	/* The constructor */
	public FloatArray() {
		
	}
	
	/* The method used to return a set of values given the location and stride */
	public float[] getValues(int location, int stride) {
		int position = location * stride;
		//Create the array
		float[] v = new float[stride];
		int n = 0;
		//Assign the values
		for (int a = position; a < position + stride; a++) {
			v[n] = this.values[a];
			n++;
		}
		//Return the values
		return v;
	}
	
	/* The method used for parsing */
	public void parse(Node parent) {
		//Get the attributes
		NamedNodeMap attributes = parent.getAttributes();
		//Go through the attributes
		for (int a = 0; a < attributes.getLength(); a++) {
			//Get the attribute
			Node attribute = attributes.item(a);
			//Check the current attributes name and assign the correct value
			if (attribute.getNodeName().equals("id"))
				this.id = attribute.getNodeValue();
			else if (attribute.getNodeName().equals("count"))
				this.count = Integer.parseInt(attribute.getNodeValue());
		}
		//Create the values object
		this.values = new float[count];
		//Get the values from the node
		String[] split = parent.getTextContent().split(" ");
		//Assign the values
		for (int a = 0; a < this.values.length; a++)
			this.values[a] = Float.parseFloat(split[a]);
	}
	
}