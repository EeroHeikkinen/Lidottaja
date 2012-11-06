package fi.museo2015.lidottaja.model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.xml.xpath.NodeMapper;
import org.springframework.xml.xpath.XPathExpression;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BindingBlock implements Chunk, Serializable {

	private static final long serialVersionUID = 1L;
	private BindingBlock parent;
	private List<Chunk> originalStack;
	private List<Chunk> stack;
	private static String splitMarker = "__split__";
	private List<String> bound;

	public BindingBlock(Node node, final XPathExpression splitExpression,
			BindingBlock parent) {
		this(node, splitExpression);
		this.parent = parent;
	}

	public BindingBlock() {

	}

	public BindingBlock(Node node, final XPathExpression splitExpression) {
		final BindingBlock me = this;
		node = node.cloneNode(true);
		List<Chunk> chunks = splitExpression.evaluate(node,
				new NodeMapper<Chunk>() {
					public Chunk mapNode(Node node, int nodeNum)
							throws DOMException {
						Chunk result;

						if (node.getNodeName().equalsIgnoreCase("block")) {
							// Only first node supported
							Node child = node.getFirstChild();
							while (child.getNodeName()
									.equalsIgnoreCase("#text")) {
								child = child.getNextSibling();
								if (child == null)
									// This shouldn't happen ever
									return null;
							}
							result = new BindingBlock(child, splitExpression,
									me);
						} else if (node.getNodeName()
								.equalsIgnoreCase("target")) {
							Element targetElement = (Element) node;
							Element nameElement = (Element) targetElement
									.getElementsByTagName("name").item(0);
							Element descriptionElement = (Element) targetElement
									.getElementsByTagName("description")
									.item(0);
							NodeList fieldElements = targetElement
									.getElementsByTagName("field");
							NodeList exampleElements = targetElement
									.getElementsByTagName("example");
							result = new BindingTarget(nameElement
									.getTextContent(), descriptionElement
									.getTextContent(), me);
							if (fieldElements.getLength() > 0)
								((BindingTarget) result).setField(fieldElements
										.item(0).getTextContent());
							if (exampleElements.getLength() > 0)
								((BindingTarget) result)
										.setValue(exampleElements.item(0)
												.getTextContent());
						} else
							return null;

						// Remove the node and leave a split marker
						// which we will use later
						Node splitNode = node.getOwnerDocument()
								.createTextNode(splitMarker);
						Node parent = node.getParentNode();
						parent.replaceChild(splitNode, node);

						return result;
					}
				});

		String xmlString = this.nodeToXMLString(node);
		List<String> fragments = Arrays.asList(xmlString.split(splitMarker));

		// This should never happen, unless the splitMarker is present in the
		// source XML
		if (fragments.size() != chunks.size() + 1)
			return;

		stack = new ArrayList<Chunk>();
		stack.add(new StringChunk(fragments.get(0)));
		for (int i = 0; i < chunks.size(); i++) {
			stack.add(chunks.get(i));
			stack.add(new StringChunk(fragments.get(i + 1)));
		}

		originalStack = new ArrayList<Chunk>();
		originalStack.addAll(stack);
	}

	public BindingBlock(BindingBlock bindingBlock) {
		this.stack = bindingBlock.getStack();
		this.parent = bindingBlock.getParent();
	}

	public boolean getHasStack() {
		if (stack == null)
			return false;
		if (stack.size() == 0)
			return false;
		return true;
	}

	public void setBound(List<String> bound) {
		this.bound = bound;
	}

	public List<String> getBound() {
		return bound;
	}

	public boolean getIsMapped() {
		for (Chunk bc : stack) {
			if (bc.getIsBindable() == true
					&& ((BindingTarget) bc).getValue() == null)
				return false;
		}
		return true;
	}

	public BindingTarget getFirstTarget() {
		for (Chunk bc : stack) {
			if (bc.getIsBindable() == true)
				return (BindingTarget) bc;
		}
		return null;
	}

	public List<Chunk> bindStack() {
		List<Chunk> boundStack = new ArrayList<Chunk>();
		for (String s : bound) {
			BindingTarget bt = get(0);
			bt.bind(s);
		}
		return boundStack;
	}

	public BindingTarget get(int i) {
		int pos = 0;
		for (Chunk bc : stack) {
			if (bc instanceof BindingTarget) {
				if (pos == i)
					return (BindingTarget) bc;
				else
					pos++;
			}
		}
		return null;
	}

	public List<Chunk> getStack() {

		return stack;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		// Loop through the stack and refer to their respective toString methods
		for (Chunk chunk : stack) {
			sb.append(chunk.toString());
		}
		return sb.toString();
	}

	public static String nodeToXMLString(Node node) {
		// Generate XML String from DOM object
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", new Integer(2));
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			transformer.transform(new DOMSource(node), new StreamResult(baos));
			return baos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BindingBlock getParent() {
		return parent;
	}

	@Override
	public boolean getIsBindable() {
		// TODO Auto-generated method stub
		return false;
	}

	public BindingTarget getTarget(String id) {
		BindingTarget result = null;
		for (Chunk chunk : stack) {
			if (chunk instanceof BindingTarget) {
				result = (BindingTarget) chunk;
				if (result.getName().equalsIgnoreCase(id))
					return result;
			} else if (chunk.getHasStack()) {
				result = ((BindingBlock) chunk).getTarget(id);
				if (result != null)
					return result;
			}
		}
		return null;
	}

	public void addChunkAfter(Chunk chunk, Chunk after) {
		int index = this.stack.indexOf(after);
		if (index <= 0)
			return;
		this.stack.add(index + 1, chunk);
	}

	public void duplicate() {
		BindingBlock copy = this.clone();
		for (Chunk c : copy.getStack()) {
			if (c instanceof BindingTarget) {
				((BindingTarget) c).clear();
			}
		}
		this.parent.addChunkAfter(copy, this);
	}

	public boolean getHasMappedTarget() {
		for (Chunk c : stack) {
			if (c instanceof BindingTarget) {
				BindingTarget t = (BindingTarget) c;
				if (t.getField() != null)
					return true;
			}
		}
		return false;
	}

	public BindingBlock clone() {
		BindingBlock b = new BindingBlock();
		List<Chunk> newStack = new ArrayList<Chunk>();
		for (Chunk c : stack) {
			Chunk clone = c.clone();
			clone.setParent(b);
			newStack.add(clone);
		}
		b.setStack(newStack);
		b.setParent(parent);
		return b;
	}

	public void setStack(List<Chunk> stack) {
		this.stack = stack;
	}

	public void setParent(BindingBlock parent) {
		this.parent = parent;
	}

	/**
	 * toXML: builds a XML
	 */
	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		// Loop through the stack and refer to their respective toXML methods
		for (Chunk chunk : stack) {
			sb.append(chunk.toXML());
		}
		if (parent == null)
			return sb.toString();
		else
			return "<block>" + sb.toString() + "</block>";
	}
}