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

import java.util.HashMap;

import org.andor.core.resource.texture.Texture;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Collada {
	
	/* The images */
	public ColladaLibraryImages libraryImages;
	
	/* The effects */
	public ColladaLibraryEffects libraryEffects;
	
	/* The materials */
	public ColladaLibraryMaterials libraryMaterials;
	
	/* The geometry */
	public ColladaLibraryGeometry libraryGeometry;
	
	/* The animations */
	public ColladaLibraryAnimations libraryAnimations;
	
	/* The controllers */
	public ColladaLibraryControllers libraryControllers;
	
	/* The visual scenes */
	public ColladaLibraryVisualScenes libraryVisualScenes;
	
	/* The loaded textures */
	public HashMap<String, Texture> loadedTextures;
	
	/* The constructor */
	public Collada() {
		
	}
	
	/* The method used for parsing */
	public void parse(Element element) {
		//Get a list of the child elements
		NodeList nodes = element.getChildNodes();
		//Go through the nodes
		for (int a = 0; a < nodes.getLength(); a++) {
			//Get the current node
			Node node = nodes.item(a);
			//Check the type of node this is
			if (node.getNodeName().equals("library_geometries")) {
				//Create and parse the library
				this.libraryGeometry = new ColladaLibraryGeometry();
				this.libraryGeometry.parse(node);
			} else if (node.getNodeName().equals("library_images")) {
				//Create and parse the library
				this.libraryImages = new ColladaLibraryImages();
				this.libraryImages.parse(node);
			} else if (node.getNodeName().equals("library_effects")) {
				//Create and parse the library
				this.libraryEffects = new ColladaLibraryEffects();
				this.libraryEffects.parse(node);
			} else if (node.getNodeName().equals("library_materials")) {
				//Create and parse the library
				this.libraryMaterials = new ColladaLibraryMaterials();
				this.libraryMaterials.parse(node);
			} else if (node.getNodeName().equals("library_animations")) {
				//Create and parse the library
				this.libraryAnimations = new ColladaLibraryAnimations();
				this.libraryAnimations.parse(node);
			} else if (node.getNodeName().equals("library_controllers")) {
				//Create and parse the library
				this.libraryControllers = new ColladaLibraryControllers();
				this.libraryControllers.parse(node);
			} else if (node.getNodeName().equals("library_visual_scenes")) {
				//Create and parse the library
				this.libraryVisualScenes = new ColladaLibraryVisualScenes();
				this.libraryVisualScenes.parse(node);
			}
		}
	}
	
	/* The method used to return whether this model has an animation */
	public boolean hasAnimation() {
		return this.libraryAnimations != null;
	}
	
}