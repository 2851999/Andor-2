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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ColladaJoints {
	
	/* The inputs in this list */
	public List<ColladaInput> inputs;
	
	/* The constructor */
	public ColladaJoints() {
		this.inputs = new ArrayList<ColladaInput>();
	}
	
	/* The method used to return an input given its semantic */
	public ColladaInput getInputBySemantic(String semantic) {
		for (int a = 0; a < inputs.size(); a++) {
			if (inputs.get(a).semantic.equals(semantic))
				return inputs.get(a);
		}
		return null;
	}
	
	/* The method used for parsing */
	public void parse(Node parent) {
		//Get the nodes
		NodeList nodes = parent.getChildNodes();
		//Go through the nodes
		for (int a = 0; a < nodes.getLength(); a++) {
			//Get the current node
			Node node = nodes.item(a);
			//Check the name of the current node
			if (node.getNodeName().equals("input")) {
				//Create a new input
				ColladaInput input = new ColladaInput();
				input.parse(node);
				//Add the source
				this.inputs.add(input);
			}
		}
	}
	
}