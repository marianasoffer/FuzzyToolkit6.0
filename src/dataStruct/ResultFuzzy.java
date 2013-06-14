package dataStruct;

public class ResultFuzzy extends Result{
	
	//grado de pertenencia para fuzzy
	public double[][] u;
	public double xieIndex;
	public double partitionCoeficient;
	public double classificationEntropy;
	public double proportionalCoeficient;
	public double fpi; //fuzzyness performance index
	              
	public ResultFuzzy()
	{
		super();
	}


	public void showData(){
		super.showData();
		System.out.println("------Grado de Pertenencia U------------");
		for(int i=0;i<10&&i<u.length;i++){
			System.out.print("|");
			for(int j=0;j<centroids.length;j++){
				System.out.print(u[i][j]);
				System.out.print("|");
			}
			System.out.println(" ");
		}
		System.out.println("..............");
		System.out.println("------evaluate clustering results------------");
		System.out.println("xieIndex:"+xieIndex);
		System.out.println("Classification Entropy:"+classificationEntropy);
		System.out.println("PartitionCoefficient:"+partitionCoeficient);
		System.out.println("ProportionalCoeficient:"+proportionalCoeficient);
		System.out.println("FPI:"+fpi);
		System.out.println("SSE(HARD):"+SSE);

	}

}

