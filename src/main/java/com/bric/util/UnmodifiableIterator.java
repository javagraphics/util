/*
 * @(#)UnmodifiableIterator.java
 *
 * $Date: 2014-03-13 09:15:48 +0100 (Do, 13 Mär 2014) $
 *
 * Copyright (c) 2012 by Jeremy Wood.
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

import java.util.Iterator;

/** This is an Iterator that throws an <code>UnsupportedOperationException()</code>
 * when <code>remove()</code> is called. This guarantees the underlying iterator
 * won't be modified.
 */
public class UnmodifiableIterator<T> implements Iterator<T> {

	Iterator<T> iter;
	
	public UnmodifiableIterator(Iterator<T> iter) {
		this.iter = iter;
	}
	
	public boolean hasNext() {
		return iter.hasNext();
	}

	public T next() {
		return iter.next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
