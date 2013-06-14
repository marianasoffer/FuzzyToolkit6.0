package clusterNominal;


import clusterUtil.*;
import cluster.*;
import dataStruct.*;

public class KMeansNom extends ClusterN {
		public KMeansNom(int numClu,NominalData ds){
			super(numClu,ds);
		}		

		String[][] recalcCentroids(){

			String[][] centt= new String[numClu][nFields];
			for(int i=0;i<numClu;i++)
				for(int j=0;j<nFields;j++)
				centt[i][j]=getCent(i,j);
		return centt;
		}

	public String getCent(int i,int j){
		String val=" ";
		String[] cent=new String[nItems];
		int[] cnt=new int[nItems];
		int next=0;
		for(int c=0;c<nItems;c++)
			cnt[c]=0;
		for(int k=0;k<nItems;k++){
			if (cluAssign[k]==i){
				boolean b=ClusterMath.estaStr(nd.ds[k][j],cent,next);
				if (b){
					for(int p=0;p<next;p++)
						if(cent[p]==nd.ds[k][j])
								cnt[p]++;}
				else{
					cent[next]=nd.ds[k][j];
					cnt[next]++;
					next++;
				}
			}
		}	
		int max=-1;
		for(int k=0;k<next+1;k++){
			if(max<cnt[k]){
				max=cnt[k];
				val=cent[k];
			}
		}
		return val;
	}
	public	ResultN processClusters(){

			initCentroidsRand();
			double ant=0,now=0;
			int iter=0;
			do{
				ant=this.SSE();
				cluAssign=assItemAClu(centroids);
				centroids=recalcCentroids();
				iter++;
				now=this.SSE();
			}while(iter<maxIter && Math.abs(now-ant)>epsilon);
			calcCluPert();//porcentaje de instancias en cada cluster
			calcValidation();
			return resulta();
		}	



	}