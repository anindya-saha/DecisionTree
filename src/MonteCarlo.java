import java.util.ArrayList;
import java.util.List;


public class MonteCarlo {

	private double[] ProbC = new double[2];
	private double[][] ProbAC = new double[2][2];
	private double[][] ProbBC = new double[2][2];


	public MonteCarlo(){
		
		// Calculated from Problem 5
		ProbC[0] = 0.6;
		ProbC[1] = 0.4;
		
		ProbAC[0][0] = 0.66;
		ProbAC[0][1] = 0.25;
		ProbAC[1][0] = 0.33;
		ProbAC[1][1] = 0.75;

		ProbBC[0][0] = 0.9;
		ProbBC[0][1] = 0.4;
		ProbBC[1][0] = 0.1;
		ProbBC[1][1] = 0.6;
	}
	
	public void runMC(int numberOfInstances){
		List<Integer> sampleA = new ArrayList<Integer>(numberOfInstances);
		List<Integer> sampleB = new ArrayList<Integer>(numberOfInstances);
		
		int a0b0Count = 0, a0b1Count = 0, a1b0Count = 0, a1b1Count = 0;
		
		for(int instance = 1; instance <= numberOfInstances; instance++){
			
			double pc = randomIntInRange(1, 10) * 0.1;
			
			int a = -1, b= -1;
			if(pc > ProbC[0]){ // c = 1;
				
				double pa = randomIntInRange(1, 10) * 0.1;
				double pb = randomIntInRange(1, 10) * 0.1;
				
				// p(a|c=1)
				if(pa > ProbAC[0][1]) {
					a = 1;
				} else {
					a = 0;
				}
				
				// p(b|c=1)
				if(pb > ProbBC[0][1]) {
					b = 1;
				} else {
					b = 0;
				}
				
				sampleA.add(a);
				sampleB.add(b);
				
			} else { // c = 0;
				
				double pa = randomIntInRange(1, 10) * 0.1;
				double pb = randomIntInRange(1, 10) * 0.1;
				
				// p(a|c=0)
				if(pa > ProbAC[0][0]) {
					a = 1; 
				} else {
					a = 0;
				}
				
				// p(b|c=0)
				if(pb > ProbBC[0][0]) {
					b = 1;
				} else {
					b = 0;
				}
				
				sampleA.add(a);
				sampleB.add(b);
			}
			
			if ((a == 0) && (b == 0)) {
			    a0b0Count++;
	        } else if ((a == 0) && (b == 1)) {
			    a0b1Count++;
	        } else if ((a == 1) && (b == 0)) {
			    a1b0Count++;
	        } else {
			    a1b1Count++;
	        }
		}
		
	    
		int b0Count = a0b0Count + a1b0Count;
		int b1Count = a0b1Count + a1b1Count;
		
		System.out.println(String.format("a=0/b=0 = %f", (double)a0b0Count / b0Count));
		System.out.println(String.format("a=0/b=1 = %f", (double)a0b1Count / b1Count));
		System.out.println(String.format("a=1/b=0 = %f", (double)a1b0Count / b0Count));
		System.out.println(String.format("a=1/b=1 = %f", (double)a1b1Count / b1Count));		
	}
			
	private static int randomInt(int n) {
		return (int) (Math.random() * n);
	}

	private static int randomIntInRange(int min, int max) {
		return randomInt(max + 1 - min) + min;
	}
	
	public static void main(String[] args) {
		MonteCarlo mc = new MonteCarlo();
		mc.runMC(10);
		
		System.out.println();
		mc.runMC(100);
		
		System.out.println();
		mc.runMC(1000);
		
		System.out.println();
		mc.runMC(10000);
	}
}
