package ml;

import java.io.BufferedReader;
import java.io.FileReader;

public class ID3Test {

	public static void main(String[] args) {
		Examples examples = new Examples();

		try (BufferedReader br = new BufferedReader(new FileReader("tr2.txt"))) {

			String line;
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(" ");
				Example ex = new Example(temp[4], new Feature(Feature.AGE, temp[1]), new Feature(Feature.SEX, temp[2]), new Feature(Feature.BREED, temp[3]));
				examples.addExample(ex);
			}
			
			TreeNode id3 = new TreeNode(examples);
			System.out.println("digraph G {");
			id3.printTree();
			System.out.println("}");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
