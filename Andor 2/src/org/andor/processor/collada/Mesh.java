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

public class Mesh {
	
	/* The sources */
	public List<Source> sources;
	
	/* The vertices */
	public Vertices vertices;
	
	/* The polylist */
	public Polylist polylist;
	
	/* The constructor */
	public Mesh() {
		this.sources = new ArrayList<Source>();
	}
	
	/* The method used to get a source given a name */
	public Source getSource(String id) {
		//Go through the sources
		for (int a = 0; a < sources.size(); a++) {
			if (sources.get(a).id.equals(id))
				return sources.get(a);
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
			if (node.getNodeName().equals("source")) {
				//Create a new source
				Source source = new Source();
				source.parse(node);
				//Add the source
				this.sources.add(source);
			} else if (node.getNodeName().equals("vertices")) {
				this.vertices = new Vertices();
				this.vertices.parse(node);
			} else if (node.getNodeName().equals("polylist")) {
				this.polylist = new Polylist();
				this.polylist.parse(node);
			}
		}
	}
	
}