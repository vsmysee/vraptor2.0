package org.vraptor.test;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MockedServletContext implements ServletContext {

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public ServletContext getContext(String uripath) {
		return null;
	}

	public int getMajorVersion() {
		return 0;
	}

	public int getMinorVersion() {
		return 0;
	}

	public String getMimeType(String file) {
		return null;
	}

	public Set getResourcePaths(String path) {
		return null;
	}

	public URL getResource(String path) throws MalformedURLException {
		return null;
	}

	public InputStream getResourceAsStream(String path) {
		return null;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		return null;
	}

	public Servlet getServlet(String name) throws ServletException {
		return null;
	}

	public Enumeration getServlets() {
		return null;
	}

	public Enumeration getServletNames() {
		return null;
	}

	public void log(String msg) {
	}

	public void log(Exception exception, String msg) {
	}

	public void log(String message, Throwable throwable) {
	}

	public String getRealPath(String path) {
		return null;
	}

	public String getServerInfo() {
		return null;
	}

	public String getInitParameter(String name) {
		return null;
	}

	public Enumeration getInitParameterNames() {
		return null;
	}

	public String getServletContextName() {
		return null;
	}

	public Object getAttribute(String arg0) {
		return attributes.get(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		attributes.put(arg0, arg1);
	}

	public boolean hasAttribute(String key) {
		return attributes.containsKey(key);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public Enumeration getAttributeNames() {
		return null;
	}

}
