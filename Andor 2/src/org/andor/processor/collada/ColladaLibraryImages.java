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

public class ColladaLibraryImages {
	
	/* The images */
	public List<ColladaImage> images;
	
	/* The constructor */
	public ColladaLibraryImages() {
		this.images = new ArrayList<ColladaImage>();
	}
	
	/* The method used to return an image given its id */
	public ColladaImage getImageById(String id) {
		//Go through the image
		for (int a = 0; a < this.images.size(); a++) {
			//Check the current image
			if (this.images.get(a).id.equals(id))
				return this.images.get(a);
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
			if (node.getNodeName().equals("image")) {
				//Create the image
				ColladaImage image = new ColladaImage();
				//Parse the image
				image.parse(node);
				//Add the image
				this.images.add(image);
			}
		}
	}
	
}