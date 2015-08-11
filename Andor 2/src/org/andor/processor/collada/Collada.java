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

import java.nio.FloatBuffer;
import java.util.List;

import org.andor.core.model.ANGeometry;
import org.andor.core.model.ANModel;
import org.andor.utils.BufferUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Collada {
	
	/* The geometry */
	public LibraryGeometry libraryGeometry;
	
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
				//Create the library
				this.libraryGeometry = new LibraryGeometry();
				//Parse the geometry
				this.libraryGeometry.parse(node);
			}
		}
	}
	
	/* The method used to turn this collada into an ANModel instance */
	public ANModel convert() {
		//The model
		ANModel model = new ANModel();
		//Go through the geometry
		for (int a = 0; a < this.libraryGeometry.geometries.size(); a++)
			//Add the current geometry
			model.geometry.add(convert(this.libraryGeometry.geometries.get(a)));
		//Return the model
		return model;
	}
	
	/* The method used to convert geometry to the ANGeometry format */
	public ANGeometry convert(Geometry geometry) {
		//The geometry
		ANGeometry converted = new ANGeometry();
		//Get the needed objects
		Mesh mesh = geometry.mesh;
		Polylist polylist = mesh.polylist;
		List<Input> inputs = polylist.inputs;
		P p = polylist.p;
		//Go through the sources
		for (int a = 0; a < inputs.size(); a++) {
			//Get the current input
			Input input = inputs.get(a);
			//Check the type
			if (input.isVertex()) {
				int[] indices = p.getValues(input.offset, polylist.count, 3); //TRIANGLES ONLY
				//Get the source
				Source source = mesh.getSource(mesh.vertices.input.source.substring(1)); //Id starts with #
				//Get the stride
				int stride = source.techniqueCommon.accessor.stride;
				//Get the float array needed
				FloatArray array = source.floatArray;
				//Get the number of values needed
				int noValues = polylist.count * stride;
				//Create the float buffer
				FloatBuffer buffer = BufferUtils.createFloatBuffer(noValues);
				//Go through the indices
				for (int b = 0; b < indices.length; b++) {
					//Add the values indicated by the current index
					buffer.put(array.getValues(indices[b], stride));
				}
				//Assign the vertices
				buffer.position(0);
				converted.vertices = new float[noValues * 3]; //3 values
				buffer.get(converted.vertices);
			} else if (input.isNormal()) {
				int[] indices = p.getValues(input.offset, polylist.count, 3); //TRIANGLES ONLY
				//Get the source
				Source source = mesh.getSource(input.source.substring(1)); //Id starts with #
				//Get the stride
				int stride = source.techniqueCommon.accessor.stride;
				//Get the float array needed
				FloatArray array = source.floatArray;
				//Get the number of values needed
				int noValues = polylist.count * stride;
				//Create the float buffer
				FloatBuffer buffer = BufferUtils.createFloatBuffer(noValues);
				//Go through the indices
				for (int b = 0; b < indices.length; b++) {
					//Add the values indicated by the current index
					buffer.put(array.getValues(indices[b], stride));
				}
				//Assign the normals
				buffer.position(0);
				converted.normals = new float[noValues * 3];
				buffer.get(converted.normals);
			} else if (input.isTexCoord()) {
				int[] indices = p.getValues(input.offset, polylist.count, 3); //TRIANGLES ONLY
				//Get the source
				Source source = mesh.getSource(input.source.substring(1)); //Id starts with #
				//Get the stride
				int stride = source.techniqueCommon.accessor.stride;
				//Get the float array needed
				FloatArray array = source.floatArray;
				//Get the number of values needed
				int noValues = polylist.count * stride;
				//Create the float buffer
				FloatBuffer buffer = BufferUtils.createFloatBuffer(noValues);
				//Go through the indices
				for (int b = 0; b < indices.length; b++) {
					//Add the values indicated by the current index
					buffer.put(array.getValues(indices[b], stride));
				}
				//Assign the texture coordinates
				buffer.position(0);
				converted.textureCoordinates = new float[noValues * 2];
				buffer.get(converted.textureCoordinates);
			}
		}
		//Return the geometry
		return converted;
	}
	
}