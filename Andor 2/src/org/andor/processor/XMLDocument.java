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

package org.andor.processor;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.andor.utils.FileUtils;
import org.andor.utils.Log;
import org.andor.utils.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLDocument {
	
	/* The document */
	private Document document;
	
	/* The constructor */
	public XMLDocument(String path, boolean external) {
		//Catch any errors
		try {
			//Get the file input stream
			InputStream is = FileUtils.getInputStream(path, external);
			//Load the document
			this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
			//Close the input stream
			is.close();
		} catch (IOException e) {
			Logger.log("Andor - XMLDocument", "An error occurred while parsing the file " + path, Log.ERROR);
			e.printStackTrace();
		} catch (SAXException e) {
			Logger.log("Andor - XMLDocument", "An error occurred while parsing the file " + path, Log.ERROR);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			Logger.log("Andor - XMLDocument", "An error occurred while parsing the file " + path, Log.ERROR);
			e.printStackTrace();
		}
	}
	
	/* The getters */
	public Document getDocument() { return this.document; }
	
}