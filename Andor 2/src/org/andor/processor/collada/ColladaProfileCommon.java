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

public class ColladaProfileCommon {
	
	/* The newparams */
	public List<ColladaNewParam> newParams;
	
	/* The technique */
	public ColladaTechnique technique;
	
	/* The constructor */
	public ColladaProfileCommon() {
		this.newParams = new ArrayList<ColladaNewParam>();
	}
	
	/* The method used to get a new param given its sid */
	public ColladaNewParam getNewParamBySid(String sid) {
		//Go through the parameters
		for (int a = 0; a < this.newParams.size(); a++) {
			//Check the current parameter
			if (this.newParams.get(a).sid.equals(sid))
				return this.newParams.get(a);
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
			if (node.getNodeName().equals("newparam")) {
				//Create a new param
				ColladaNewParam newParam = new ColladaNewParam();
				newParam.parse(node);
				//Add the new param
				this.newParams.add(newParam);
			} else if (node.getNodeName().equals("technique")) {
				this.technique = new ColladaTechnique();
				this.technique.parse(node);
			}
		}
	}
	
}