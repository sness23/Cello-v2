/*
 * Copyright (C) 2017 Massachusetts Institute of Technology (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.cellocad.v2.results.netlist;

import java.io.IOException;
import java.io.Writer;
import org.cellocad.v2.common.Utils;
import org.cellocad.v2.common.algorithm.data.NetlistNodeData;
import org.cellocad.v2.common.graph.graph.VertexTemplate;
import org.cellocad.v2.common.json.JsonUtils;
import org.cellocad.v2.common.netlist.data.StageNetlistNodeData;
import org.cellocad.v2.common.profile.ProfileUtils;
import org.cellocad.v2.results.logicSynthesis.LSResultsUtils;
import org.cellocad.v2.results.netlist.data.ResultNetlistNodeData;
import org.json.simple.JSONObject;

/**
 * The NetlistNode class is a class representing the node(s) of the project.
 *
 * @author Vincent Mirian
 * @date 2018-05-21
 */
public class NetlistNode extends VertexTemplate<NetlistEdge> {

  private void setDefault() {
    setResultNetlistNodeData(new ResultNetlistNodeData());
  }

  /** Initializes a newly created {@link NetlistNode}. */
  public NetlistNode() {
    super();
    setDefault();
  }

  /**
   * Initializes a newly created {@link NetlistNode} with its contents set to those of parameter
   * {@code other}.
   *
   * @param other The other NetlistNode.
   */
  public NetlistNode(final NetlistNode other) {
    super(other);
    setDefault();
    setResultNetlistNodeData(new ResultNetlistNodeData(other.getResultNetlistNodeData()));
  }

  /**
   * Initializes a newly created {@link NetlistNode} using the parameter {@code jsonObj}.
   *
   * @param jsonObj The JavaScript Object Notation (JSON) representation of the {@link NetlistNode}
   *     object.
   */
  public NetlistNode(final JSONObject jsonObj) {
    this();
    parse(jsonObj);
  }

  /*
   * Parse
   */
  private void parseName(final JSONObject jsonObj) {
    final String name = ProfileUtils.getString(jsonObj, "name");
    if (name != null) {
      setName(name);
    }
  }

  private void parse(final JSONObject jsonObj) {
    parseName(jsonObj);
    getResultNetlistNodeData().parse(jsonObj);
  }

  /*
   * Inherit
   */
  /**
   * Adds this instance to the source node of the {@link NetlistEdge} defined by parameter {@code
   * e}.
   *
   * @param e The {@link NetlistEdge}.
   */
  @Override
  protected void addMeToSrc(final NetlistEdge e) {
    e.setSrc(this);
  }

  /**
   * Adds this instance to the destination node of the {@link NetlistEdge} defined by parameter
   * {@code e}.
   *
   * @param e The {@link NetlistEdge}.
   */
  @Override
  protected void addMeToDst(final NetlistEdge e) {
    e.setDst(this);
  }

  /**
   * Return a newly created {@link NetlistEdge} object with its contents set to those of parameter
   * {@code e}.
   *
   * @param e The other {@link NetlistEdge}.
   * @return A newly created {@link NetlistEdge} object with its contents set to those of parameter
   *     {@code e}.
   */
  @Override
  public NetlistEdge createT(final NetlistEdge e) {
    NetlistEdge rtn = null;
    rtn = new NetlistEdge(e);
    return rtn;
  }

  /*
   * Write
   */
  /**
   * Returns a string containing the header in JSON format of this instance.
   *
   * @return A string containing the header in JSON format of this instance.
   */
  protected String getJsonHeader() {
    String rtn = "";
    // name
    rtn += JsonUtils.getEntryToString("name", getName());
    return rtn;
  }

  /**
   * Returns a string containing the footer in JSON format of this instance.
   *
   * @return A string containing the footer in JSON format of this instance.
   */
  protected String getJsonFooter() {
    final String rtn = "";
    return rtn;
  }

  /**
   * Writes this instance in JSON format to the writer defined by parameter {@code os} with the
   * number of indents equivalent to the parameter {@code indent}.
   *
   * @param indent The number of indents.
   * @param os The writer.
   * @throws IOException If an I/O error occurs.
   */
  public void writeJson(final int indent, final Writer os) throws IOException {
    String str = null;
    // header
    str = getJsonHeader();
    str = JsonUtils.addIndent(indent, str);
    os.write(str);
    // data
    getResultNetlistNodeData().writeJson(indent, os);
    /*
     * StageNetlistNodeData sdata = this.getStageNetlistNodeData(); if (sdata != null) {
     * sdata.writeJSON(indent, os); } NetlistNodeData data = this.getNetlistNodeData(); if (data !=
     * null) { data.writeJSON(indent, os); }.
     */
    // footer
    str = getJsonFooter();
    str = JsonUtils.addIndent(indent, str);
    os.write(str);
  }

  /*
   * dot file
   */
  /**
   * Returns a string representing the shape of this instance in DOT (graph description language)
   * format.
   *
   * @return A string representing the shape of this instance in DOT (graph description language)
   *     format.
   */
  @Override
  protected String getShape() {
    String rtn = "";
    rtn = "circle";
    if (getResultNetlistNodeData().getPartitionID() < 0) {
      rtn = "octagon";
    }
    if (!(LSResultsUtils.isAllInput(this) || LSResultsUtils.isAllOutput(this))) {
      rtn = "box";
    }
    return rtn;
  }

  /**
   * Returns a string containing this instance in DOT (graph description language) format.
   *
   * @return A string containing this instance in DOT (graph description language) format.
   */
  @Override
  protected String getData() {
    String rtn = "";
    // strip
    rtn += super.getData();
    rtn = rtn.substring(0, rtn.lastIndexOf("\"]" + Utils.getNewLine()));
    // add
    rtn += getResultNetlistNodeData().toString();
    // update
    rtn += "\"]";
    rtn += Utils.getNewLine();
    // add point for fanout
    // TODO: hack this should be in a hypergraph Netlist
    if (getNumOutEdge() > 1) {
      rtn += "\"" + getName() + "Point\" [ shape=point ]";
      rtn += Utils.getNewLine();
      rtn += "\"" + getName() + "\" -> \"" + getName() + "Point\":w";
      rtn += Utils.getNewLine();
    }
    return rtn;
  }

  /*
   * ResultNetlistData
   */
  /**
   * Setter for {@code resultNetlistData}.
   *
   * @param nData The ResultNetlistNodeData to set <i>resultNetlistData</i>.
   */
  public void setResultNetlistNodeData(final ResultNetlistNodeData nData) {
    resultNetlistData = nData;
  }

  /**
   * Getter for {@code resultNetlistData}.
   *
   * @return The ResultNetlistNodeData of this instance.
   */
  public ResultNetlistNodeData getResultNetlistNodeData() {
    return resultNetlistData;
  }

  private ResultNetlistNodeData resultNetlistData;

  /*
   * NetlistData
   */
  /**
   * Setter for {@code netlistData}.
   *
   * @param nData The NetlistNodeData to set <i>netlistData</i>.
   */
  public void setNetlistNodeData(final NetlistNodeData nData) {
    netlistData = nData;
  }

  /**
   * Getter for {@code netlistData}.
   *
   * @return The NetlistNodeData of this instance.
   */
  public NetlistNodeData getNetlistNodeData() {
    return netlistData;
  }

  private NetlistNodeData netlistData;

  /*
   * StageNetlistNodeData
   */
  /**
   * Setter for {@code stageNetlistData}.
   *
   * @param nData The StageNetlistNodeData to set <i>stageNetlistData</i>.
   */
  public void setStageNetlistNodeData(final StageNetlistNodeData nData) {
    stageNetlistData = nData;
  }

  /**
   * Getter for {@code stageNetlistData}.
   *
   * @return The StageNetlistNodeData of this instance.
   */
  public StageNetlistNodeData getStageNetlistNodeData() {
    return stageNetlistData;
  }

  private StageNetlistNodeData stageNetlistData;
}
