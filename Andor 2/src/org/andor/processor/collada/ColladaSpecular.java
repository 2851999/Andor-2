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
import org.w3c.dom.NodeList;

public class ColladaSpecular {
	
	/* The colour instance */
	public ColladaColor color;
	
	/* The texture instance */
	public ColladaTexture texture;
	
	/* The constructor */
	public ColladaSpecular() {
		
	}
	
	/* The methods used to check whether a value is present */
	public boolean hasColor() { return this.color != null; }
	public boolean hasTexture() { return this.texture != null; }
	
	/* The method used for parsing */
	public void parse(Node parent) {
		//Get the nodes
		NodeList nodes = parent.getChildNodes();
		//Go through the nodes
		for (int a = 0; a < nodes.getLength(); a++) {
			//Get the current node
			Node node = nodes.item(a);
			//Check the name of the current node
			if (node.getNodeName().equals("color")) {
				this.color = new ColladaColor();
				this.color.parse(node);
			} else if (node.getNodeName().equals("texture")) {
				this.texture = new ColladaTexture();
				this.texture.parse(node);
			}
		}
	}
	
}