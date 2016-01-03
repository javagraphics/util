/*
 * @(#)PermutationIterator.java
 *
 * $Date: 2015-12-27 03:42:44 +0100 (So, 27 Dez 2015) $
 *
 * Copyright (c) 2015 by Jeremy Wood.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/** This creates all the possible permutations of a collection of objects.
 * For example, given the elements "A", "B", "C", this will return all 6 possible ways to rearrange
 * these letters: ABC, ACB, BAC, BCA, CAB, CBA.
 * 
 * @param <T>
 */
public class PermutationIterator<T> implements Iterator<List<T>>
{
	protected final List<T> allElements;
	protected long ctr = 0;
	protected final long max;
	//scratch variables that we constantly reuse
	private final transient int[] digits;
	private final transient List<T> remaining = new LinkedList<>();
	
	/** Create a PermutationIterator.
	 * 
	 * @param allElements all the elements this iterator will provide permutations of.
	 * Note the number of permutations this iterator creates is the factorial
	 * of the size of this list, which means it gets very large very fast.
	 * The {@link #getFactorial(int)} method will throw an ArithmeticException
	 * if there are over 20 elements. (This logic could be safely rewritten using
	 * BigIntegers to avoid that limitation, but that raises the question: should we?)
	 */
	public PermutationIterator(Collection<T> allElements) {
		this.allElements = new ArrayList<>(allElements);
		max = getFactorial(allElements.size());
		digits = new int[allElements.size()];
	}
	
	/** Return the factorial of the argument.
	 * 
	 * @param k the input value for the factorial function.
	 * @return the factorial of the argument.
	 * @throws ArithmeticException if the factorial is too large to be computed using
	 * longs. Unit tests suggest this occurs around k=21.
	 */
	public static long getFactorial(int k) throws ArithmeticException {
		if(k<0) throw new IllegalArgumentException("the argument must be nonnegative");
		long product = 1;
		if(k!=0) {
			for(int a = 2; a<=k; a++) {
				product *= a;
			}
			
			long t = product;
			for(int a = 2; a<=k; a++) {
				t /= a;
			}
			if(t!=1)
				throw new ArithmeticException("machine error calculating the factorial of "+k);
		}
		return product;
	}

	@Override
	public boolean hasNext()
	{
		return ctr<max;
	}

	@Override
	public List<T> next()
	{
		if(ctr>=max)
			throw new NoSuchElementException();
	
		long v = ctr;
		long currentFactorial = max;
		for(int a = allElements.size(); a>0; a--) {
			currentFactorial /= a;
			digits[allElements.size() - a] = (int)(v/currentFactorial);
			v = v%currentFactorial;
		}
		remaining.addAll(allElements);
		List<T> returnValue = new ArrayList<>(allElements.size());
		for(int a = 0; a<digits.length; a++) {
			returnValue.add( remaining.remove(digits[a]) );
		}
		
		ctr++;
		return returnValue;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
