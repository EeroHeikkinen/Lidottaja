package fi.museo2015.lidottaja.model;

/*
 * The interface for differents sorts of chunks in the working stack of a mapping.
 */
public interface Chunk {
	public boolean getHasStack();

	public boolean getIsBindable();

	public Chunk clone();

	public void setParent(BindingBlock b);

	public BindingBlock getParent();

	public String toXML();
}
