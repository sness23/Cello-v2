/**
 * Copyright (C) 2017 Massachusetts Institute of Technology (MIT)
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cellocad.cello2.common.stage.runtime.environment;

import org.cellocad.cello2.common.runtime.environment.ArgDescription;

/**
 * The StageArgDescription class is class containing the description of the common command line argument(s) for a stage.
 * 
 * @author Vincent Mirian
 * 
 * @date Nov 20, 2017
 *
 */
public class StageArgDescription extends ArgDescription{

    /*
     * Default
     */
	/**
	 * String representing the ALGORITHMNAME command line argument description
	 */
    final static public String ALGORITHMNAME_DESCRIPTION = "algorithm name for the Stage";
	/**
	 * String representing the STAGENAME command line argument description
	 */
    final static public String STAGENAME_DESCRIPTION = "name of the Stage instance";
	/**
	 * String representing the INPUTNETLIST command line argument description
	 */
    final static public String INPUTNETLIST_DESCRIPTION = "input netlist file for Stage";
	/**
	 * String representing the OUTPUTNETLIST command line argument description
	 */
    final static public String OUTPUTNETLIST_DESCRIPTION = "output netlist file for Stage";
    
}