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

package org.andor;

import org.andor.core.Font;
import org.andor.core.resource.texture.Texture;

public class Andor {
	
	/* The current version */
	public static final String VERSION = "V2.0.1";
	
	/* The nickname of this version */
	public static final String VERSION_NAME = "Unknown At This Time";
	
	/* The current build's type */
	public static final String BUILD_TYPE = "Experimental";
	
	/* The date this build started development */
	public static final String DATE = "30/07/2015";
	
	/* The default engine resources */
	public static class Resources {
		/* The textures */
		public static class Textures {
			/* The blank texture */
			public static Texture Blank;
		}
		/* The fonts */
		public static class Fonts {
			/* The default font */
			public static Font Default;
		}
	}
	
}