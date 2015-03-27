package ml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TreeNode {
	
	private static int UNIQUE_ID = 0;
	
	private int id;
	private String category = null;
	private String decisionFeatureName = null;
	private HashMap<String, TreeNode> children = new HashMap<String, TreeNode>();

	public TreeNode(Examples examples) {
		this.id = ++UNIQUE_ID;
		generateTree(examples, examples.getFeatureNames());
	}

	protected TreeNode(Examples examples, Set<String> selectionFeatures) {
		this.id = ++UNIQUE_ID;
		generateTree(examples, selectionFeatures);
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public String getCategory() {
		return category;
	}

	public String getDecisionFeatureName() {
		return decisionFeatureName;
	}

	public TreeNode getChild(String featureValue) {
		return children.get(featureValue);
	}

	public void addChild(String propertyValue, TreeNode child) {
		children.put(propertyValue, child);
	}
	
	public String categorize(Example ex) {
		if (children.isEmpty()) {
			return category;
		}
		if (decisionFeatureName == null) {
			return category;
		}

		Feature prop = ex.getFeature(decisionFeatureName);
		TreeNode child = children.get(prop.getValue());
		if (child == null) return null;
		return child.categorize(ex);
	}

	public void generateTree(Examples examples, Set<String> selectionFeatures) {
		
		// All instances are either Yes or No
		if (examples.getCategories().size() == 1) {
			category = examples.getCategories().iterator().next();
			return;
		}

		// empty feature set, return with most common value of target attribute
		if (selectionFeatures.isEmpty()) {
			int categoryCount = 0;
			for(String cat : examples.getCategories()){
				if(examples.getExampleCountByCategory(category) > categoryCount){
					category = cat;
					categoryCount = examples.getExampleCountByCategory(category);
				}
			}
		}
		
		// Begin Recursive Tree generation
		
		// select the best partition through entropy and info gain
		double bestInfoGain = Double.MIN_VALUE;
		HashMap<String, Examples> bestPartition = null;
		
		for(String featureName : selectionFeatures){
			HashMap<String, Examples> partition = examples.partitionByFeatureName(featureName);
			double partitionInfoGain = infoGain(partition, examples);
			if( partitionInfoGain > bestInfoGain) {
				bestPartition = partition;
				bestInfoGain = partitionInfoGain;				
				this.decisionFeatureName = featureName;
			}
		}
		
		// Create children; recursively build tree.
		Set<String> childSelectionFeatureSet = new HashSet<String>(selectionFeatures);
		childSelectionFeatureSet.remove(decisionFeatureName);
		for(String featureName : bestPartition.keySet()){
			Examples childExamples = bestPartition.get(featureName);
			children.put(featureName, new TreeNode(childExamples, childSelectionFeatureSet));
		}
	}

	// Calculate Info Gain
	protected double infoGain(HashMap<String, Examples> partition, Examples examples) {
		double examplesEntropy = entropy(examples);
		int totalSize = examples.getSize();
		double partitionEntropy = 0.0;
		for(String partitionKey : partition.keySet()){
			Examples partitionExamples = partition.get(partitionKey);
			int partitionSize = partitionExamples.getSize();
			partitionEntropy += ((double)partitionSize / totalSize) * entropy(partitionExamples);
		}
		return examplesEntropy - partitionEntropy;
	}
	
	// Calculate Entropy
	private double entropy(Examples examples) throws IllegalArgumentException {
		double entropy = 0.0;
		int totalCount = examples.getSize();
		for(String cat : examples.getCategories()) {
			int catCount = examples.getExampleCountByCategory(cat);
			entropy += -((double)catCount / totalCount) * log2((double)catCount / totalCount);
		}
		return entropy;
	}

	private double log2(double a) {
		return Math.log10(a) / Math.log10(2);
	}
	
	public void printTree() {
		if(this.isLeaf() == false) {
			System.out.println(String.format("attr%d [shape=\"rectangle\", label=\"%s\"]", id, decisionFeatureName));
			for(Map.Entry<String, TreeNode> entry : children.entrySet()){
				TreeNode childNode = entry.getValue();
				childNode.printTree();
				if(childNode.isLeaf() == false){
					System.out.println(String.format("attr%d -> attr%d [label=\"%s\"]", id, childNode.id, entry.getKey()));
				} else {
					System.out.println(String.format("attr%d -> leaf%d [label=\"%s\"]", id, childNode.id, entry.getKey()));
				}
			}
		} else {
			System.out.println(String.format("leaf%d [shape=\"plaintext\", label=\"%s\"]", id, category));
		}
	}
}
