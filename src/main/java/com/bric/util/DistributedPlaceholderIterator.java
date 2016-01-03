/*
 * @(#)DistributedPlaceholderIterator.java
 *
 * $Date: 2014-03-13 09:15:48 +0100 (Do, 13 Mär 2014) $
 *
 * Copyright (c) 2013 by Jeremy Wood.
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


public class DistributedPlaceholderIterator {
	protected final int[] placeholders;
	protected final int max;
	protected final boolean includeNegatives;
	
	protected transient int currentMax;
	
	protected final int[] digitCount;
	
	public DistributedPlaceholderIterator(int placeholderCount,int max,boolean includeNegatives) {
		this(placeholderCount, max, 1, includeNegatives);
	}
	
	public DistributedPlaceholderIterator(int placeholderCount,int max,int initialMax,boolean includeNegatives) {
		this.max = max;
		this.currentMax = initialMax;
		this.placeholders = new int[placeholderCount];
		this.includeNegatives = includeNegatives;
		digitCount = new int[max+2];
		reset();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int a = 0; a<placeholders.length; a++) {
			if(a!=0) {
				sb.append(", ");
			}
			sb.append(placeholders[a]);
		}
		sb.append("]");
		return sb.toString();
	}
	
	public int getPlaceholderCount() {
		return placeholders.length;
	}
	
	public int getPlaceholder(int index) {
		return placeholders[index];
	}
	
	private void reset() {
		digitCount[0] = placeholders.length;
		for(int a = 0; a<placeholders.length; a++) {
			placeholders[a] = 0;
		}
		for(int a = 1; a<digitCount.length; a++) {
			digitCount[a] = 0;
		}
	}
	
	private void setValue(int arrayIndex,int newValue) {
		digitCount[placeholders[arrayIndex]]--;
		placeholders[arrayIndex] = newValue;
		digitCount[placeholders[arrayIndex]]++;
	}
	
	public void next() {
		boolean valid = false;
		while(!valid) {
			progress : {
				int index = placeholders.length-1;
				
				while(true) {
					if(includeNegatives) {
						if(placeholders[index]>0) {
							placeholders[index] = -placeholders[index];
							break progress;
						} else if(placeholders[index]<0) {
							placeholders[index] = -placeholders[index];
						}
					}
					setValue(index, placeholders[index]+1);
					if(placeholders[index]<=currentMax) {
						break progress;
					}
					setValue(index, 0);
					index--;

					if(index<0) {
						index = placeholders.length-1;
						currentMax++;
						reset();
					}
				}
			}
			
			valid = digitCount[currentMax]!=0;
		}
	}
	
	public boolean isDone() {
		return (currentMax>max);
	}
}
