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

public class ColladaInstanceController {
	
	/* The url */
	public String url;
	
	/* The skeletons */
	public List<ColladaSkeleton> skeletons;
	
	/* The bind material instance */
	public ColladaBindMaterial bindMaterial;
	
	/* The constructor */
	public ColladaInstanceController() {
		this.skeletons = new ArrayList<ColladaSkeleton>();
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
			if (attribute.getNodeName().equals("url"))
				this.url = attribute.getNodeValue();
		}
		//Get the nodes
		NodeList nodes = parent.getChildNodes();
		//Go through the nodes
		for (int a = 0; a < nodes.getLength(); a++) {
			//Get the current node
			Node node = nodes.item(a);
			//Check the name of the current node
			if (node.getNodeName().equals("skeleton")) {
				ColladaSkeleton skeleton = new ColladaSkeleton();
				skeleton.parse(node);
				this.skeletons.add(skeleton);
			} else if (node.getNodeName().equals("bind_material")) {
				this.bindMaterial = new ColladaBindMaterial();
				this.bindMaterial.parse(node);
			}
		}
	}
	
}