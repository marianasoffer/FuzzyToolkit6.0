package cluster.implement.fuzzy;

/*
 * Possibilistic clustering arose as a challenge to the probabilistic-like character
 * required for the membership grades in clusters. The limitation of the form does
 * not allow us to distinguish between a situation in which a pattern is shared 
 * between clusters and one in which it is simply atypical and we would like to
 * see this effect quantified in some way. .by kirshampuram and keller 93 y 96.
 it drops the requirement that the sum of membership grades must equal 1.
 It uses a lagrange multiplier para calcular el numero beta que caracteriza el cluster.
 * drops the requirement
 *  that the sum of membership grades must equal 1.
 */

import cluster.FuzzyCluster;
import dataStruct.DoubleDataset;
import dataStruct.ResultFuzzy;
import distance.EucDist;



public class PossCMeans extends FuzzyCluster{

	public double Uk[][];
	public double[] beta;
	
	public PossCMeans(int numClu,DoubleDataset ds,double m){
		super(numClu,ds);
		this.m=m;
		Uk=new double[nItems][numClu];
	}	

	public PossCMeans(int numClu,DoubleDataset ds,double m,boolean supervised){
		super(numClu,ds,supervised);
		this.m=m;
		Uk=new double[nItems][numClu];
	}	

	

    //weighted within sum of squares (have to minimize)
    public double objectiveFunction(){
    	double res=0;
		for(int i=0;i<nItems;i++)
			for(int j=0;j<numberClusters;j++)
				res+=Math.pow(u[i][j],m)*Math.pow(getDistanceKind().calculate(ds.dataset[i], centroids[j]),2);
		double res2=0,res3=0;;
		for(int j=0;j<numberClusters;j++){
			res2=0;
			for(int i=0;i<nItems;i++)
				res2+=Math.pow(1-u[i][j], m);
			res3+=beta[j]*res2;
		}
    	return res+res3;
    }
 public void computeBeta(){
	
	 for(int i=0;i<numberClusters;i++){
		 double bup=0,bdn=0;
		 for(int j=0;j<nItems;j++){
			 bup+=Math.pow(u[j][i],m)*Math.pow(this.getDistanceKind().calculate(ds.dataset[j], centroids[i]),2);
			 bdn+=Math.pow(u[j][i],m);
		 }
		 	if (bdn<eps)
		 		bdn=eps;
	 		 beta[i]=bup/bdn;
	 }
	}
    
	//Calculo u con lambda (matriz de pertenencia)
 public void calcMatPert(){
	for(int i=0;i<nItems;i++){
		for(int j=0;j<numberClusters;j++){
			double xx=Math.pow(getDistanceKind().calculate(ds.dataset[i],centroids[j]),2)/beta[j];
			xx=Math.pow(xx, 1/(m-1));
			xx+=1;
			if (xx<eps) xx=eps;
				u[i][j] = 1.0 / xx;
			}
		}
	}

	public void initU(){
		double diff;
	
		for(int i=0;i<nItems;i++){
	        double sum = 0.0;
			for(int j=0;j<numberClusters;j++){
				diff=new EucDist().calculate(ds.dataset[i],centroids[j]);
				if (diff == 0) u[j][i] = eps;
				else u[j][i] = diff;
	            sum += u[j][i];
			}
	        double sum2 = 0.0;
			for(int j=0;j<numberClusters;j++){
	            u[j][i] = 1.0 / Math.pow(u[j][i] / sum, 2.0 / (m - 1.0));
	            sum2 += u[j][i];
			}
			for(int j=0;j<numberClusters;j++)
	            u[i][j] = u[j][i] / sum2;
			
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

 	public ResultFuzzy processClusters(){
		beta=new double[numberClusters];
		initCentroidsRand();
		initU();
		double ant=0,now=0;
	    int i = 0;
	    do {
			ant=objectiveFunction();
			computeBeta();
	    	i++;
	        Uk=u.clone();
	        calcMatPert();
	        calcCentroids();
			now=objectiveFunction();
	    }while(i<maxIterations && Math.abs(now-ant)>epsilon);

		clusterInstanceBelongsTo=assignInstanceToCluster(centroids);
	    assCluU(numberClusters);
		computPercentageOfInstanceInClusterc();//porcentaje de instancias en cada cluster
		xieIndex=getXieIndex();
		partitionCoeficient=getPartitionCoeficient();
		classificationEntropy=getClassificationEntropy();
		proportionalCoeficient=getProportionalCoefficient();
		calculateClusterValidityMeasures();
		return transferResults();			
	 
 	}	
	public int classifyInstance(double[] inst){
		int resu=-1;
		double di=Double.MIN_VALUE;
		double[] uTmp=new double[numberClusters];
		ds.dataset[0]=inst.clone();
		for(int j=0;j<numberClusters;j++){
			double xx=Math.pow(getDistanceKind().calculate(ds.dataset[0],centroids[j]),2)/beta[j];
			xx=Math.pow(xx, 1/(m-1));
			xx+=1;
			if (xx<eps) xx=eps;
				uTmp[j] = 1.0 / xx;
			}
 		for (int i=0;i<numberClusters;i++)
 			if (di<uTmp[i]){
 					di=uTmp[i];
 					resu=i;
 				}
 			return resu;
 		}

	
	}	
