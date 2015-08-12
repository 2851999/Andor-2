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

public class ColladaPhong {
	
	/* The various instances */
	public ColladaEmission emission;
	public ColladaAmbient ambient;
	public ColladaDiffuse diffuse;
	public ColladaSpecular specular;
	public ColladaShininess shininess;
	public ColladaTransparent transparent;
	public ColladaTransparency transparency;
	public ColladaIndexOfRefraction indexOfRefraction;
	
	/* The constructor */
	public ColladaPhong() {
		
	}
	
	/* The methods used to check whether a specific instance has been assigned */
	public boolean hasEmission() { return this.emission != null; }
	public boolean hasAmbient() { return this.ambient != null; }
	public boolean hasDiffuse() { return this.diffuse != null; }
	public boolean hasSpecular() { return this.specular != null; }
	public boolean hasShininess() { return this.shininess != null; }
	public boolean hasTransparent() { return this.transparent != null; }
	public boolean hasTransparency() { return this.transparency != null; }
	public boolean hasIndexOfRefraction() { return this.indexOfRefraction != null; }
	
	/* The method used for parsing */
	public void parse(Node parent) {
		//Get the nodes
		NodeList nodes = parent.getChildNodes();
		//Go through the nodes
		for (int a = 0; a < nodes.getLength(); a++) {
			//Get the current node
			Node node = nodes.item(a);
			//Check the name of the current node
			if (node.getNodeName().equals("emission")) {
				this.emission = new ColladaEmission();
				this.emission.parse(node);
			} else if (node.getNodeName().equals("ambient")) {
				this.ambient = new ColladaAmbient();
				this.ambient.parse(node);
			} else if (node.getNodeName().equals("diffuse")) {
				this.diffuse = new ColladaDiffuse();
				this.diffuse.parse(node);
			} else if (node.getNodeName().equals("specular")) {
				this.specular = new ColladaSpecular();
				this.specular.parse(node);
			} else if (node.getNodeName().equals("shininess")) {
				this.shininess = new ColladaShininess();
				this.shininess.parse(node);
			} else if (node.getNodeName().equals("transparent")) {
				this.transparent = new ColladaTransparent();
				this.transparent.parse(node);
			} else if (node.getNodeName().equals("transparency")) {
				this.transparency = new ColladaTransparency();
				this.transparency.parse(node);
			} else if (node.getNodeName().equals("index_of_refraction")) {
				this.indexOfRefraction = new ColladaIndexOfRefraction();
				this.indexOfRefraction.parse(node);
			}
		}
	}
	
}