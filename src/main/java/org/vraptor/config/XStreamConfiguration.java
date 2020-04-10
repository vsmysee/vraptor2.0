package org.vraptor.config;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.vraptor.webapp.DefaultWebApplication;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Reads the xml configuration file
 * 
 * @author Guilherme Silveira
 */
public class XStreamConfiguration {

	private static final Logger logger = Logger
			.getLogger(XStreamConfiguration.class);

	private XStream xstream;

	/**
	 * Constructs an xstream reader
	 * 
	 */
	public XStreamConfiguration() {
		this.xstream = new XStream(new DomDriver());
		this.xstream.registerConverter(new ConfigComponentConverter());
		this.xstream.registerConverter(new ConfigFactoryComponentConverter());
		this.xstream.registerConverter(new ConfigConverterConverter());
		this.xstream.registerConverter(new ConfigPluginComponentConverter());
		this.xstream.registerConverter(new ConfigRegexViewManagerConverter());
		this.xstream.alias("factory", ConfigFactoryComponent.class);
		this.xstream.alias("component", ConfigComponent.class);
		this.xstream.alias("plugin", ConfigPluginComponent.class);
		this.xstream.alias("converter", ConfigConverter.class);
		this.xstream.alias("regex-view-manager", ConfigRegexViewManager.class);
	}

	/**
	 * Reads a file and uses it to register vraptor's data in the controller
	 * 
	 * @param stream
	 *            stream
	 * @param controller
	 *            controller
	 * @throws ConfigException
	 *             some configuration error
	 * @throws IOException
	 *             some input exception occurred
	 */
	@SuppressWarnings("unchecked")
	public void readComponents(InputStream stream,
			DefaultWebApplication application) throws ConfigException,
			IOException {
		InputStreamReader reader = new InputStreamReader(stream);
		XStream xstream = getXStream();
		ObjectInputStream in = xstream.createObjectInputStream(reader);
		while (true) {
			try {
				ConfigItem item = (ConfigItem) in.readObject();
				if (item.isComponent()) {
					item.register(application);
				}
			} catch (EOFException e) {
				logger.debug("finished reading file");
				break;
			} catch (ClassNotFoundException e) {
				throw new ConfigException(e.getMessage(), e);
			}
		}
		in.close();
		reader.close();
	}

	/**
	 * Reads all managers
	 * 
	 * @param stream
	 *            stream
	 * @param controller
	 *            controller
	 * @throws ConfigException
	 *             configuration exceptions
	 * @throws IOException
	 *             ioexceptions
	 */
	@SuppressWarnings("unchecked")
	public void readManagers(InputStream stream,
			DefaultWebApplication application) throws ConfigException,
			IOException {
		InputStreamReader reader = new InputStreamReader(stream);
		XStream xstream = getXStream();
		ObjectInputStream in = xstream.createObjectInputStream(reader);
		while (true) {
			try {
				ConfigItem item = (ConfigItem) in.readObject();
				if (item.isManager()) {
					item.register(application);
				}
			} catch (EOFException e) {
				logger.debug("finished reading file");
				break;
			} catch (ClassNotFoundException e) {
				throw new ConfigException(e.getMessage(), e);
			}
		}
		in.close();
		reader.close();
	}

	/**
	 * Writes all items in a config output
	 * 
	 * @param stream
	 *            stream output
	 * @param items
	 *            items
	 * @throws IOException
	 *             output exception
	 */
	@SuppressWarnings("unchecked")
	public void write(OutputStream stream, List<ConfigItem> items)
			throws IOException {
		PrintWriter writer = new PrintWriter(stream);
		XStream xstream = getXStream();
		ObjectOutputStream out = xstream.createObjectOutputStream(writer,
				"vraptor");
		for (ConfigItem item : items) {
			out.writeObject(item);
		}
		out.flush();
		out.close();
		writer.flush();
		writer.close();
	}

	/**
	 * Returns the xstream instance
	 * 
	 * @return the instance
	 */
	private XStream getXStream() {
		return xstream;
	}

}
