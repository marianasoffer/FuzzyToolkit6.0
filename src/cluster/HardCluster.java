package cluster;

import clusterUtil.ClusterMath;

import dataStruct.*;
import java.io.*;
public abstract class HardCluster extends Cluster implements Serializable{
	
	public double interClusterDistance=0;
	public double[] intraClusterDistance;
	public double totalIntraClusterDistance=0;
	public double[] clusterRadious;
	
	public HardCluster(int numberCluster,DoubleDataset ds,boolean supervised){
		super(numberCluster,ds,supervised);
		intraClusterDistance=new double[numberCluster];
		clusterRadious=new double[numberClusters];
		for(int i=0;i<numberCluster;i++){
			intraClusterDistance[i]=0;
			clusterRadious[i]=0;
		}	
	}
	
	public HardCluster(int numberCluster,DoubleDataset ds){
		super(numberCluster,ds);
		intraClusterDistance=new double[numberCluster];
		clusterRadious=new double[numberClusters];
		for(int i=0;i<numberCluster;i++){
			intraClusterDistance[i]=0;
			clusterRadious[i]=0;
		}

	}
	public HardCluster(){
		
	}
	
	
	public Result results(){
		ResultHard rh=new ResultHard();
		rh.clusterInstanceBelongsTo=clusterInstanceBelongsTo;
		rh.centroids=centroids;
		rh.percentInstanceInCluster=percentInstanceInCluster;	
		rh.interClusterDistance=this.interClusterDistance;
		rh.intraClusterDistance=this.intraClusterDistance.clone();
		rh.maximumRadius=this.maximumRadius;
		rh.averageRadius=this.averageRadius;
		rh.totalIntraClusterDistance=this.totalIntraClusterDistance;
		rh.ds=ds.dataset.clone();
		rh.SSE=SSE;
		rh.clusterInstanceBelongsTo=this.clusterInstanceBelongsTo.clone();
		rh.maximumRadius=this.maximumRadius;
		rh.clusterRadious=this.clusterRadious.clone();
		return rh;
	}

	public double[] intraClusterDistance()
	{
		double totalDistanceSummmatory[]=new double[numberClusters];
		double AcumulatesClusterValue=0;
		for (int i=0;i<numberClusters;i++){
			double totalValueCuster=0;
			double[] temporaryCentroid=centroids[i].clone();
			for(int j=0;j<nItems;j++)
				if (clusterInstanceBelongsTo[j]==i)
					totalValueCuster+=getDistanceKind().calculate(temporaryCentroid,ds.dataset[j]);
			totalDistanceSummmatory[i]=totalValueCuster;
			AcumulatesClusterValue+=totalValueCuster;
		}
		this.totalIntraClusterDistance=AcumulatesClusterValue;
		return totalDistanceSummmatory;
	}

	public double totalIntraClusterDistance()
	{
		double total=0;
		for (int i=0;i<numberClusters;i++){
			double oneClusterDistance=0;
			double[] temporaryCentroid=centroids[i].clone();
			for(int j=0;j<nItems;j++)
				if (clusterInstanceBelongsTo[j]==i)
					oneClusterDistance+=getDistanceKind().calculate(temporaryCentroid,ds.dataset[j]);
			total+=oneClusterDistance;
		}
		return total;
	}


	public double interClusterDistance()
	{
		double total=0;
		int numberClusters=centroids.length;
		for (int i=0;i<numberClusters-1;i++){
			for(int j=i+1;j<numberClusters;j++)
				total+= getDistanceKind().calculate(centroids[i],centroids[j]);
		}
		return total;
	}
		
	public double[] calculateCluRadious(){
		clusterRadious=new double[numberClusters];
		int[] cant=new int[numberClusters];
		for(int i=0;i<numberClusters;i++)
			clusterRadious[i]=0;
		for(int i=0;i<numberClusters;i++)
			cant[i]=0;

		for(int c=0;c<numberClusters;c++){
			for(int it=0;it<nItems;it++)
				if (clusterInstanceBelongsTo[it]==c){
					double dist=this.getDistanceKind().calculate(centroids[c],ds.dataset[it]);
					if (dist>clusterRadious[c])
						clusterRadious[c]=dist;
					cant[c]++;
				}
		}
		return clusterRadious.clone();
	}
	
	public void calculateClusterValidityMeasures(){
		interClusterDistance=interClusterDistance();
		intraClusterDistance=intraClusterDistance();
		totalIntraClusterDistance=totalIntraClusterDistance();
		SSE=SSE();
		maximumRadius=getMaximumRadius();
		averageRadius=getAverageRadius();
		this.clusterRadious=calculateCluRadious();

	}
	public int classifyInstance(double[] inst){
		int clusterResult=-1;
		double storeMinDistance=Double.MAX_VALUE;
		for(int i=0;i<numberClusters;i++){
			double auxiliaryDistance=getDistanceKind().calculate(this.centroids[i],inst);
			if(auxiliaryDistance<storeMinDistance){
				storeMinDistance=auxiliaryDistance;
				clusterResult=i;
			}
		}
		return clusterResult;
	}
	
	//busca primero dentro del radio cual tiene mas instancias de un cluster
	//si no hay busca que corresponda al cluster del que hay una instancia mas cercana
	public int classifyInstinDensityCluster(double[] inst,double rad){
		int closerCluster=-1;
		double minimumClusterDistance=Double.MAX_VALUE;
		int[]instanceNumber=new int[numberClusters];
		double tracksDistance=0;
		for (int i=0;i<numberClusters;i++)	
			instanceNumber[i]=0;
		for(int i=0;i<nItems;i++){
			tracksDistance=getDistanceKind().calculate(ds.dataset[i],inst);
				if(tracksDistance<rad)
					instanceNumber[(int) clusterInstanceBelongsTo[i]]++;
				else
				if(minimumClusterDistance>tracksDistance){
					minimumClusterDistance=tracksDistance;
					closerCluster=clusterInstanceBelongsTo[i];	
					}
				}	

		int bestCluster=-1;
		int indexBestCluster=-1;
		for (int i=0;i<numberClusters;i++){
			if (instanceNumber[i]>bestCluster && instanceNumber[i]>0){
				bestCluster=instanceNumber[i];
				indexBestCluster=i;
			}
		}
		int clust=indexBestCluster;
		if  (clust>-1) clust=closerCluster;
		return closerCluster;
	}
}
