package cluster;

import java.util.*;
import clusterUtil.*;
import distance.*;
import dataStruct.*;
import java.io.*;

import org.math.array.util.Random;
public abstract class Cluster implements Serializable{

	public Distance distanceType;
	public double epsilon=0.1;
	public boolean supervised=false;
	public DoubleDataset ds;
	public DoubleDataset auxiliaryDs;
	public Result resultSet;
	public int nFields;
	public int nItems;
	public double SSE=0;
	public int[] clusterInstanceBelongsTo; //a que cluster pertenece cada instancia
	public double[] percentInstanceInCluster;//Porcentaje de instancias que tiene cada cluster
	public double[][] centroids;
	public int numberClusters;
	public int maxIterations=100; //catidad maxima de iteracioes del algoritmo
	public double maximumRadius=0;
	public double averageRadius=0;

	public Cluster(){
		
	}
	public Result processClusters(){
		Result rresult =null;
		return rresult;
	}
	public Cluster(int numberCluster,DoubleDataset ds,boolean supervised){
		this.nFields=this.ds.dataset[0].length;
		this.nItems=ds.dataset.length;
		this.numberClusters=numberCluster;
	
		if (supervised) {
			this.auxiliaryDs=new DoubleDataset();
			clusterInstanceBelongsTo=new int[nItems];
			this.auxiliaryDs=ds;
			this.ds=new DoubleDataset();
			this.ds.dataset=new double[auxiliaryDs.dataset.length][auxiliaryDs.dataset[0].length-1];
			for(int i=0;i<ds.dataset.length;i++)
				this.ds.dataset[i]=ClusterMath.removeNItemsVect(auxiliaryDs.dataset[i],1).clone();
		}
		else
			this.ds=ds;
		clusterInstanceBelongsTo=new int[nItems];
		centroids=new double[numberClusters][nFields];
		percentInstanceInCluster=new double[numberClusters];
		clusterInstanceBelongsTo=new int[nItems];

	}
	
	public Cluster(int numberCluster,DoubleDataset ds){
		this.ds=ds;
		this.nFields=ds.dataset[0].length;
		this.numberClusters=numberCluster;
		this.nItems=ds.dataset.length;
		clusterInstanceBelongsTo=new int[nItems];
		centroids=new double[numberCluster][nFields];
		percentInstanceInCluster=new double[numberCluster];

	}

	public double getMaximumRadius(){
		Double maximum=Double.MIN_VALUE;
		double tmp=0;
		for(int i=0;i<numberClusters;i++)
			for(int j=0;j<nItems;j++)
				if(clusterInstanceBelongsTo[j]==i){
					tmp=getDistanceKind().calculate(ds.dataset[j],centroids[i]);
					if(tmp>maximum)
						maximum=tmp;
				}	
		return maximum;
	}

	public double getAverageRadius(){
		double maximum=0;
		double distanceSumatory=0;
		for(int i=0;i<numberClusters;i++)
			for(int j=0;j<nItems;j++)
				if(clusterInstanceBelongsTo[j]==i){
					distanceSumatory+=getDistanceKind().calculate(ds.dataset[j],centroids[i]);
					maximum=maximum+1;
				}	
		return distanceSumatory/maximum;
	}
	
	public void ini(int nc,DoubleDataset ds){
		this.ds=ds;
		this.nFields=ds.dataset[0].length;
		this.numberClusters=nc;
		this.nItems=ds.dataset.length;
		clusterInstanceBelongsTo=new int[nItems];
		centroids=new double[numberClusters][nFields];
		percentInstanceInCluster=new double[numberClusters];
	}
//	ResultSet getClusters(Cluster clustering, DoubleDataset misDatos);

	public void setEpsilon(double eps){
		this.epsilon=eps;
	};

	public void setIters(int it){
		Math.rint(numberClusters);
		this.maxIterations=it;
	};

/*	public void initCentroidsRand(){
		
		//Obtengo el vector instanc con los individuos al random elejidos como centroides
		int[] instance=new int[numberClusters];
		for(int i=0;i<numberClusters;i++)
			instance[i]=-1;
		int indexOfSelected=nItems;
		for(int i=0;i<numberClusters;)
		{
			double random=Math.random()*(indexOfSelected-1);
			int selected=(int)random;
			//chequeo si select ya esta en instanc
			boolean wasSelected=false;
			for(int j=0;j<numberClusters;j++)
				if (instance[j]==selected) wasSelected=true;
			if (!wasSelected){
				instance[i]=selected;
				i++;
			}
		}
		//asigno a los centroides los valores de los k iniciales valores de los individuos
		for(int i=0;i<numberClusters;i++){
			int subi=instance[i];
				for(int j=0;j<nFields;j++){
					centroids[i][j]=ds.dataset[subi][j];
				}			
		}
	}
	*/

	//Para probar con asignacion fija
	public void initCentroidsRand(){
		for(int i=0;i<numberClusters;i++)
			centroids[i]=ds.dataset[i].clone();
	}
	
	public void computPercentageOfInstanceInClusterc(){
		for(int i=0;i<centroids.length;i++)
			percentInstanceInCluster[i]=0;
		for(int i=0;i<nItems;i++)
			percentInstanceInCluster[clusterInstanceBelongsTo[i]]+=1;		
		for(int i=0;i<centroids.length;i++)
			percentInstanceInCluster[i]=percentInstanceInCluster[i]*100/(nItems);
	}

	public void setDistance(Distance clusterDistance){
		this.distanceType= clusterDistance;
	}
	public Distance getDistanceKind() {
		return this.distanceType;
	}
	
	public double SSE()
	{
		double total=0;
		int numC=centroids.length;
		for (int i=0;i<numC;i++){
			double calculatedSSE=0;
			for(int j=0;j<nItems;j++)
				if (clusterInstanceBelongsTo[j]==i)
					calculatedSSE+=Math.pow(getDistanceKind().calculate(centroids[i],ds.dataset[j]),2);
			total+=calculatedSSE;
		}
		return total;
	}

	public int[] assignInstanceToCluster(double[][] cent){
		double[] item;
		int[] clusterFromItem=new int[nItems];
		for(int j=0;j<nItems;j++)
			clusterFromItem[j]=-1;
    	for(int i=0;i<nItems;i++){
	       	item=ds.dataset[i].clone();
			int subindex=-1;
			double minimumDistance=100000000;
			double[] auxiliaryCentroid=new double[numberClusters];
				for(int k=0;k<numberClusters;k++){
					auxiliaryCentroid=cent[k].clone();
				double actualDistance=getDistanceKind().calculate(auxiliaryCentroid,item);
				if (actualDistance<minimumDistance){
					minimumDistance=actualDistance;
					subindex=k;
					}
				}
	    		clusterFromItem[i]=subindex;	
    	}
    	return clusterFromItem;
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
}

