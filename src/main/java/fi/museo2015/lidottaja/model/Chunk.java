package fi.museo2015.lidottaja.model;

public interface Chunk {
	public boolean getHasStack();

	public boolean getIsBindable();

	public Chunk clone();

	public void setParent(BindingBlock b);

	public BindingBlock getParent();

	public String toXML();
}
