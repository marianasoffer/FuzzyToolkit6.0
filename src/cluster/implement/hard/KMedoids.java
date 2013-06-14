
package cluster.implement.hard;
//Stadard K medoids
import clusterUtil.*;
import cluster.*;
import dataStruct.*;

//calculo sacando promedio de 
//asigno los centroides al random pa empezarinstancisa del cluster el centroide y
//dpes asigno como centroide la instancia mas cercana
public class KMedoids extends HardCluster {
	
	public KMedoids(int numClu,DoubleDataset ds){
		super(numClu,ds);
	}		

	public KMedoids(int numClu,DoubleDataset ds,boolean supervised){
		super(numClu,ds,supervised);
	}		

 	public Result processClusters(){
		
		double[] intraDist=new double[numberClusters];
		double globIntra;
		double[][] centTemp=new double[numberClusters][nFields];

		initCentroidsRand();
		clusterInstanceBelongsTo=assignInstanceToCluster(centroids);

		intraDist=ClusterMath.cluDist(centroids,ds.dataset, clusterInstanceBelongsTo,getDistanceKind());
		globIntra=ClusterMath.addVect(intraDist);
		double nowIntra;
		boolean swapeo=true;
		while(swapeo){
			swapeo=false;
			boolean sigue=true;
			int[] CluATmp=new int[nFields];
			for(int i=0;i<nItems &&sigue;i++){
				if (!ClusterMath.isCentroid(centroids,ds.dataset[i])){
					for(int j=0;j<numberClusters &&sigue;j++){
						centTemp=centroids.clone();
						centTemp[j]=ds.dataset[i].clone();
						CluATmp=assignInstanceToCluster(centTemp);
						intraDist=ClusterMath.cluDist(centTemp,ds.dataset, CluATmp,getDistanceKind());
						nowIntra=ClusterMath.addVect(intraDist);
						if (nowIntra<globIntra){
							centroids=centTemp.clone();
							clusterInstanceBelongsTo=CluATmp.clone();
							swapeo=true;
							globIntra=nowIntra;
							sigue=false;
						}
					}
				}
			}
			if (swapeo)
				clusterInstanceBelongsTo=assignInstanceToCluster(centroids);
		}
		computPercentageOfInstanceInClusterc();//porcentaje de instancias en cada cluster
		calculateClusterValidityMeasures();
		return results();
	}		


}
