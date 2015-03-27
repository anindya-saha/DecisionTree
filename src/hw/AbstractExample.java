package hw;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractExample {
	protected String category = null;
	private Map<String, AbstractProperty> properties = new HashMap<String, AbstractProperty>();

	// Constructor for classified examples
	public AbstractExample(String category, AbstractProperty... propertyList) throws IllegalArgumentException {
		if (isLegalCategory(category) == false)
			throw new IllegalArgumentException(category + "is an illegal category for example.");
		this.category = category;
		addProperties(propertyList);
	}

	// Constructor for unclassified examples
	public AbstractExample(AbstractProperty... propertyList) throws IllegalArgumentException {
		addProperties(propertyList);
	}

	private void addProperties(AbstractProperty[] propertyList) throws IllegalArgumentException {
		Set<String> requiredProps = getPropertyNames();
		// check that all properties are legal
		for (int i = 0; i < propertyList.length; i++) {
			AbstractProperty prop = propertyList[i];
			if (requiredProps.contains(prop.getName()) == false)
				throw new IllegalArgumentException(prop.getName() + "illegal Property for example.");
			properties.put(prop.getName(), prop);
			requiredProps.remove(prop.getName());
		}
		// Check that all legal properties were used
		if (requiredProps.isEmpty() == false) {
			Object[] p = requiredProps.toArray();
			String props = "";
			for (int i = 0; i < p.length; i++)
				props += (String) p[i] + " ";
			throw new IllegalArgumentException("Missing Properties in example: " + props);
		}
	}

	public AbstractProperty getProperty(String name) {
		return properties.get(name);
	}

	public String getCategory() {
		return category;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(Map.Entry<String, AbstractProperty> entry : properties.entrySet()){
			sb.append(String.format("%s: %s, ", entry.getKey(), entry.getValue().getValue()));
		}
		sb.append(String.format("Category: %s", category));
		sb.append("]");
		return sb.toString();
	}

	public abstract Set<String> getPropertyNames();
	
	public abstract boolean isLegalCategory(String value);
}
