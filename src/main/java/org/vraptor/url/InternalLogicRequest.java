package org.vraptor.url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.LogicRequest;

/**
 * Internally seen interface.
 * 
 * @author Guilherme Silveira.
 */
public interface InternalLogicRequest extends LogicRequest {

	HttpServletRequest getRequest();

	HttpServletResponse getResponse();

}
