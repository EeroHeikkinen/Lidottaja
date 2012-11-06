package fi.museo2015.lidottaja.model;

import java.io.Serializable;

public class StringChunk implements Chunk, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private BindingBlock parent;

	StringChunk(String content) {
		this.content = content;
	}

	public String toString() {
		return content;
	}

	@Override
	public boolean getHasStack() {
		return false;
	}

	@Override
	public boolean getIsBindable() {
		// TODO Auto-generated method stub
		return false;
	}

	public StringChunk clone() {
		return new StringChunk(content);
	}

	@Override
	public void setParent(BindingBlock b) {
		parent = b;
	}

	@Override
	public BindingBlock getParent() {
		return parent;
	}

	@Override
	public String toXML() {
		return toString();
	}
}
