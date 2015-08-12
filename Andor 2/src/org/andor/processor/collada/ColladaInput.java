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

public class ColladaInput {
	
	/* The different kinds of semantic */
	public static final String SEMANTIC_POSITION = "POSITION";
	public static final String SEMANTIC_VERTEX = "VERTEX";
	public static final String SEMANTIC_NORMAL = "NORMAL";
	public static final String SEMANTIC_TEXCOORD = "TEXCOORD";
	
	/* The semantic and source */
	public String semantic;
	public String source;
	
	/* The possible offset */
	public int offset;
	
	/* The constructor */
	public ColladaInput() {
		this.offset = 0;
	}
	
	/* The method used for parsing */
	public void parse(Node parent) {
		//Get the attributes
		NamedNodeMap attributes = parent.getAttributes();
		//Go through the attributes
		for (int a = 0; a < attributes.getLength(); a++) {
			//Get the attribute
			Node attribute = attributes.item(a);
			//Check the current attributes name and assign the correct value
			if (attribute.getNodeName().equals("semantic"))
				this.semantic = attribute.getNodeValue();
			else if (attribute.getNodeName().equals("source"))
				this.source = attribute.getNodeValue();
			else if (attribute.getNodeName().equals("offset"))
				this.offset = Integer.parseInt(attribute.getNodeValue());
		}
	}
	
	/* The methods used to check what this input's type is */
	public boolean isPosition() { return this.semantic.equals(SEMANTIC_POSITION); }
	public boolean isVertex() { return this.semantic.equals(SEMANTIC_VERTEX); }
	public boolean isNormal() { return this.semantic.equals(SEMANTIC_NORMAL); }
	public boolean isTexCoord() { return this.semantic.equals(SEMANTIC_TEXCOORD); }
	
}