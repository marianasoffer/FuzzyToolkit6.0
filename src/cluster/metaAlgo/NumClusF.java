package cluster.metaAlgo;

import dataStruct.DoubleDataset;
import dataStruct.ResultFuzzy;
import dataStruct.ResultHard;

public class NumClusF extends MetaLearn{

	public NumClusF(int min,int max,DoubleDataset ds){
		super(min,max,ds);
	}
	public	ResultFuzzy metaProcess(){
		ResultFuzzy resultado=null;
		this.actualClu=0;
		double xieIndex=0;
		for(int i=minClu;i<maxClu;i++){
			cluType.numberClusters=i;
			cluType.ini(i,ds);
			resultado=(ResultFuzzy)cluType.processClusters();
			if (xieIndex<resultado.xieIndex){
				xieIndex=resultado.xieIndex;
				actualClu=i;
			}	
		}
		cluType.ini(actualClu,ds);
		resultado=(ResultFuzzy)cluType.processClusters();
		return resultado;
	}		
}