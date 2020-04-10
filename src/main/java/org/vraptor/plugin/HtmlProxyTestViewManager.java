package org.vraptor.plugin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.scope.RequestContext;
import org.vraptor.view.ViewException;
import org.vraptor.view.ViewManager;

public class HtmlProxyTestViewManager implements ViewManager {

	private static final Logger logger = Logger
			.getLogger(HtmlProxyTestViewManager.class);

	private final ViewManager manager;

	public HtmlProxyTestViewManager(ViewManager viewManager) {
		logger.debug("Creating html proxy view manager");
		this.manager = viewManager;
	}

	public void forward(LogicRequest logicRequest, String result,
			HttpServletRequest request, HttpServletResponse response)
			throws ViewException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		manager.forward(logicRequest, result, request, wrapResponse(response,
				stream));
		writeResult(logicRequest.getRequestContext(), response, stream
				.toByteArray());
	}

	private HttpServletResponse wrapResponse(HttpServletResponse response,
			final OutputStream stream) {
		final ServletOutputStream sos = new ServletOutputStream() {
			@Override
			public void write(int b) throws IOException {
				stream.write(b);
			}
		};
		final PrintWriter writer = new PrintWriter(sos, true);
		return new HttpServletResponseWrapper(response) {
			@Override
			public PrintWriter getWriter() throws IOException {
				return writer;
			}

			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return sos;
			}
		};
	}

	private void writeResult(RequestContext request,
			HttpServletResponse response, byte[] bs) throws ViewException {
		String result = (String) request
				.getAttribute(HtmlTestCreator.CREATOR_ID);
		try {
			OutputStream os = response.getOutputStream();
			PrintWriter writer = new PrintWriter(os, true);
			writer.println(result);
			writer.println("<!-- original result-->");
			os.write(bs);
		} catch (IOException e) {
			throw new ViewException("Unable to output html proxy test.");
		}
	}

	public void directForward(LogicRequest logicRequest, String result,
			HttpServletRequest request, HttpServletResponse response,
			String forwardUrl) throws ViewException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		manager.directForward(logicRequest, result, request, wrapResponse(
				response, stream), forwardUrl);
		writeResult(logicRequest.getRequestContext(), response, stream
				.toByteArray());
	}

}
