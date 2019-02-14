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
package org.cellocad.cello2.logicOptimization.runtime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.cello2.common.netlistConstraint.data.NetlistConstraint;
import org.cellocad.cello2.common.runtime.RuntimeObject;
import org.cellocad.cello2.common.runtime.environment.RuntimeEnv;
import org.cellocad.cello2.common.stage.Stage;
import org.cellocad.cello2.common.target.data.TargetData;
import org.cellocad.cello2.logicOptimization.algorithm.LOAlgorithm;
import org.cellocad.cello2.logicOptimization.algorithm.LOAlgorithmFactory;
import org.cellocad.cello2.logicOptimization.algorithm.data.LONetlistData;
import org.cellocad.cello2.logicOptimization.algorithm.data.LONetlistDataFactory;
import org.cellocad.cello2.logicOptimization.algorithm.data.LONetlistEdgeData;
import org.cellocad.cello2.logicOptimization.algorithm.data.LONetlistEdgeDataFactory;
import org.cellocad.cello2.logicOptimization.algorithm.data.LONetlistNodeData;
import org.cellocad.cello2.logicOptimization.algorithm.data.LONetlistNodeDataFactory;
import org.cellocad.cello2.logicOptimization.common.LOUtils;
import org.cellocad.cello2.logicOptimization.netlist.data.LOStageNetlistData;
import org.cellocad.cello2.logicOptimization.netlist.data.LOStageNetlistEdgeData;
import org.cellocad.cello2.logicOptimization.netlist.data.LOStageNetlistNodeData;
import org.cellocad.cello2.logicOptimization.runtime.environment.LOArgString;
import org.cellocad.cello2.results.netlist.Netlist;
import org.cellocad.cello2.results.netlist.NetlistEdge;
import org.cellocad.cello2.results.netlist.NetlistNode;

/**
 * The LORuntimeObject class is the RuntimeObject class for the <i>logicOptimization</i> stage.
 * 
 * @author Vincent Mirian
 * 
 * @date 2018-05-21
 *
 */
public class LORuntimeObject extends RuntimeObject{

	/**
	 *  Initializes a newly created LORuntimeObject with its <i>stage</i> set to parameter <i>stage</i>,
	 *  its <i>targetData</i> set to parameter <i>targetData</i>,
	 *  its <i>netlist</i> set to parameter <i>netlist</i>, and,
	 *  its <i>runEnv</i> set to parameter <i>runEnv</i>.
	 *  
	 *  @param stage Stage used during execution
	 *  @param targetData TargetData used during execution
	 *  @param netlistConstraint NetlistConstraint used during execution
	 *  @param netlist Netlist used during execution
	 *  @param runEnv RuntimeEnv used during execution
	 *  @throws RuntimeException if any of the parameters are null
	 */
	public LORuntimeObject(
			final Stage stage,
			final TargetData targetData,
			final NetlistConstraint netlistConstraint,
			final Netlist netlist,
			final RuntimeEnv runEnv
			) {
		super(stage, targetData, netlistConstraint, netlist, runEnv);
	}

	/**
	 * 	Prepares the DataFactory for the Netlist, NetlistNode and NetlistEdge of the logicOptimization stage.
	 */
	@Override
	protected void prepareDataFactory() {
		this.setNetlistDataFactory(new LONetlistDataFactory());
		this.setNetlistNodeDataFactory(new LONetlistNodeDataFactory());
		this.setNetlistEdgeDataFactory(new LONetlistEdgeDataFactory());
	}

	
	/**
	 *  Sets the LOStageNetlistData for the logicOptimization stage in parameter <i>netlist</i>
	 *  <b>Note: this method will be deprecated in the future.</b>
	 *  
	 *  @param netlist the <i>netlist</i> of this instance
	 */
	@Override
	protected void setStageNetlistData(Netlist netlist) {
		netlist.setStageNetlistData(new LOStageNetlistData());
	}

	/**
	 *  Sets the LOStageNetlistNodeData for the logicOptimization stage in parameter <i>node</i>
	 *  <b>Note: this method will be deprecated in the future.</b>
	 *  
	 *  @param node a node within the <i>netlist</i> of this instance
	 */
	@Override
	protected void setStageNetlistNodeData(NetlistNode node) {
		node.setStageNetlistNodeData(new LOStageNetlistNodeData());
	}

	/**
	 *  Sets the LOStageNetlistEdgeData for the logicOptimization stage in parameter <i>edge</i>
	 *  <b>Note: method this will be deprecated in the future.</b>
	 *  
	 *  @param edge an edge within the <i>netlist</i> of this instance
	 */
	@Override
	protected void setStageNetlistEdgeData(NetlistEdge edge) {
		edge.setStageNetlistEdgeData(new LOStageNetlistEdgeData());
	}

	/**
	 *  Sets the NetlistData of the appropriate algorithm in parameter <i>netlist</i>
	 *  @param netlist the <i>netlist</i> of this instance
	 */
	@Override
	protected void setNetlistData(Netlist netlist) {
		LONetlistData data = this.getNetlistDataFactory().getNetlistData(this.getAlgorithmProfile());
		netlist.setNetlistData(data);
	}

	/**
	 *  Sets the NetlistNodeData of the appropriate algorithm in parameter <i>node</i>
	 *  @param node a node within the <i>netlist</i> of this instance
	 */
	@Override
	protected void setNetlistNodeData(NetlistNode node) {
		LONetlistNodeData data = this.getNetlistNodeDataFactory().getNetlistNodeData(this.getAlgorithmProfile());
		node.setNetlistNodeData(data);
	}

	/**
	 *  Sets the NetlistEdgeData of the appropriate algorithm in parameter <i>edge</i>
	 *  @param edge an edge within the <i>netlist</i> of this instance
	 */
	@Override
	protected void setNetlistEdgeData(NetlistEdge edge) {
		LONetlistEdgeData data = this.getNetlistEdgeDataFactory().getNetlistEdgeData(this.getAlgorithmProfile());
		edge.setNetlistEdgeData(data);
	}
	
	/**
	 * Returns the path of the Resources directory for the logicOptimization stage
	 * 
	 * @return the path of the Resources directory for the logicOptimization stage
	 *
	 */
	@Override
	protected String getResourcesFilepath() {
		String rtn = null;
		rtn = LOUtils.getResourcesFilepath();
		return rtn;
	}

	/**
	 *  Returns a string representing the OPTIONS command line argument for the logicOptimization stage
	 *  
	 *  @return a string representing the OPTIONS command line argument for the logicOptimization stage
	 */
	@Override
	protected String getOptionsString() {
		return LOArgString.OPTIONS;
	}

	/**
	 * 	Executes the algorithm of the logicOptimization stage.
	 */
	@Override
	protected void runAlgo() {
		// get Algorithm from Factory
		LOAlgorithmFactory AF = new LOAlgorithmFactory();
		LOAlgorithm algo = AF.getAlgorithm(this.getAlgorithmProfile());
		//executeAlgo
		this.executeAlgo(algo);
	}

	/**
	 *  Getter for the LONetlistDataFactory
	 *  @return the LONetlistDataFactory
	 */
	protected LONetlistDataFactory getNetlistDataFactory() {
		return this.netlistDataFactory;
	}
	
	/**
	 *  Setter for the LONetlistDataFactory
	 *  @param netlistDataFactory the LONetlistDataFactory
	 */
	private void setNetlistDataFactory(final LONetlistDataFactory netlistDataFactory) {
		this.netlistDataFactory = netlistDataFactory;
	}
	
	/**
	 *  Getter for the LONetlistNodeDataFactory
	 *  @return the LONetlistNodeDataFactory
	 */
	protected LONetlistNodeDataFactory getNetlistNodeDataFactory() {
		return this.netlistNodeDataFactory;
	}
	
	/**
	 *  Setter for the LONetlistNodeDataFactory
	 *  @param netlistNodeDataFactory the LONetlistNodeDataFactory
	 */
	private void setNetlistNodeDataFactory(final LONetlistNodeDataFactory netlistNodeDataFactory) {
		this.netlistNodeDataFactory = netlistNodeDataFactory;
	}
	
	/**
	 *  Getter for the LONetlistEdgeDataFactory
	 *  @return the LONetlistEdgeDataFactory
	 */
	protected LONetlistEdgeDataFactory getNetlistEdgeDataFactory() {
		return this.netlistEdgeDataFactory;
	}
	
	/**
	 *  Setter for the LONetlistEdgeDataFactor
	 *  @param netlistEdgeDataFactory the LONetlistEdgeDataFactor
	 */
	private void setNetlistEdgeDataFactory(final LONetlistEdgeDataFactory netlistEdgeDataFactory) {
		this.netlistEdgeDataFactory = netlistEdgeDataFactory;
	}
	
	private LONetlistDataFactory netlistDataFactory;
	private LONetlistEdgeDataFactory netlistEdgeDataFactory;
	private LONetlistNodeDataFactory netlistNodeDataFactory;


	/**
	 *  Returns the Logger instance for the <i>logicOptimization</i> stage.
	 *  @return the Logger instance for the <i>logicOptimization</i> stage.
	 */
	protected Logger getLogger() {
		return LORuntimeObject.logger;
	}
	private static final Logger logger = LogManager.getLogger(LORuntimeObject.class.getSimpleName());

}