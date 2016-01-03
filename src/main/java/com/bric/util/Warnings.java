/*
 * @(#)Warnings.java
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

import java.util.Hashtable;
import java.util.Vector;

/** Static methods related to warnings.
*/
public class Warnings {
	
	private static Hashtable<String, Long> textToTime;
	private static Vector<String> printOnceMessages;
	
	/** Prints a message once to System.err.
	* A unique string will only be printed once per session.
	* This is intended for cases where a developer should be alerted
	* to a possible problem, but the console should not be flooded
	* with messages.  Also the "problem" is one that only deserves a
	* warning -- not an exception.
	*/
	public static synchronized void printOnce(String message) {
		if(printOnceMessages==null)
			printOnceMessages = new Vector<String>();
		
		if(printOnceMessages.contains(message)) {
			return;
		}
		System.err.println(message);
		printOnceMessages.add(message);
	}
	
	/** Prints this message to the console, but only once every so often.
	 * <P>This is intended to prevent the console from being flooded with
	 * warning messages for a frequently occurring problem/situation.
	 * 
	 * @param text the message to print.
	 * @param delay the number of milliseconds to wait before another message may be printed.
	 */
	public synchronized static void println(String text,long delay) {
		if(text==null) return;
		if(textToTime==null) 
			textToTime = new Hashtable<String, Long>();
		
		long currentTime = System.currentTimeMillis();
		Long l = textToTime.get(text);
		if(l==null || currentTime>l.longValue()) {
			textToTime.put(text, new Long(currentTime+delay));
			System.err.println(text);
		}
	}
}
