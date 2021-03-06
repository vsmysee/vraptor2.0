package org.vraptor.test;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * Default http session implementation
 * 
 * @author Guilherme Silveira
 */
public class MockedHttpSession implements HttpSession {

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public long getCreationTime() {
		return 0;
	}

	public String getId() {
		return null;
	}

	public long getLastAccessedTime() {
		return 0;
	}

	public ServletContext getServletContext() {
		return null;
	}

	public void setMaxInactiveInterval(int arg0) {
	}

	public int getMaxInactiveInterval() {
		return 0;
	}

	@SuppressWarnings("deprecation")
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return null;
	}

	public Object getValue(String arg0) {
		return null;
	}

	public String[] getValueNames() {
		return null;
	}

	public void putValue(String arg0, Object arg1) {
	}

	public void removeValue(String arg0) {
	}

	public void invalidate() {
	}

	public boolean isNew() {
		return false;
	}

	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}

	public void removeAttribute(String key) {
		this.attributes.remove(key);
	}

	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}

	public Enumeration getAttributeNames() {
		return null;
	}

}
