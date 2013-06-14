
package cluster.implement.hard;
import cluster.*;
import dataStruct.*;

import java.util.*;

public class FarthestFirst extends HardCluster {
	
	
	public FarthestFirst(int numClu,DoubleDataset ds){
		super(numClu,ds);
	}
	
	public FarthestFirst(int numClu,DoubleDataset ds,boolean supervised){
		super(numClu,ds,supervised);
	}
	

	//Inicializacion normal
	double[][] initCentroids(){

	    Random r = new Random();
	    //elio el primero al random y despuse voy elijiendo el mas leajano
	    //de todos los centroides que tengo hasta completar los centroides
	    boolean[] selected = new boolean[nItems];
	    for(int p=0;p<nItems;p++)
	    	selected[p]=false;
	    int firstI = r.nextInt(nItems);
	    //int firstI=0;
	    centroids[0]=ds.dataset[firstI].clone();
	    selected[firstI] = true;
	    for(int i=1;i<numberClusters;i++){ //elijo todos los clusters
	    	int subi=-1;
	    	double dist=Double.MIN_VALUE;
	    	for(int j=0;j<nItems;j++){
	    		if (!selected[j]){
	    		double distaclu=0;
	    		for(int t=0;t<nItems;t++)
	    			if (selected[t])
	    				distaclu=distaclu+getDistanceKind().calculate(ds.dataset[t], ds.dataset[j]);
	    		if (distaclu>dist){
	    			dist=distaclu;
	    			subi=j;
	    		}
	    	}
	    }
	    	centroids[i]=ds.dataset[subi].clone();
	    	selected[subi]=true;
	    	
	    }

	    return centroids;
	}

public	Result processClusters(){
		centroids=initCentroids();
		
		clusterInstanceBelongsTo=assignInstanceToCluster(centroids);
		computPercentageOfInstanceInClusterc();//porcentaje de instancias en cada cluster
		calculateClusterValidityMeasures();
		return results();
	}	


}