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

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Polylist {
	
	/* The material and count */
	public String material;
	public int count;
	
	/* The inputs in this list */
	public List<Input> inputs;
	
	/* The p in this list */
	public P p;
	
	/* The constructor */
	public Polylist() {
		this.inputs = new ArrayList<Input>();
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
			if (attribute.getNodeName().equals("material"))
				this.material = attribute.getNodeValue();
			else if (attribute.getNodeName().equals("count"))
				this.count = Integer.parseInt(attribute.getNodeValue());
		}
		//Get the nodes
		NodeList nodes = parent.getChildNodes();
		//Go through the nodes
		for (int a = 0; a < nodes.getLength(); a++) {
			//Get the current node
			Node node = nodes.item(a);
			//Check the name of the current node
			if (node.getNodeName().equals("input")) {
				//Create a new input
				Input input = new Input();
				input.parse(node);
				//Add the source
				this.inputs.add(input);
			} else if (node.getNodeName().equals("p")) {
				this.p = new P();
				p.parse(node);
			}
		}
	}
	
}