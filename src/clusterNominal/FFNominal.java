package clusterNominal;

import dataStruct.*;
import java.util.Random;

import cluster.*;
import dataStruct.*;
import distance.*;
public class FFNominal  extends ClusterN{

	public FFNominal(int numClu,NominalData ds){
			super(numClu,ds);
		}		

	public int[] assItemAClu(String[][] cent){
		String[] item;
		int[] ass=new int[nItems];
		for(int j=0;j<nItems;j++)
			ass[j]=-1;
    	for(int i=0;i<nItems;i++){
	       	item=nd.ds[i];
			int idc=0;
			double dis=100000000;
			String[] cluc=new String[numClu];
				for(int k=0;k<numClu;k++){
					cluc=centroids[k];
					double dist=getDistKind().calcular(cluc, item);
				if (dist<dis){
					dis=dist;
					idc=k;
					}
				}
	    		ass[i]=idc;	

    	}
    	return ass;
  }
public	ResultN processClusters(){
	  String[][] centro=new String[numClu][nFields];

	    initCentroidsRand();
		
		cluAssign=assItemAClu(centro);
		calcCluPert();//porcentaje de instancias en cada cluster
		calcValidation();
		return resulta();
	}	



}