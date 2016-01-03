/*
 * @(#)CommonFiles.java
 *
 * $Date: 2014-03-13 09:15:48 +0100 (Do, 13 Mär 2014) $
 *
 * Copyright (c) 2011 by Jeremy Wood.
 * All rights reserved.
 *
 * The copyright of this software is owned by Jeremy Wood. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Jeremy Wood. For details see accompanying license terms.
 * 
 * This software is probably, but not necessarily, discussed here:
 * https://javagraphics.java.net/
 * 
 * That site should also contain the most recent official version
 * of this software.  (See the SVN repository for more details.)
 */
package com.bric.util;

import java.io.File;
import java.util.Arrays;
import java.util.Vector;

public class CommonFiles {
	
	public static File getDesktop() {
		File home = new File(System.getProperty("user.home"));
		File desktop = new File(home,"Desktop");
		if(desktop.exists())
			return desktop;
		desktop = new File(home,"My Desktop");
		if(desktop.exists())
			return desktop;
		return null;
	}
	
	/** Returns the available volumes.
	 * <P>Every <code>File</code> object returned here
	 * will exist.
	 */
	public static File[] getVolumes() {
		Vector<File> list = new Vector<File>();
		if(JVM.isMac) {
			File[] volumes = new File("/Volumes/").listFiles();
			for(int a = 0; a<volumes.length; a++) {
				if(volumes[a].exists())
					list.add(volumes[a]);
			}
		} else {
			File[] roots = File.listRoots();
			for(int a = 0; a<roots.length; a++) {
				if(roots[a].exists())
					list.add(roots[a]);
			}
		}
		return list.toArray(new File[list.size()]);
	}
	

	/** Returns directories related to the user.  (Desktops, My Documents, etc.)
	 * <P>Every <code>File</code> object returned here
	 * will exist.
	 */
	public static File[] getUserDirectories(boolean includeUserHome) {
		Vector<File> list = new Vector<File>();
		
		File home = new File(System.getProperty("user.home"));
		
		String[] strings = new String[] {"Desktop", 
				"Documents",
				"Downloads",
				"Movies",
				"Music",
				"My Documents",
				"My Music",
				"My Pictures",
				"Pictures",
				"Public",
				"Sites"};
		
		if(home.exists() && includeUserHome)
			list.add(home);
		
		for(int a = 0; a<strings.length; a++) {
			File f = new File(home, strings[a]);
			if(f.exists())
				list.add(f);
		}
		
		File[] array = list.toArray(new File[list.size()]);
		Arrays.sort(array);
		return array;
	}
}
