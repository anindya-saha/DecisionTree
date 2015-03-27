package hw;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class ExampleSet {
	private Vector<AbstractExample> examples = new Vector<AbstractExample>();
	private HashSet<String> categories = new HashSet<String>();
	private Set<String> propertyNames = null;

	public void addExample(AbstractExample e) throws IllegalArgumentException {
		if (e.getCategory() == null)
			throw new IllegalArgumentException("Example missing categorization.");
		// Check that new example is of same class

		if ((examples.isEmpty()) || e.getClass() == examples.firstElement().getClass()) {
			examples.add(e);
			categories.add(e.getCategory());// as existing examples
			if (propertyNames == null)
				propertyNames = new HashSet<String>(e.getPropertyNames());
		} else
			throw new IllegalArgumentException("All examples must be same type.");

	}

	public int getSize() {
		return examples.size();
	}

	public boolean isEmpty() {
		return examples.isEmpty();
	}

	public AbstractExample getExample(int i) {
		return examples.get(i);
	}

	public Set<String> getCategories() {
		return new HashSet<String>(categories);
	}

	public Set<String> getPropertyNames() {
		return new HashSet<String>(propertyNames);
	}

	// More complex methods to be defined.
	public int getExampleCountByCategory(String cat) throws IllegalArgumentException {
		Iterator<AbstractExample> iter = examples.iterator();
		AbstractExample example;
		int count = 0;
		while (iter.hasNext()) {
			example = iter.next();
			if (example.getCategory().equals(cat))
				count++;
		}
		return count;
	}

	public HashMap<String, ExampleSet> partition(String propertyName) throws IllegalArgumentException {
		HashMap<String, ExampleSet> partition = new HashMap<String, ExampleSet>();
		Set<String> values = getValues(propertyName);
		Iterator<String> iter = values.iterator();
		while (iter.hasNext()) {
			String val = iter.next();
			ExampleSet examples = getExamplesByProperty(propertyName, val);
			partition.put(val, examples);
		}
		return partition;
	}

	private Set<String> getValues(String propName) {
		HashSet<String> values = new HashSet<String>();
		Iterator<AbstractExample> iter = examples.iterator();
		while (iter.hasNext()) {
			AbstractExample ex = iter.next();
			values.add(ex.getProperty(propName).getValue());
		}
		return values;
	}

	private ExampleSet getExamplesByProperty(String propName, String value) throws IllegalArgumentException {
		ExampleSet result = new ExampleSet();
		Iterator<AbstractExample> iter = examples.iterator();
		AbstractExample example;
		while (iter.hasNext()) {
			example = iter.next();
			if (example.getProperty(propName).getValue().equals(value))
				result.addExample(example);
		}
		return result;
	}
}
