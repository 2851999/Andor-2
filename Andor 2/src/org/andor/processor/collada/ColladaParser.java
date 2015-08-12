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

import org.andor.processor.XMLDocument;

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
	
}