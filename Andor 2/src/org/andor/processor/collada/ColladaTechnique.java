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
import org.w3c.dom.NodeList;

public class ColladaTechnique {
	
	/* The sid of this parameter */
	public String sid;
	
	/* The profile */
	public String profile;
	
	/* The phong instance */
	public ColladaPhong phong;
	
	/* The extra instance */
	public ColladaExtra extra;
	
	/* The bump instance */
	public ColladaBump bump;
	
	/* The constructor */
	public ColladaTechnique() {
		
	}
	
	/* The methods used to check whether a certain value exists */
	public boolean hasSid() { return this.sid != null; }
	public boolean hasProfile() { return this.profile != null; }
	public boolean hasPhong() { return this.phong != null; }
	public boolean hasExtra() { return this.extra != null; }
	public boolean hasBump() { return this.bump != null; }
	
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
			else if (attribute.getNodeName().equals("profile"))
				this.profile = attribute.getNodeValue();
		}
		//Get the nodes
		NodeList nodes = parent.getChildNodes();
		//Go through the nodes
		for (int a = 0; a < nodes.getLength(); a++) {
			//Get the current node
			Node node = nodes.item(a);
			//Check the name of the current node
			if (node.getNodeName().equals("phong")) {
				this.phong = new ColladaPhong();
				this.phong.parse(node);
			} else if (node.getNodeName().equals("extra")) {
				this.extra = new ColladaExtra();
				this.extra.parse(node);
			} else if (node.getNodeName().equals("bump")) {
				this.bump = new ColladaBump();
				this.bump.parse(node);
			}
		}
	}
	
}