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
import java.util.List;

import org.andor.core.Matrix4f;
import org.andor.core.Vector2f;
import org.andor.core.Vector3f;
import org.andor.core.model.Model;
import org.andor.core.model.ModelJoint;
import org.andor.core.model.ModelSkeleton;
import org.andor.core.model.ModelSkin;
import org.andor.core.model.ModelVertex;
import org.andor.core.model.ModelVertexGroup;
import org.andor.core.render.Material;
import org.andor.core.resource.texture.Texture;
import org.andor.core.resource.texture.TextureLoader;

public class ColladaConverter {
	
	/* The static method used to convert a collada model into an Andor one */
	public static Model convert(Collada collada, String path, boolean external) {
		//The model
		Model model = new Model();
		ModelSkin skin = new ModelSkin(model);
		
		List<ColladaGeometry> geometries = collada.libraryGeometry.geometries;
		for (int g = 0; g < geometries.size(); g++) {
			ColladaGeometry geometry = geometries.get(g);
			ColladaMesh mesh = geometry.mesh;
			
			//Get the vertices data
			ColladaVertices vertices = mesh.vertices;
			ColladaSource verticesSource = mesh.getSourceById(vertices.input.source.substring(1));
			float[] verticesData = verticesSource.floatArray.values;
			
			//Create the model vertices
			ModelVertex[] modelVertices = new ModelVertex[verticesData.length / 3];
			for (int a = 0; a < modelVertices.length; a++) {
				int n = a * 3;
				modelVertices[a] = new ModelVertex();
				modelVertices[a].position = new Vector3f(
					verticesData[n],
					verticesData[n + 1],
					verticesData[n + 2]
				);
			}
			
			//Check for an animation
			if (collada.hasAnimation()) {
				//Assign the animation data
				
				//Get the needed data
				ColladaNode object = collada.libraryVisualScenes.visualScenes.get(0).getNodeWithInstanceController();
				ColladaInstanceController instanceController = object.instanceController;
				List<ColladaSkeleton> roots = instanceController.skeletons;
				ColladaController controller = collada.libraryControllers.getControllerById(instanceController.url.substring(1));
				ColladaNode armature = collada.libraryVisualScenes.visualScenes.get(0).getNodeById(controller.name);
				
				ModelSkeleton skeleton = new ModelSkeleton();
				for (int a = 0; a < roots.size(); a++) {
					//Interpret the current root
					skeleton.rootJoints.add(convertJoint(armature.getNodeById(roots.get(a).value.substring(1)), skeleton.allJoints, skin));
				}
				
				ColladaSkin controllerSkin = controller.skin;
				skin.bindShapeMatrix = controllerSkin.bindShapeMatrix.getMatrix();
				ColladaJoints joints = controllerSkin.joints;
				ColladaInput invBindMatricesInput = joints.getInputBySemantic(ColladaInput.SEMANTIC_INV_BIND_MATRIX);
				ColladaSource invBindMatricesSource = controllerSkin.getSourceById(invBindMatricesInput.source.substring(1));
				
				ColladaVertexWeights vertexWeights = controllerSkin.vertexWeights;
				ColladaInput jointsInput = vertexWeights.getInputBySemantic(ColladaInput.SEMANTIC_JOINT);
				ColladaInput weightsInput = vertexWeights.getInputBySemantic(ColladaInput.SEMANTIC_WEIGHT);
				ColladaSource jointsSource = controllerSkin.getSourceById(jointsInput.source.substring(1));
				ColladaNameArray jointsNames = jointsSource.nameArray;
				ColladaSource weightsSource = controllerSkin.getSourceById(weightsInput.source.substring(1));
				ColladaVCount vCount = vertexWeights.vCount;
				ColladaV v = vertexWeights.v;
				
				//Assign the inverse bind matrices
				for (int a = 0; a < jointsNames.count; a++) {
					int n = a * 16;
					float[] values = new float[16];
					for (int b = n; b < n + 16; b++)
						values[b - n] = invBindMatricesSource.floatArray.values[b];
					skeleton.getJointByName(jointsNames.values[a]).inverseBindMatrix = new Matrix4f(values);
				}
				
				int count = 0;
				for (int a = 0; a < vCount.values.length; a++) {
					modelVertices[a].joints = new ModelJoint[vCount.values[a]];
					modelVertices[a].weights = new float[vCount.values[a]];
					//Go through the current values
					for (int b = 0; b < vCount.values[a]; b++) {
						modelVertices[a].joints[b] = skeleton.getJointByName(jointsNames.values[v.values[count + (b * 2)]]); //COULD BE WRONG
						modelVertices[a].weights[b] = weightsSource.floatArray.values[v.values[(count + (b * 2)) + 1]];
					}
					count += vCount.values[a] * 2;
					//Normalise the weights
//					float total = 0;
//					for (int b = 0; b < modelVertices[a].weights.length; b++)
//						total += modelVertices[a].weights[b];
//					if (total > 1.0f) { //!= or >
//						for (int b = 0; b < modelVertices[a].weights.length; b++) {
//							modelVertices[a].weights[b] /= total;                       //COULD BE WRONG
//						}
//					}
				}
				
				model.skeleton = skeleton;
				
				//Now onto interpreting the animation data for each joint
				List<ColladaAnimation> animations = collada.libraryAnimations.animations;
				//Go through the animations
				for (int a = 0; a < animations.size(); a++) {
					ColladaAnimation animation = animations.get(a);
					ColladaChannel channel = animation.channel;
					ColladaSampler sampler = animation.sampler;
					ColladaInput inputInput = sampler.getInputBySemantic(ColladaInput.SEMANTIC_INPUT);
					ColladaInput outputInput = sampler.getInputBySemantic(ColladaInput.SEMANTIC_OUTPUT);
					ColladaSource inputSource = animation.getSourceById(inputInput.source.substring(1));
					ColladaSource outputSource = animation.getSourceById(outputInput.source.substring(1));
					ColladaFloatArray inputs = inputSource.floatArray;
					ColladaFloatArray outputs = outputSource.floatArray;
					
					//Get the joint
					String jointName = channel.target.split("/")[0];
					ModelJoint joint = skeleton.getJointByName(jointName);
					joint.keyframes = inputs.values;
					for (int b = 0; b < inputs.values.length; b++) {
						joint.animationTime += inputs.values[b];
					}
					joint.keyframeTransforms = new Matrix4f[inputs.count];
					
					for (int b = 0; b < joint.keyframeTransforms.length; b++) {
						int n = b * 16;
						float[] values = new float[16];
						for (int c = n; c < n + 16; c++) {
							values[c - n] = outputs.values[c];
						}
						joint.keyframeTransforms[b] = new Matrix4f(values);
					}
				}
			}
			
			//Get the polylists for each of the vertex groups
			List<ColladaPolylist> polylists = mesh.polylists;
			
			//Go through the lists
			for (int a = 0; a < polylists.size(); a++) {
				ColladaPolylist current = polylists.get(a);
				
				ModelVertexGroup vertexGroup = new ModelVertexGroup(skin);
				ModelVertex[] verticesArray = new ModelVertex[current.count * 3]; //Triangles only
				
				List<ColladaInput> inputs = current.inputs;
				for (int b = 0; b < inputs.size(); b++) {
					ColladaInput input = inputs.get(b);
					int[] indices = current.p.getValues(input.offset, current.count, 3); //Triangles only
					
					//Check the type of input
					if (input.isVertex()) {
						//Go through the indices and assign the data
						for (int c = 0; c < indices.length; c++) {
							verticesArray[c] = modelVertices[indices[c]].clone();
						}
					} else {
						//Get the needed data
						ColladaSource source = mesh.getSourceById(input.source.substring(1));
						ColladaFloatArray array = source.floatArray;
						if (input.isNormal()) {
							//Go through the indices and assign the data
							for (int c = 0; c < indices.length; c++) {
								float[] data = array.getValues(indices[c], source.techniqueCommon.accessor.stride, false);
								verticesArray[c].normal = new Vector3f(data[0], data[1], data[2]);
							}
						} else if (input.isTexCoord()) {
							//Go through the indices and assign the data
							for (int c = 0; c < indices.length; c++) {
								float[] data = array.getValues(indices[c], source.techniqueCommon.accessor.stride, true);
								verticesArray[c].textureCoordinate = new Vector2f(data[0], data[1]);
							}
						}
					}
				}
				vertexGroup.vertices = verticesArray;
				vertexGroup.material = convertMaterial(collada, collada.libraryMaterials.getMaterialById(current.material), path, external);
				skin.vertexGroups.add(vertexGroup);
			}
		}
		
		//Return the model
		model.skin = skin;
		return model;
	}
	
	/* The static method used to convert a ColladaMaterial into a Material instance */
	public static Material convertMaterial(Collada collada, ColladaMaterial material, String path, boolean external) {
		//Make sure the material isn't null
		if (material != null) {
			//The material
			Material converted = new Material(material.name);
			
			//Get the effect
			ColladaEffect effect = collada.libraryEffects.getEffectById(material.instanceEffect.url.substring(1)); //URL begins with a #
			ColladaProfileCommon profileCommon = effect.profileCommon;
			//Make sure it has information for the material
			if (profileCommon.technique.hasPhong()) {
				//Get the phong instance
				ColladaPhong phong = profileCommon.technique.phong;
				//Check whether the phong instance has any values that are supported
				if (phong.hasDiffuse()) {
					//Check the diffuse value
					if (phong.diffuse.hasColour())
						//Set the colour in the material
						converted.setDiffuseColour(phong.diffuse.colour.getColour());
					else if (phong.diffuse.hasTexture())
						//Set the texture in the material
						converted.setDiffuseTexture(loadTexture(collada, phong.diffuse.texture, profileCommon, path, external));
				}
			}
			
			return converted;
		}
		//Something wasn't found so return null
		return null;
	}
	
	/* The static methods used to convert a Collada joint into a ModelJoint */
	public static ModelJoint convertJoint(ColladaNode node, List<ModelJoint> allJoints, ModelSkin skin) {
		//Create the joint
		ModelJoint joint = new ModelJoint();
		joint.skin = skin;
		joint.name = node.id;
		joint.jointMatrix = node.matrix.getMatrix();
		allJoints.add(joint);
		convertJoint(joint, node, allJoints, skin);
		return joint;
	}
	
	public static void convertJoint(ModelJoint joint, ColladaNode node, List<ModelJoint> allJoints, ModelSkin skin) {
		//Convert the children
		List<ColladaNode> children = node.nodes;
		for (int a = 0; a < children.size(); a++) {
			ModelJoint child = new ModelJoint();
			child.name = children.get(a).id;
			child.parent = joint;
			child.skin = skin;
			child.jointMatrix = children.get(a).matrix.getMatrix();
			convertJoint(child, children.get(a), allJoints, skin);
			joint.skin = skin;
			joint.children.add(child);
			allJoints.add(child);
		}
	}
	
	/* The static method used to load and return a texture */
	public static Texture loadTexture(Collada collada, ColladaTexture texture, ColladaProfileCommon profileCommon, String path, boolean external) {
		//The loaded texture
		Texture loadedTexture = null;
		//Get the texture name
		String textureId = profileCommon.getNewParamBySid(profileCommon.getNewParamBySid(texture.texture).sampler2D.source.value).surface.initFrom.value;
		//Get the image
		ColladaImage image = collada.libraryImages.getImageById(textureId);
		//Get the texture name
		String textureName = image.initFrom.value;
		if (collada.loadedTextures == null)
			collada.loadedTextures = new HashMap<String, Texture>();
		//Check to see whether the image has already been loaded
		if (collada.loadedTextures.containsKey(textureName))
			//Assign the texture
			loadedTexture = collada.loadedTextures.get(textureName);
		else
			collada.loadedTextures.put(textureName, loadedTexture = TextureLoader.load(path + textureName, external));
		//Return the texture
		return loadedTexture;
	}
	
}