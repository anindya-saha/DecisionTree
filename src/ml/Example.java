package ml;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Example {
	
	public static final String YES = "Y";
	public static final String NO = "N";
	
	private String category = null;
	private Map<String, Feature> features = new HashMap<String, Feature>();

	public Example(String category, Feature... featureList) {
		this.category = category;
		addFeature(featureList);
	}

	private void addFeature(Feature[] featureList) {
		for(Feature feature : featureList){
			features.put(feature.getName(), feature);
		}
	}

	public Feature getFeature(String name) {
		return features.get(name);
	}

	public String getCategory() {
		return category;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(Map.Entry<String, Feature> entry : features.entrySet()){
			sb.append(String.format("%s: %s, ", entry.getKey(), entry.getValue().getValue()));
		}
		sb.append(String.format("Category: %s", category));
		sb.append("]");
		return sb.toString();
	}

	public Set<String> getFeatureNames() {
		Set<String> set = new LinkedHashSet<String>();
		set.add(Feature.AGE);
		set.add(Feature.SEX);
		set.add(Feature.BREED);
		return set;
	}
}
