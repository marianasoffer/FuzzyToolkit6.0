package cluster.metaAlgo;

import clusterUtil.ClusterMath;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultHard;
//maximiza la intra cluster distance
public class NumClusH extends MetaLearn{

	public NumClusH(int min,int max,DoubleDataset ds){
		super(min,max,ds);
	}
	public	Result metaProcess(){
		ResultHard resultado=null;
		this.actualClu=0;
		double tSSE=Double.MAX_VALUE;
		for(int i=minClu;i<maxClu;i++){
			cluType.ini(i,ds);
			resultado=(ResultHard)cluType.processClusters();
			double sum=resultado.SSE;
			if (tSSE>resultado.SSE){
				tSSE=resultado.SSE;
				actualClu=i;
			}	
		}
		cluType.ini(actualClu,ds);
		resultado=(ResultHard)cluType.processClusters();
		return resultado;
	}		
}