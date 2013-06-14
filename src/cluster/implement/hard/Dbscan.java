package cluster.implement.hard;

import cluster.HardCluster;
import clusterUtil.ClusterMath;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import java.util.*;
public class Dbscan extends HardCluster {
	
	public double radius; //radio en el que tienen que haber al menos n puntos
	public int numPoint;// amount of points needed in the radios;
	public int actualClu=0;
	public Dbscan(DoubleDataset ds,double r,int pts){
		super(5,ds);
		this.radius=r;
		this.numPoint=pts;
	}		
	
	 public int[] changeNumClu(int[] v)	{
		 int max=ClusterMath.max(v);
		 int[] patr=new int[max+1];
		 int indc=0;
		 int cant=0;
		 while(indc<max+1){
			 if(ClusterMath.exists(v,indc)){
				 patr[cant]=indc;
				 cant++;
			 }
			 indc++;
		 }
		 for(int i=0;i<cant;i++){
			 ClusterMath.replace(v, patr[i], i);
		 }
		 numberClusters=cant;
		 return v;
	 }
	void calcCentroids(){
		//tengo assignClu, numClu y ds.dataset
		centroids=new double[numberClusters][nFields];
		int[] cantClu=new int[numberClusters];
		for (int i=0;i<numberClusters;i++)
			for (int j=0;j<nFields;j++)
				centroids[i][j]=0;
		for (int i=0;i<numberClusters;i++)
			cantClu[i]=0;
		for (int i=0;i<nItems;i++){
			int cn=clusterInstanceBelongsTo[i];
			cantClu[cn]++;
			for (int j=0;j<nFields;j++)
				centroids[cn][j]=centroids[cn][j]+ds.dataset[i][j];
		}
		for (int i=0;i<numberClusters;i++)
			for (int j=0;j<nFields;j++)
				centroids[i][j] =centroids[i][j]/cantClu[i];
		
	}

	public void calcCluNum(int[] ac){
		//lo que tengo que hacer es ver cuantos clusters hay 
		//porque por ahi hay clusters saltados y los otros algos no me andan
		//es decir habria que traducir el vector de asignacion para hacer bien
//		el calculo
		for(int i=0;i<nItems;i++)
			clusterInstanceBelongsTo[i]++;
		numberClusters=0;
		for(int i=0;i<nItems;i++)
			if (numberClusters<ac[i])
				numberClusters=ac[i];
		numberClusters++;
	}

	public int[] processPoints(int[] pts,int clun){
		int[] pt=new int [nItems];
		int[] cola=new int [nItems];

		
		for(int i=0;i<pts.length;i++)
			cola[i]=pts[i];
		int lenCola=pts.length;
		while(lenCola>0){
			int actual=cola[lenCola-1];
			lenCola--;
			pt=processPoint(actual);
			if (pt.length>=numPoint){
				for(int j=0;j<pt.length;j++){
					if(clusterInstanceBelongsTo[pt[j]]!=clun){
						cola[lenCola]=pt[j];
						clusterInstanceBelongsTo[pt[j]]=clun;
						lenCola++;
						
					}
				}
			}
		}
		
		
		int cont=0;
		for(int i=0;i<pts.length;i++)
			for(int j=0;j<nItems;j++)
				if (j!=i){
					double dist=getDistanceKind().calculate(ds.dataset[i],ds.dataset[j]);
					if (dist<radius)
						{pt[cont]=j;
						 if (pt[cont]!=j)
							 cont++;
						 clusterInstanceBelongsTo[j]=clun;}
				}
		int[] rx=new int[cont];
		for(int e=0;e<cont;e++)
			rx[e]=pt[e];
		return rx;
	}


	public int [] processPoint(int p){
		int[] pt=new int [nItems];
		int num=0;
		for(int i=0;i<nItems;i++)
			if (p!=i){
				double dist=getDistanceKind().calculate(ds.dataset[i],ds.dataset[p]);
				if (dist<radius)pt[num++]=i;
			}
		int[] pts=new int [num];
		for(int i=0;i<num;i++)
			pts[i]=pt[i];
		return pts;
	}

	public void computPercentageOfInstanceInCluster2(){
		for(int i=0;i<percentInstanceInCluster.length;i++)
			percentInstanceInCluster[i]=0;
		for(int i=0;i<nItems;i++)
			if (clusterInstanceBelongsTo[i]>-1)
				percentInstanceInCluster[clusterInstanceBelongsTo[i]]+=1;		
		for(int i=0;i<percentInstanceInCluster.length;i++)
			percentInstanceInCluster[i]=percentInstanceInCluster[i]*100/(nItems);
	}

	public	Result processClusters(){
		for (int i=0;i<clusterInstanceBelongsTo.length;i++)
			clusterInstanceBelongsTo[i]=-1;
		int cambio=1;
		while (cambio==1){
			cambio=0;
			for(int i=0;i<nItems;i++)
				if (clusterInstanceBelongsTo[i]==-1){
					int[] pts = processPoint(i);
					if (pts.length>=numPoint){
						for(int p=0;p<pts.length;p++)
							clusterInstanceBelongsTo[pts[p]]=actualClu;
						clusterInstanceBelongsTo[i]=actualClu;
						cambio=1;
						processPoints(pts,actualClu);
					}
				}
		}
		calcCluNum(clusterInstanceBelongsTo);
		clusterInstanceBelongsTo=changeNumClu(clusterInstanceBelongsTo);
		this.numberClusters=actualClu+2;
		percentInstanceInCluster=new double[actualClu+2];
		computPercentageOfInstanceInCluster2();// porcentaje de instancias en cada cluster
		calcCentroids();
		//no se si tiene sentido las siguientes medidas ya q el centroide no caracteriza el cluster
		calculateClusterValidityMeasures();
		System.out.println("El primer cluster son los outliers");
		return results();
		
	}
}