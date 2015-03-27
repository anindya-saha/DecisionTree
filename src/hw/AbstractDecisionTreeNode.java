package hw;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractDecisionTreeNode {
	private static int UNIQUE_ID = 0;
	
	private int id;
	private String category = null;
	private String decisionPropertyName = null;
	private HashMap<String, AbstractDecisionTreeNode> children = new HashMap<String, AbstractDecisionTreeNode>();

	public AbstractDecisionTreeNode(ExampleSet examples) throws IllegalArgumentException {
		this.id = ++UNIQUE_ID;
		induceTree(examples, examples.getPropertyNames());
	}

	protected AbstractDecisionTreeNode(ExampleSet examples, Set<String> selectionProperties) throws IllegalArgumentException {
		this.id = ++UNIQUE_ID;
		induceTree(examples, selectionProperties);
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public String getCategory() {
		return category;
	}

	public String getDecisionProperty() {
		return decisionPropertyName;
	}

	public AbstractDecisionTreeNode getChild(String propertyValue) {
		return children.get(propertyValue);
	}

	public void addChild(String propertyValue, AbstractDecisionTreeNode child) {
		children.put(propertyValue, child);
	}
	
	public String categorize(AbstractExample ex) {
		if (children.isEmpty())
			return category;
		if (decisionPropertyName == null)
			return category;

		AbstractProperty prop = ex.getProperty(decisionPropertyName);
		AbstractDecisionTreeNode child = children.get(prop.getValue());
		if (child == null)
			return null;
		return child.categorize(ex);
	}

	public void induceTree(ExampleSet examples, Set<String> selectionProperties) throws IllegalArgumentException {
		// Case 1: All instances are the same
		// category, the node is a leaf.
		if (examples.getCategories().size() == 1) {
			category = examples.getCategories().iterator().next();
			return;
		}

		// Case 2: Empty example set. Create
		// leaf with no classification.
		if (examples.isEmpty())
			return;
		// Case 3: Empty property set; could not classify.
		if (selectionProperties.isEmpty())
			return;//TODO return with most common value of categories
		// Case 4: Choose test and build subtrees.
		// Initialize by partitioning on first
		// untried property.
		Iterator<String> iter = selectionProperties.iterator();
		String bestPropertyName = iter.next();
		HashMap<String, ExampleSet> bestPartition = examples.partition(bestPropertyName);
		double bestPartitionEvaluation = evaluatePartitionQuality(bestPartition, examples);
		while (iter.hasNext()) {
			String nextProp = iter.next();
			HashMap<String, ExampleSet> nextPart = examples.partition(nextProp);
			double nextPartitionEvaluation = evaluatePartitionQuality(nextPart, examples);
			// Better partition found. Save.
			if (nextPartitionEvaluation > bestPartitionEvaluation) {
				bestPartitionEvaluation = nextPartitionEvaluation;
				bestPartition = nextPart;
				bestPropertyName = nextProp;
			}
		}
		// Create children; recursively build tree.
		this.decisionPropertyName = bestPropertyName;
		Set<String> newSelectionPropSet = new HashSet<String>(selectionProperties);
		newSelectionPropSet.remove(decisionPropertyName);
		iter = bestPartition.keySet().iterator();
		while (iter.hasNext()) {
			String value = iter.next();
			ExampleSet child = bestPartition.get(value);
			children.put(value, createChildNode(child, newSelectionPropSet));
		}
	}

	public void printTree() {
		if(this.isLeaf() == false) {
			System.out.println(String.format("attr%d [shape=\"rectangle\", label=\"%s\"]", id, decisionPropertyName));
			for(Map.Entry<String, AbstractDecisionTreeNode> entry : children.entrySet()){
				AbstractDecisionTreeNode childNode = entry.getValue();
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

	protected abstract double evaluatePartitionQuality(HashMap<String, ExampleSet> part, ExampleSet examples) throws IllegalArgumentException;
	
	protected abstract AbstractDecisionTreeNode createChildNode(ExampleSet examples, Set<String> selectionProperties);	
}
