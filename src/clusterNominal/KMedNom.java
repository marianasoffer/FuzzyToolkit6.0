package clusterNominal;

import clusterUtil.*;
import cluster.*;
import dataStruct.*;

//calculo sacando promedio de 
//asigno los centroides al random pa empezarinstancisa del cluster el centroide y
//dpes asigno como centroide la instancia mas cercana
public class KMedNom extends ClusterN {
	
	public KMedNom(int numClu,NominalData ds){
		super(numClu,ds);
	}		

	double[] intraD(String[][] centroids,NominalData nd, int[] cluAssign){
		double[] ICluDists=new double[centroids.length];
		
		for(int i=0;i<numClu;i++)
			ICluDists[i]=0;
		
		for(int i=0;i<numClu;i++)
			for(int j=0;j<nItems;j++)
				if(cluAssign[j]==i)
					ICluDists[i]+=this.getDistKind().calcular(centroids[i],nd.ds[j]);
		return ICluDists;

	}
 	public ResultN processClusters(){
		
		double[] intraDist=new double[numClu];
		double globIntra;
		String[][] centTemp=new String[numClu][nFields];

		initCentroidsRand();
		cluAssign=assItemAClu(centroids);

		intraDist=intraD(centroids,nd, cluAssign);
		globIntra=ClusterMath.addVect(intraDist);
		double nowIntra;
		boolean swapeo=true;
		while(swapeo){
			swapeo=false;
			boolean sigue=true;
			int[] CluATmp=new int[nFields];
			for(int i=0;i<nItems &&sigue;i++){
				if (!ClusterMath.isCentroid(centroids,nd.ds[i])){
					for(int j=0;j<numClu &&sigue;j++){
						centTemp=centroids.clone();
						centTemp[j]=nd.ds[i].clone();
						CluATmp=assItemAClu(centTemp);
						intraDist=intraD(centTemp,nd, CluATmp);
						nowIntra=ClusterMath.addVect(intraDist);
						if (nowIntra<globIntra){
							centroids=centTemp.clone();
							cluAssign=CluATmp.clone();
							swapeo=true;
							globIntra=nowIntra;
							sigue=false;
						}
					}
				}
			}
			if (swapeo)
				cluAssign=assItemAClu(centroids);
			calcCluPert();
		}
		calcCluPert();//porcentaje de instancias en cada cluster
		calcValidation();
		return resulta();
	}		


}
