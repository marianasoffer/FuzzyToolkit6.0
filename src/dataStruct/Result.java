package dataStruct;
import java.io.*;

public class Result implements Serializable{
	//asignacion a cada cluster de cada instancia
//	public int[] cluPert;	
	public double[] percentInstanceInCluster;
	public double[][] centroids;
	public double[][] ds;
	public int[] clusterInstanceBelongsTo;
	public double maximumRadius=0;
	public double averageRadius=0;
	public double SSE=0;
//	private static final long serialVersionUID = 6526471155622776147L;
	

	public void showData(){
		System.out.println("#individuals:"+ds.length);
		System.out.println("#fields:"+centroids[0].length);
		System.out.println("------Assigns------------");
		System.out.print("|");
		for(int i=0;i<this.clusterInstanceBelongsTo.length;i++){
			System.out.print(this.clusterInstanceBelongsTo[i]);
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
		for(int x=0;x<percentInstanceInCluster.length;x++)
			System.out.println(x+":"+percentInstanceInCluster[x]+"%");

	
   }
}