package hw;

public abstract class AbstractProperty {
	private String value = null;

	public AbstractProperty(String value) throws IllegalArgumentException {
		if (isLegalValue(value) == false)
			throw new IllegalArgumentException(value + "is an illegal Value for Property " + getName());
		this.value = value;
	}

	public final String getValue() {
		return value;
	}

	public abstract boolean isLegalValue(String value);

	public abstract String getName();

	// Enforcing Immutable object pattern
	public final void setValue(String v) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	// Enforcing Immutable object pattern
	public final void setName(String n) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
