
package cluster.implement.hard;


import cluster.*;
import clusterUtil.ClusterMath;
import dataStruct.*;

//Standard k-means
public class SoftKMeans extends HardCluster{
	public double[][] w=new double[nItems][numberClusters];//grados de pertenencia
	public double beta;  //medida de rigidez o stiffness

	public SoftKMeans(int numClu,DoubleDataset ds,double beta){
		super(numClu,ds);
		this.beta=beta;
	}		
	public SoftKMeans(int numClu,DoubleDataset ds,double beta,boolean supervised){
		super(numClu,ds,supervised);
		this.beta=beta;
	}		
	//recalculo los centroides	

public void calcuW(){
	double up=0; double down=0;
	int i=0;int j=0;
	for(i=0;i<nItems;i++)
		for( j=0;j<numberClusters;j++)
			w[i][j]=0;
	for( i=0;i<nItems;i++)
		for( j=0;j<numberClusters;j++){
			up=Math.exp(-1*beta*getDistanceKind().calculate(ds.dataset[i], centroids[j]));
				down=0;
				for(int k=0;k<numberClusters;k++)
					down+=Math.exp(-1*beta*getDistanceKind().calculate(ds.dataset[i], centroids[k]));
				if(down==0)
				down=epsilon;
				w[i][j]=up/down;
		}	
}
	
public void recalcCentroids(){
	double[] cent2=new double[nFields];
	double[] centFin=new double[nFields];
	double dn=0,up=0;;
	for(int i=0;i<numberClusters;i++){
		dn=0;up=0;
		for(int f=0;f<nFields;f++)
			centFin[f]=0;
		for(int j=0;j<nItems;j++){
			dn+=w[j][i];
			cent2=ds.dataset[j].clone();
			cent2=clusterUtil.ClusterMath.multVectNum(cent2, w[j][i]);
			centFin=clusterUtil.ClusterMath.sumVectNum(cent2, centFin);
			}
		centFin=clusterUtil.ClusterMath.multVectNum(centFin, 1/dn);
		centroids[i]=centFin.clone();		
		dn=0;

	}

}

public	Result processClusters(){
		initCentroidsRand();
		double ant=0,now=0;
		int iter=0;
		do{
			ant=this.SSE();
			calcuW();
			recalcCentroids();
			now=this.SSE();
			iter++;
	    }while(iter<maxIterations   && Math.abs(now-ant)>epsilon);
		clusterInstanceBelongsTo=assignInstanceToCluster(centroids);
		computPercentageOfInstanceInClusterc();//porcentaje de instancias en cada cluster
		calculateClusterValidityMeasures();
		return results();
	}	



}