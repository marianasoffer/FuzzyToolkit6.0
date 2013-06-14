package cluster.implement.fuzzy;

import cluster.FuzzyCluster;
import dataStruct.DoubleDataset;
import dataStruct.ResultFuzzy;
import distance.EucDist;
//Standard fuzzy Cmeans2
public class CMeans extends FuzzyCluster{

	
	public CMeans(int numClu,DoubleDataset ds,double m){
		super(numClu,ds);
		this.m=m;
	}	

	public CMeans(int numClu,DoubleDataset ds,double m,boolean supervised){
		super(numClu,ds,supervised);
		this.m=m;
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
    			if (xx <=0) xx = eps;
    			xx=xx*xx;
    			for(int t=0;t<numberClusters;t++){
    				double xx2=new EucDist().calculate(ds.dataset[i],centroids[t]);
                    if (xx2 < eps) xx2 = eps;
                    xx2=xx2*xx2;
                    sum += Math.pow(xx/xx2, 1.0 / (m - 1.0));
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
				res+=Math.pow(u[i][j],m)*Math.pow(getDistanceKind().calculate(ds.dataset[i], centroids[j]),2);
    	return res;
    }

    public ResultFuzzy processClusters(){
    	//centroids[0][0]=5;
    	//centroids[0][1]=5;
    	//centroids[1][0]=10;
    	//centroids[1][1]=10;
    	initCentroidsRand();
		double ant=0,now=0;
	    int i = 0;
	    do {
			ant=objectiveFunction();
	    	i++;
	        calcMatPert();
	        calcCentroids();
			now=objectiveFunction();
	    }while(i<maxIterations && Math.abs(now-ant)>epsilon);

		clusterInstanceBelongsTo=assignInstanceToFuzzyCluster(u);
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

