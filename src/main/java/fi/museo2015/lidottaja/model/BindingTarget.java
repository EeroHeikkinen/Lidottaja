package fi.museo2015.lidottaja.model;

import java.io.Serializable;

/*
 * A chunk that is bindable. 
 * This is the type that holds the actual bindings of the mapping.
 */
public class BindingTarget implements Chunk, Bindable, Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String value;
	private String field;
	private int index = 0;
	private boolean required;
	private BindingBlock parent;

	BindingTarget(String name) {
		this.name = name;
	}

	BindingTarget(String name, String description) {
		this.name = name;
		this.description = description;
	}

	BindingTarget(String name, String description, BindingBlock parent) {
		this(name, description);
		this.parent = parent;
	}

	public String toXML() {
		StringBuilder sb = new StringBuilder();
		if (name.isEmpty())
			return null;
		sb.append("<name>" + name + "</name>");
		if (description != null)
			sb.append("<description>" + description + "</description>");
		if (value != null)
			sb.append("<example>" + value + "</example>");
		if (field != null)
			sb.append("<field>" + field + "</field>");

		return "<target>" + sb + "</target>";
	}

	public String toString() {
		if (value != null)
			return value;
		return "";
	}

	public BindingTarget() {
	}

	public String getName() {
		// Until slash
		return name;
	}

	public void setName(String name) {
		// Slashes are forbidden
		name.replaceAll("_", " ");
		this.name = name;
	}

	public String getDescription() {
		if (description == null)
			return name;
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRequired() {
		return this.required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public boolean getHasStack() {
		return false;
	}

	@Override
	public boolean getIsBindable() {
		return true;
	}

	@Override
	public void bind(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public BindingBlock getParent() {
		return parent;
	}

	public BindingTarget clone() {
		BindingTarget c = new BindingTarget();
		c.description = description;
		c.field = field;
		c.name = this.name;
		c.index = this.index + 1;
		c.parent = null;
		c.required = required;
		c.value = value;
		return c;
	}

	public void clear() {
		this.value = null;
		this.field = null;
	}

	@Override
	public void setParent(BindingBlock b) {
		parent = b;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
