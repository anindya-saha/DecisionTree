package ml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Examples {
	private Vector<Example> examples = new Vector<Example>();
	private HashSet<String> categories = new HashSet<String>();
	private Set<String> featureNames = null;

	public void addExample(Example e) {
		examples.add(e);
		categories.add(e.getCategory());
		if (featureNames == null) {
			featureNames = new HashSet<String>(e.getFeatureNames());
		}
	}

	public int getSize() {
		return examples.size();
	}

	public boolean isEmpty() {
		return examples.isEmpty();
	}

	public Example getExample(int i) {
		return examples.get(i);
	}

	public Set<String> getCategories() {
		return new HashSet<String>(categories);
	}

	public Set<String> getFeatureNames() {
		return new HashSet<String>(featureNames);
	}

	public int getExampleCountByCategory(String category) {
		int count = 0;
		for(Example example : examples){
			if (example.getCategory().equals(category)) {
				count++;
			}
		}
		return count;
	}

	public HashMap<String, Examples> partitionByFeatureName(String featureName) {
		HashMap<String, Examples> partition = new HashMap<String, Examples>();
		Set<String> featureValues = this.getFeatureValuesByFeatureName(featureName);
		for(String featureValue : featureValues){
			Examples examples = getExamplesByFeatureNameAndFeatureValue(featureName, featureValue);
			partition.put(featureValue, examples);
		}
		return partition;
	}

	private Set<String> getFeatureValuesByFeatureName(String featureName) {
		HashSet<String> values = new HashSet<String>();
		for(Example example : examples){
			values.add(example.getFeature(featureName).getValue());
		}
		return values;
	}

	private Examples getExamplesByFeatureNameAndFeatureValue(String featureName, String featureValue) {
		Examples result = new Examples();
		for(Example example : examples){
			if(example.getFeature(featureName).getValue().equals(featureValue)){
				result.addExample(example);
			}
		}
		return result;
	}
}
