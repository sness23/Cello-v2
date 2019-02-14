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
package org.cellocad.cello2.logicOptimization.algorithm.data.utils;

import org.cellocad.cello2.common.profile.AlgorithmProfile;
import org.cellocad.cello2.logicOptimization.algorithm.data.LONetlistNodeData;
import org.cellocad.cello2.logicOptimization.algorithm.data.LONetlistNodeDataFactory;
import org.cellocad.cello2.results.netlist.Netlist;
import org.cellocad.cello2.results.netlist.NetlistNode;


/**
 * The LONetlistNodeDataUtils class is class with utility methods for LONetlistNodeData instances in the <i>logicOptimization</i> stage.
 * 
 * @author Vincent Mirian
 * 
 * @date 2018-05-21
 *
 */
public class LONetlistNodeDataUtils {

	/**
	 * Resets the algorithm data, where the algorithm is defined by parameter <i>AProfile</i>,
	 * for all nodes in the netlist instance defined by parameter <i>netlist</i>
	 *
	 * @param netlist the Netlist
	 * @param AProfile the AlgorithmProfile
	 *
	 */
	static public void resetNetlistNodeData(Netlist netlist, AlgorithmProfile AProfile){
		for (int i = 0; i < netlist.getNumVertex(); i++) {
			NetlistNode node = netlist.getVertexAtIdx(i);
			LONetlistNodeDataUtils.resetNetlistNodeData(node, AProfile);
		}
	}

	/**
	 * Resets the algorithm data, where the algorithm is defined by parameter <i>AProfile</i>,
	 * for a NetlistNode instance defined by parameter <i>node</i>
	 *
	 * @param node the NetlistNode
	 * @param AProfile the AlgorithmProfile
	 *
	 */
	static public void resetNetlistNodeData(NetlistNode node, AlgorithmProfile AProfile){
		LONetlistNodeDataFactory LOFactory = new LONetlistNodeDataFactory();
		LONetlistNodeData data = LOFactory.getNetlistNodeData(AProfile);
		node.setNetlistNodeData(data);
	}
	
}