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

public class ColladaLibraryEffects {
	
	/* The effects */
	public List<ColladaEffect> effects;
	
	/* The constructor */
	public ColladaLibraryEffects() {
		this.effects = new ArrayList<ColladaEffect>();
	}
	
	/* The method used to return an effect given its id */
	public ColladaEffect getEffectById(String id) {
		//Go through the effects
		for (int a = 0; a < this.effects.size(); a++) {
			//Check the current effect
			if (this.effects.get(a).id.equals(id))
				return this.effects.get(a);
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
			if (node.getNodeName().equals("effect")) {
				//Create the effect
				ColladaEffect effect = new ColladaEffect();
				//Parse the effect
				effect.parse(node);
				//Add the image
				this.effects.add(effect);
			}
		}
	}
	
}