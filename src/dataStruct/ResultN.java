package dataStruct;

import java.io.Serializable;

public class ResultN implements Serializable{
	//asignacion a cada cluster de cada instancia
	public int[] cluPert;	
	public double[] cluPerc;
	public String[][] centroids;
	public NominalData nd;
	public int[] cluAssign;
	public double interCluD;
	public double[] intraCluD;
	public double SSE=0;
	public double totIntraCluD;

	//	private static final long serialVersionUID = 6526471155622776147L;
	

	public void showData(){
		System.out.println("#individuals:"+cluPert.length);
		System.out.println("#fields:"+centroids[0].length);
		System.out.println("------Assigns------------");
		System.out.print("|");
		for(int i=0;i<cluPert.length;i++){
			System.out.print(cluPert[i]);
			System.out.print("|");}
		System.out.println("");
		System.out.println(" ");
		System.out.println("------Centroids------------");
		for(int x=0;x<centroids.length;x++){
			System.out.print("|");
			for(int j=0;j<centroids[0].length;j++)
				System.out.print(centroids[x][j]+"|");
			System.out.println(" ");
		}
		System.out.println("------Porcentajes de instancias en clusters------------");
		for(int x=0;x<cluPerc.length;x++)
			System.out.println(x+":"+cluPerc[x]+"%");
		System.out.println("------How good are the clustering results------------");
		System.out.println("SSE:"+SSE);
		System.out.println("inter cluster distance:"+interCluD);
		System.out.println("Total intra cluster distance:"+totIntraCluD);
		for(int i=0;i<intraCluD.length;i++)
			System.out.println("intra cluster distance for cluster("+i+")"+intraCluD[i]);
	
   }
}