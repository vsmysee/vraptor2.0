package org.vraptor.plugin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

/**
 * A creator which uses the client outputstream to show the test result.
 * 
 * @author Guilherme Silveira
 */
public class HtmlTestCreator implements TestCreator {

	private static final Logger logger = Logger
			.getLogger(HtmlTestCreator.class);

	protected static final String CREATOR_ID = HtmlTestCreator.class + "_ID";

	public void execute(Object component, LogicRequest request, String result) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<html>\n<div id=\"test\"><pre>\n");
		buffer.append("\n");
		buffer.append("TestSession session = ENGINE.createSession();\n");
		buffer.append(String.format(
				"Result result = session.execute(\"%s.%s.logic\"", request
						.getComponentName(), request.getLogicName()));
		Map<String, Object> parameters = request.getRequestContext()
				.getParameterMap();
		for (String param : parameters.keySet()) {
			buffer.append(appendParameter(param, parameters.get(param)));
		}
		buffer.append(");\n");
		buffer.append(String.format(
				"assert result.getReturnCode().equals(\"%s\");\n", result));
		buffer.append("</pre></div>\n");
		logger.debug(buffer.toString());
		request.getRequestContext().setAttribute(CREATOR_ID, buffer.toString());
	}

	private String appendParameter(String param, Object object) {
		if (object.getClass().isArray()) {
			String[] values = (String[]) object;
			String result = "";
			for (String value : values) {
				result += appendParameter(param, value);
			}
			return result;
		} else {
			return ",\n\t\"" + param + "=" + object.toString() + "\"";
		}
	}

	public boolean shouldRedirect() {
		return true;
	}

}
