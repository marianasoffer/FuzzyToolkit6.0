package cluster;

import java.io.Serializable;

import org.apache.commons.math.util.MathUtils;

import dataStruct.*;
import distance.EucDist;
import clusterUtil.*;

public abstract class FuzzyCluster extends Cluster
{
	
	public double[][] u; //grado de pertenencia de cada instacia a c/cluster
	public double m; //nivel de fuzzyness
	public double xieIndex=0;//This index can be interpreted as the
	//ratio of the total within-group variance and the separation of the 
	//cluster centers.
	//measures how good are the clusters
	public double partitionCoeficient;
	public double classificationEntropy;
	public double proportionalCoeficient;
	public double fpi;
	public double eps=0.1;
	//inicializo vectores y variables en el constructor
	public FuzzyCluster(int numberCluster,DoubleDataset ds){
		super(numberCluster,ds);
		u=new double[nItems][numberCluster];
		this.numberClusters=numberCluster;
	}

	public FuzzyCluster(int numberCluster,DoubleDataset ds,boolean supervised){
		super(numberCluster,ds,supervised);
		u=new double[nItems][numberCluster];
		this.numberClusters=numberCluster;

	}

	public int[] assignInstanceToFuzzyCluster(double[][] u){

		int[] clusterFromItem=new int[nItems];
		for(int j=0;j<nItems;j++)
			clusterFromItem[j]=-1;
    	for(int i=0;i<nItems;i++){
			int subindex=-1;
			double maxPert=-1;
				for(int k=0;k<numberClusters;k++){
				if (maxPert<u[i][k]){
					maxPert=u[i][k];
					subindex=k;
					}
				}
	    		clusterFromItem[i]=subindex;	
    	}
    	return clusterFromItem.clone();
  }

	public void assCluU(int numberCluster){
		
		double[] temporaryDistances=new double[numberCluster];
		double totalDistance;
		
		for(int i=0;i<nItems;i++){
			totalDistance=0;
			int maximumBelongingDegree=-1;
			for(int j=0;j<numberCluster;j++){
				double tmp;
				tmp=getDistanceKind().calculate(ds.dataset[i],centroids[j]);
				totalDistance+=tmp;
				temporaryDistances[j]=tmp;
				//en el caso que la distancia sea cero pertenece seguro a ese cluster
				if (tmp==0){
					maximumBelongingDegree=j;
				}
			}
			if(maximumBelongingDegree==-1){
				for(int j=0;j<numberCluster;j++)
					u[i][j]=temporaryDistances[j]/totalDistance;
			}
			else {
				//la instancia pertenece al cluster sureclu todo 0 meno ese cluster
				for(int j=0;j<numberCluster;j++)
					u[i][j]=eps;
				u[i][maximumBelongingDegree]=1-eps*(this.numberClusters-1);
				
			}
		}
	}

	public void initU(){
		double diff;
	
		for(int i=0;i<nItems;i++){
	        double sum = 0.0;
			for(int j=0;j<numberClusters;j++){
				diff=new EucDist().calculate(ds.dataset[i],centroids[j]);
				if (diff == 0) u[i][j] = eps;
				else u[i][j] = diff;
	            sum += u[i][j];
			}
	        double sum2 = 0.0;
			for(int j=0;j<numberClusters;j++){
	            u[i][j] = 1.0 / Math.pow(u[i][j] / sum, 2.0 / (m - 1.0));
	            sum2 += u[i][j];
			}
			for(int j=0;j<numberClusters;j++)
	            u[i][j] = u[i][j] / sum2;
			
		}
	}
	
	public double getXieIndex(){
    	double totUp=0;
    	double totDn=Double.MAX_VALUE;
    	for(int i=0;i<nItems;i++)
    		for(int k=0;k<numberClusters;k++)
    			totUp+=Math.pow(u[i][k],2)*Math.pow(getDistanceKind().calculate(ds.dataset[i],centroids[k]),2);
    	
    	for(int k=0;k<numberClusters;k++)
        	for(int j=0;j<numberClusters;j++)
        		if(k!=j){
        			double dist=Math.pow(getDistanceKind().calculate(centroids[j],centroids[k]),2);
        			if (dist<totDn)
        				totDn=dist;
        		}
        			
    	return totUp/(totDn*nItems);
    }
    public double getPartitionCoeficient(){
    	double tot=0;
    	for(int i=0;i<nItems;i++)
    		for(int k=0;k<numberClusters;k++)
    			tot+=Math.pow(u[i][k],2);
  		return tot/nItems;
 }
    
    public double getClassificationEntropy(){
    	double tot=0;
    	for(int i=0;i<nItems;i++)
    		for(int k=0;k<numberClusters;k++)
    			tot+=u[i][k]*Math.log(u[i][k]);
  		return -1*(tot/nItems);
	}

    public double getProportionalCoefficient(){
    	double storeEquationTerm=0;
    	for(int i=0;i<nItems;i++){
        	double acumulateEquationTerm=Double.MIN_VALUE;
    		for(int k=0;k<numberClusters;k++)
    			if (acumulateEquationTerm<u[i][k])
    				acumulateEquationTerm=u[i][k];
    		storeEquationTerm+=acumulateEquationTerm;
    			}		
    	storeEquationTerm=storeEquationTerm/nItems;
    		return storeEquationTerm;
    		}

	//Crea el objeto donde se guardan los resultados fuzzy 
	//segun valores de matrices resultantes al finalizar el algoritmo
	public ResultFuzzy transferResults(){
		ResultFuzzy rf=new ResultFuzzy();
		rf.percentInstanceInCluster=this.percentInstanceInCluster.clone();
		rf.clusterInstanceBelongsTo=clusterInstanceBelongsTo.clone();
		rf.centroids=centroids;
		rf.u=u;
		rf.xieIndex=xieIndex;
		rf.classificationEntropy=classificationEntropy;
		rf.partitionCoeficient=partitionCoeficient;
		rf.proportionalCoeficient=proportionalCoeficient;
		rf.percentInstanceInCluster=percentInstanceInCluster;
		rf.ds=ds.dataset.clone();
		rf.SSE=SSE;
		rf.fpi=this.fpi;
		rf.maximumRadius=this.maximumRadius;
		return rf;
	}

public double fpi(){
	double result=1-(numberClusters*this.partitionCoeficient-1)/(numberClusters-1);
	return result;
}
public void calculateClusterValidityMeasures(){
	classificationEntropy=this.getClassificationEntropy();
	this.partitionCoeficient=this.getPartitionCoeficient();
	this.proportionalCoeficient=this.getProportionalCoefficient();
	this.maximumRadius=this.getMaximumRadius();
    this.averageRadius=this.getAverageRadius();
	SSE=SSE();
	xieIndex=this.getXieIndex();
	this.fpi=fpi();
}

public int classifyInstance(double[] inst){
    return -1;	
}
}