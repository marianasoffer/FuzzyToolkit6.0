package clusterNominal;

import clusterUtil.*;
import distance.*;
import dataStruct.*;

import java.io.*;
import dataStruct.*;
public abstract class ClusterN implements Serializable{

	public Distance dotDistType;
	public double epsilon=0.1;
	public NominalData nd;
	public ResultN rsn;
	public int nFields;
	public int nItems;
	public int[] cluAssign; //a que cluster pertenece cada instancia
	public double[] cluPerc;//Porcentaje de instancias que tiene cada cluster
	public String [][] centroids;
	public int numClu;
	public int maxIter=100; //catidad maxima de iteracioes del algoritmo
	public double[] maxCluradious;
	public double interCluD=0;
//	public double interCluDNorm=0;
	public double[] intraCluD;
	public double totIntraCluD=0;
	public double SSE=0;

	public ClusterN(){
		
	}
	public ResultN processClusters(){
		ResultN r =null;
		return r;
	}
	public ClusterN(int numClu,NominalData ds){
		this.nd=ds;
		this.nFields=this.nd.ds[0].length;
		this.numClu=numClu;
		this.nItems=this.nd.ds.length;
		cluAssign=new int[nItems];
		centroids=new String[numClu][nFields];
		cluPerc=new double[numClu];
		maxCluradious=new double[numClu];

	}
	
	
	public void setEpsilon(double eps){
		this.epsilon=eps;
	};

	public void setIters(int it){
		this.maxIter=it;
	};

		public void initCentroidsRand(){
		for(int i=0;i<numClu;i++)
			centroids[i]=nd.ds[i].clone();
	}
	
/*	public void initCentroidsRand(){
		
		//Obtengo el vector instanc con los individuos al random elejidos como centroides
		int[] instanc=new int[numClu];
		for(int i=0;i<numClu;i++)
			instanc[i]=-1;
		int cantInd=nItems;
		for(int i=0;i<numClu;)
		{
			double rnd=Math.random()*(cantInd-1);
			int sel=(int)rnd;
			//chequeo si select ya esta en instanc
			boolean esta=false;
			for(int j=0;j<numClu;j++)
				if (instanc[j]==sel) esta=true;
			if (!esta){
				instanc[i]=sel;
				i++;
			}
		}
		//asigno a los centroides los valores de los k iniciales valores de los individuos
		for(int i=0;i<numClu;i++){
			int subi=instanc[i];
				for(int j=0;j<nFields;j++){
					centroids[i][j]=nd.ds[subi][j];
				}			
		}
	}
*/
	public void calcCluPert(){
		for(int i=0;i<numClu;i++)
			cluPerc[i]=0;
		for(int i=0;i<nItems;i++)
			cluPerc[cluAssign[i]]+=1;
		for(int i=0;i<numClu;i++)
			cluPerc[i]=cluPerc[i]*100/(nItems);
	}

	public void setDistance(Distance distTrat){
		this.dotDistType= distTrat;
	}
	public Distance getDistKind() {
		return this.dotDistType;
	}
	public void SaveModel(String fi){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(fi);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
			System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public ResultN resulta(){
		ResultN r=new ResultN();
		r.cluPert=cluAssign;
		r.centroids=centroids;
		r.cluPerc=cluPerc;	
		r.interCluD=this.interCluD;
		r.intraCluD=this.intraCluD.clone();
		r.totIntraCluD=this.totIntraCluD;
		r.nd=nd;
		r.SSE=SSE;
		r.cluAssign=this.cluAssign.clone();
		return r;
	}

	public double[] intraClusterDistance()
	{
		double totalDistanceSummmatory[]=new double[this.numClu];
		for (int i=0;i<numClu;i++){
			double totalValueCuster=0;
			String[] temporaryCentroid=centroids[i].clone();
			for(int j=0;j<nItems;j++)
				if (this.cluAssign[j]==i)
					totalValueCuster+=this.getDistKind().calcular(temporaryCentroid,this.nd.ds[j]);
			totalDistanceSummmatory[i]=totalValueCuster;
		}
		return totalDistanceSummmatory;
	}

	//within cluster sum of squares
	public double SSE()
	{
		double tot=0;
		int numC=centroids.length;
		for (int i=0;i<numC;i++){
			double tot1clu=0;
			for(int j=0;j<nItems;j++)
				if (cluAssign[j]==i)
					tot1clu+=Math.pow(getDistKind().calcular(centroids[i],nd.ds[j]),2);
			tot+=tot1clu;
		}
		return tot;
	}

	public double interCluDist()
	{
		double tot=0;
		int numC=centroids.length;
		for (int i=0;i<numC-1;i++){
			for(int j=i+1;j<numC;j++)
				tot+= getDistKind().calcular(centroids[i],centroids[j]);
		}
		return tot;
	}
		
	
	//asigno cada item al cluster mas cercano
	public int[] assItemAClu(String[][] cent){
		String[] item;
		int[] ass=new int[nItems];
		for(int j=0;j<nItems;j++)
			ass[j]=-1;
    	for(int i=0;i<nItems;i++){
	       	item=nd.ds[i];
			int idc=0;
			double dis=100000000;
			String[] cluc=new String[cent.length];
				for(int k=0;k<cent.length;k++){
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

	public void calcValidation(){
		interCluD=interCluDist();
		intraCluD=this.intraClusterDistance();
		totIntraCluD=0;
		double totIt=0;
		int numC=centroids.length;
		for (int i=0;i<numC;i++)
			totIt+=intraCluD[i];
		totIntraCluD=totIt;
		SSE=SSE();

}
}
