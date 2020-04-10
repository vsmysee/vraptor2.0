package org.vraptor;

/**
 * The execution result..
 * 
 * @author Guilherme Silveira
 */
public interface Result {

	String getReturnCode();
	
	LogicRequest getLogicRequest();

}
