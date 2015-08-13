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

public class ColladaTranslate {
	
	/* The sid of this data */
	public String sid;
	
	/* The values */
	public float[] values;
	
	/* The constructor */
	public ColladaTranslate() {
		
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
			if (attribute.getNodeName().equals("sid"))
				this.sid = attribute.getNodeValue();
		}
		//Get the values from the node
		String[] split = parent.getTextContent().split(" ");
		//Create the values object
		this.values = new float[split.length];
		//Assign the values
		for (int a = 0; a < this.values.length; a++)
			this.values[a] = Float.parseFloat(split[a]);
	}
	
}