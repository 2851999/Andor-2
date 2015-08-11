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

import org.andor.core.model.ANGeometry;
import org.andor.processor.XMLDocument;
import org.andor.utils.SystemInformation;

public class ColladaParser {
	
	/* The static method used to parse a collada file */
	public static Collada parse(XMLDocument document) {
		//Create the collada object
		Collada collada = new Collada();
		//Parse the document
		collada.parse(document.getDocument().getDocumentElement());
		//Return the collada
		return collada;
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		//Collada collada = parse(new XMLDocument("H:/Storage/Users/Joel/Desktop/Sponza/sponza.dae", true));
		Collada collada = parse(new XMLDocument("H:/Storage/Users/Joel/Desktop/box.dae", true));
		ANGeometry geometry = collada.convert(collada.libraryGeometry.geometries.get(0));
		long total = System.currentTimeMillis() - start;
		for (int a = 0; a < geometry.vertices.length; a++)
			System.out.println(geometry.vertices[a]);
		System.out.println("TOTAL TIME " + ((float) total / 1000f) + " seconds");
		System.out.println((((SystemInformation.getTotalMemory() - SystemInformation.getFreeMemory()) / 1024) / 1024));
	}
	
}