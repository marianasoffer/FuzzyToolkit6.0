package cluster.implement.fuzzy;

import cluster.FuzzyCluster;
import dataStruct.DoubleDataset;
import dataStruct.ResultFuzzy;
import distance.EucDist;
//Standard fuzzy Cmeans2
public class FuzzyKMedoid extends FuzzyCluster{
	
	public FuzzyKMedoid(int numClu,DoubleDataset ds,double m){
		super(numClu,ds);
		this.m=m;
	}	

	public FuzzyKMedoid(int numClu,DoubleDataset ds,double m,boolean supervised){
		super(numClu,ds,supervised);
		this.m=m;
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
	

    public void calcCentroids() {
        double[] U = new double[nFields] ;
        double[] L = new double[nFields] ;
    	for(int i=0;i<numberClusters;i++){
    		for(int k=0;k<nFields;k++){
    			U[k]=0;
    			L[k]=0;
    		}	
			for(int j=0;j<nItems;j++){
                double uu = Math.pow(u[j][i], m);
                for(int l=0;l<nFields;l++){
                	U[l]+= uu * ds.dataset[j][l];
                	L[l]+= uu;
                }
			}
			for(int l=0;l<nFields;l++)
				centroids[i][l]=U[l]/L[l];
    	}
    }	
    public void calcMatPert(){	
    	for(int i=0;i<nItems;i++){
    		for(int j=0;j<numberClusters;j++){
    			double sum=0;
    			double xx=getDistanceKind().calculate(ds.dataset[i],centroids[j]);
    			if (xx < eps) xx = eps;
    			for(int t=0;t<numberClusters;t++){
    				double xx2=new EucDist().calculate(ds.dataset[i],centroids[t]);
                    if (xx2 < eps) xx2 = eps;
                    sum += Math.pow(xx/xx2, 2.0 / (m - 1.0));
    			}
                u[i][j] = 1.0 / sum;
    		}
		}
	}

    //weighted within sum of squares (have to minimize)
    public double objectiveFunction(){
    	double res=0;
		for(int i=0;i<nItems;i++)
			for(int j=0;j<numberClusters;j++)
				res+=Math.pow(u[i][j],m)*this.getDistanceKind().calculate(ds.dataset[i], centroids[j]);
    	return res;
    }

    public void ajustaCentroids(){
    	double [][] c=new double[numberClusters][nFields];
    	for(int i=0;i<numberClusters;i++){
    		int min=-1;
    		double dst=Double.MAX_VALUE;
    		for(int j=0;j<nItems;j++){
    			double dist=getDistanceKind().calculate(ds.dataset[j], centroids[i]);
    			if(dist<dst){
    				dst=dist;
    				min=j;
    			}
    		}
    		centroids[i]=ds.dataset[min].clone();
    	}
    }

    public ResultFuzzy processClusters(){
		initCentroidsRand();
		initU();
		double ant=0,now=0;
	    int i = 0;
	    do {
			ant=objectiveFunction();
	    	i++;
	        calcMatPert();
	        calcCentroids();
	        ajustaCentroids();
			now=objectiveFunction();
	    }while(i<maxIterations && Math.abs(now-ant)>epsilon);

		clusterInstanceBelongsTo=assignInstanceToCluster(centroids);
	    assCluU(numberClusters);
		computPercentageOfInstanceInClusterc();//porcentaje de instancias en cada cluster

		calculateClusterValidityMeasures();

		return transferResults();			
	 
		
	}
	public int classifyInstance(double[] inst){
		int resu=-1;
		double di=Double.MIN_VALUE;
		for(int j=0;j<numberClusters;j++){
			double sum=0;
			double xx=getDistanceKind().calculate(inst,centroids[j]);
			if (xx < 1.0) xx = eps;
			for(int t=0;t<numberClusters;t++){
				double xx2=new EucDist().calculate(inst,centroids[t]);
                if (xx2 < 1.0) xx2 = eps;
                sum += Math.pow(xx/xx2, 2.0 / (m - 1.0));
			}
			sum=1.0/sum;
			if (di<sum){
				di=sum;
				resu=j;
			}
		}
		return resu;
	}

}

