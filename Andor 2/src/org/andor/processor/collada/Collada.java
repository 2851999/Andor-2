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
import java.util.HashMap;
import java.util.List;

import org.andor.core.model.ANGeometry;
import org.andor.core.model.ANModel;
import org.andor.core.render.Material;
import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureLoader;
import org.andor.utils.BufferUtils;
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
			}
		}
	}
	
	/* The method used to turn this collada into an ANModel instance */
	public ANModel convert(String path, boolean external) {
		//The model
		ANModel model = new ANModel();
		//Create the textures hashmap
		this.loadedTextures = new HashMap<String, Texture>();
		//Go through the geometry
		for (int a = 0; a < this.libraryGeometry.geometries.size(); a++)
			//Add the current geometry
			convert(model.geometry, this.libraryGeometry.geometries.get(a), path, external);
		//Return the model
		return model;
	}
	
	/* The method used to convert geometry to the ANGeometry format */
	public void convert(List<ANGeometry> geometryList, ColladaGeometry geometry, String path, boolean external) {
		//Get the needed objects
		ColladaMesh mesh = geometry.mesh;
		List<ColladaPolylist> polylists = mesh.polylists;
		//Go through the sources
		for (int c = 0; c < polylists.size(); c++) {
			ColladaPolylist polylist = polylists.get(c);
			ColladaP p = polylist.p;
			List<ColladaInput> inputs = polylist.inputs;
			//The geometry
			ANGeometry converted = new ANGeometry();
			for (int a = 0; a < inputs.size(); a++) {
				//Get the current input
				ColladaInput input = inputs.get(a);
				//Check the type
				if (input.isVertex())
					this.assignFloatArray(converted, mesh, input, polylist, p, mesh.vertices.input.source);
				else if (input.isNormal())
					this.assignFloatArray(converted, mesh, input, polylist, p, input.source);
				else if (input.isTexCoord())
					this.assignFloatArray(converted, mesh, input, polylist, p, input.source);
			}
			//Check to see whether a material exists
			if (polylist.hasMaterial())
				//Assign the material
				converted.material = this.convert(this.libraryMaterials.getMaterialById(polylist.material), path, external);
			//Add the geometry
			geometryList.add(converted);
		}
	}
	
	/* The method used to convert a ColladaMaterial into a Material instance */
	public Material convert(ColladaMaterial material, String path, boolean external) {
		//Make sure the material isn't null
		if (material != null) {
			//The material
			Material converted = new Material(material.name);
			
			//Get the effect
			ColladaEffect effect = this.libraryEffects.getEffectById(material.instanceEffect.url.substring(1)); //URL begins with a #
			ColladaProfileCommon profileCommon = effect.profileCommon;
			//Make sure it has information for the material
			if (profileCommon.technique.hasPhong()) {
				//Get the phong instance
				ColladaPhong phong = profileCommon.technique.phong;
				//Check whether the phong instance has any values that are supported
				if (phong.hasDiffuse()) {
					//Check the diffuse value
					if (phong.diffuse.hasTexture())
						//Set the texture in the material
						converted.setDiffuseTexture(this.loadTexture(phong.diffuse.texture, profileCommon, path, external));
				}
			}
			
			return converted;
		}
		//Something wasn't found so return null
		return null;
	}
	
	/* The method used to load and return a texture */
	public Texture loadTexture(ColladaTexture texture, ColladaProfileCommon profileCommon, String path, boolean external) {
		//The loaded texture
		Texture loadedTexture = null;
		//Get the texture name
		String textureId = profileCommon.getNewParamBySid(profileCommon.getNewParamBySid(texture.texture).sampler2D.source.value).surface.initFrom.value;
		//Get the image
		ColladaImage image = this.libraryImages.getImageById(textureId);
		//Get the texture name
		String textureName = image.initFrom.value;
		//Check to see whether the image has already been loaded
		if (this.loadedTextures.containsKey(textureName))
			//Assign the texture
			loadedTexture = this.loadedTextures.get(textureName);
		else
			this.loadedTextures.put(textureName, loadedTexture = TextureLoader.load(path + textureName, external));
		//Return the texture
		return loadedTexture;
	}
	
	/* The method used to get assign a float array from a mesh */
	public void assignFloatArray(ANGeometry geometry, ColladaMesh mesh, ColladaInput input, ColladaPolylist polylist, ColladaP p, String sourceId) {
		int[] indices = p.getValues(input.offset, polylist.count, 3); //TRIANGLES ONLY
		//Get the source
		ColladaSource source = mesh.getSourceById(sourceId.substring(1)); //Id starts with #
		//Get the stride
		int stride = source.techniqueCommon.accessor.stride;
		//Get the float array needed
		ColladaFloatArray array = source.floatArray;
		//Get the number of values needed
		int noValues = polylist.count * stride;
		//Create the float buffer
		FloatBuffer buffer = BufferUtils.createFloatBuffer(noValues);
		//The invertY value
		boolean invertY = false;
		if (input.isTexCoord())
			invertY = true;
		//Go through the indices
		for (int b = 0; b < indices.length; b++) {
			//Add the values indicated by the current index
			buffer.put(array.getValues(indices[b], stride, invertY));
		}
		//Assign the values
		buffer.position(0);
		if (input.isVertex()) {
			geometry.vertices = new float[noValues * 3];
			buffer.get(geometry.vertices);
		} else if (input.isNormal()) {
			geometry.normals = new float[noValues * 3];
			buffer.get(geometry.normals);
		} else if (input.isTexCoord()) {
			geometry.textureCoordinates = new float[noValues * 3];
			buffer.get(geometry.textureCoordinates);
		}
	}
	
}