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
import org.springframework.xml.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A backing bean for the main hotel search form. Encapsulates the criteria
 * needed to perform a hotel search.
 * 
 * It is expected a future milestone of Spring Web Flow 2.0 will allow
 * flow-scoped beans like this one to hold references to transient services that
 * are restored automatically when the flow is resumed on subsequent requests.
 * This would allow this SearchCriteria object to delegate to the
 * {@link BookingService} directly, for example, eliminating the need for the
 * actions in {@link MainActions}.
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

	@Autowired
	public Mapping(@Value("classpath:/lido-template.xml") Resource template,
			XPathExpression splitExpression) throws IOException,
			ParserConfigurationException, SAXException {
		File templateFile = template.getFile();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(templateFile);
		doc.getDocumentElement().normalize();
		binder = new BindingBlock(doc, splitExpression);
	}

	public Mapping(String value, XPathExpression splitExpression)
			throws IOException, ParserConfigurationException, SAXException {
		try {
			Document doc = loadXMLFromString(value);
			doc.getDocumentElement().normalize();
			binder = new BindingBlock(doc, splitExpression);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public String getXML() {
		return binder.toXML();
	}

	public String getLido() {
		return binder.toString();
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
}