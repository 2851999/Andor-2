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

public class ColladaVisualScene {
	
	/* The id and name */
	public String id;
	public String name;
	
	/* The nodes */
	public List<ColladaNode> nodes;
	
	/* The constructor */
	public ColladaVisualScene() {
		this.nodes = new ArrayList<ColladaNode>();
	}
	
	/* The method used to get a source given it's id */
	public ColladaNode getNodeById(String id) {
		//Go through the sources
		for (int a = 0; a < nodes.size(); a++) {
			if (nodes.get(a).id.equals(id))
				return nodes.get(a);
		}
		return null;
	}
	
	/* The method used to get a source given it's sid */
	public ColladaNode getNodeBySid(String sid) {
		//Go through the sources
		for (int a = 0; a < nodes.size(); a++) {
			if (nodes.get(a).sid.equals(sid))
				return nodes.get(a);
		}
		return null;
	}
	
	/* The method used for parsing */
	public void parse(Node parent) {
		//Go through the attributes
		NamedNodeMap attributes = parent.getAttributes();
		for (int a = 0; a < attributes.getLength(); a++) {
			//Get the attribute
			Node attribute = attributes.item(a);
			//Check the current attributes name and assign the correct value
			if (attribute.getNodeName().equals("id"))
				this.id = attribute.getNodeValue();
			else if (attribute.getNodeName().equals("name"))
				this.name = attribute.getNodeValue();
		}
		//Get the nodes
		NodeList nodes = parent.getChildNodes();
		//Go through the nodes
		for (int a = 0; a < nodes.getLength(); a++) {
			//Get the current node
			Node node = nodes.item(a);
			//Check the name of the current node
			if (node.getNodeName().equals("node")) {
				//Create a new node
				ColladaNode n = new ColladaNode();
				n.parse(node);
				//Add the node
				this.nodes.add(n);
			}
		}
	}
	
}