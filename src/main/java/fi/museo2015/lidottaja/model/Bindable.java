package fi.museo2015.lidottaja.model;

/*
 * An interface that determines whether a chunk is bindable
 * currently only the BindingTarget class implements this
 * TODO: might refactor this out if not needed
 */
public interface Bindable {
	public void bind(String value);
}
