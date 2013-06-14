package dataStruct;

public class ResultHard extends Result{
	//Within cluster sum of squared errors: 24.287376563159434
	public double interClusterDistance;
	public double[] intraClusterDistance;
	public double totalIntraClusterDistance;
	public double[] clusterRadious;
	public ResultHard()
	{
		super();
	}

	
	public void showData(){
		super.showData();

		System.out.println("------How good are the clustering results------------");
		System.out.println("SSE:"+SSE);
		System.out.println("inter cluster distance:"+interClusterDistance);
		System.out.println("Total intra cluster distance:"+totalIntraClusterDistance);
		for(int i=0;i<intraClusterDistance.length;i++)
			System.out.println("intra cluster distance for cluster("+i+")"+intraClusterDistance[i]);
		System.out.println("Maximum Radius:"+maximumRadius);
		System.out.println("Average Radius:"+averageRadius);
		
	}

}