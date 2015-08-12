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

public class ColladaLibraryMaterials {
	
	/* The materials */
	public List<ColladaMaterial> materials;
	
	/* The constructor */
	public ColladaLibraryMaterials() {
		this.materials = new ArrayList<ColladaMaterial>();
	}
	
	/* The method used to return a material given its id */
	public ColladaMaterial getMaterialById(String id) {
		//Go through the materials
		for (int a = 0; a < this.materials.size(); a++) {
			//Check the current material
			if (this.materials.get(a).id.equals(id))
				return this.materials.get(a);
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
			//Check the current node
			if (node.getNodeName().equals("material")) {
				//Create the material
				ColladaMaterial material = new ColladaMaterial();
				//Parse the material
				material.parse(node);
				//Add the material
				this.materials.add(material);
			}
		}
	}
	
}