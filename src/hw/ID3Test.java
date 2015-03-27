package hw;

import java.io.BufferedReader;
import java.io.FileReader;

public class ID3Test {

	public static void main(String[] args) {
		ExampleSet examples = new ExampleSet();

		try (BufferedReader br = new BufferedReader(new FileReader("tr.txt"))) {

			String line;
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(" ");
				AdoptExample ex = new AdoptExample(temp[4], new AgeProperty(temp[1]), new SexProperty(temp[2]), new BreedProperty(temp[3]));
				examples.addExample(ex);
			}
			
			InformationTheoreticDecisionTreeNode id3 = new InformationTheoreticDecisionTreeNode(examples);
			System.out.println("digraph G {");
			id3.printTree();
			System.out.println("}");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
