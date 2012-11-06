package fi.museo2015.lidottaja.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A wrapper for a single mapping
 */

public class Mapping implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private BindingBlock binder;
	private boolean saved;

	public BindingBlock getBinder() {
		return binder;
	}

	public void setBinder(BindingBlock binder) {
		this.binder = binder;
	}

	/*
	 * Generates an initial mapping from a template file. TODO: allow template
	 * file to be supplied from a configuration file?
	 */
	@Autowired
	public Mapping(@Value("classpath:/lido-template.xml") Resource template) {
		try {
			File templateFile = template.getFile();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(templateFile);
			doc.getDocumentElement().normalize();
			binder = new BindingBlock(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Generates a mapping from a string Required for loading from a database.
	 */
	public Mapping(String value) throws IOException,
			ParserConfigurationException, SAXException {
		try {
			Document doc = loadXMLFromString(value);
			doc.getDocumentElement().normalize();
			binder = new BindingBlock(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Support method for converting a string to a DOM Document
	 */
	public static Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * Returns a complete XML representation with all the binding metadata
	 * <target> <block> elements etc
	 */
	public String getXML() {
		return binder.toXML();
	}

	/*
	 * Returns a functionally correct representation of the mapping, without any
	 * of the binding metadata elements
	 */
	public String getLido() {
		return binder.toString();
	}

	/*
	 * Whether running in a detached state eg. is the mapping saved to a
	 * database? Should we update or insert when saving
	 */
	public boolean isSaved() {
		return saved;
	}

	/*
	 * Sets whether running in a detached state
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
}