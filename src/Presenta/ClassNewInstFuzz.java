//Indica en que cluster fuzzy va una instancia nueva basado en el modelo de cluster previamente 
//aprendido y guardado


package Presenta;

import cluster.implement.fuzzy.CMeans;
import cluster.implement.fuzzy.FuzzyKMedoid;
import cluster.implement.fuzzy.PossCMeans;
import cluster.implement.hard.KNN;
import clusterUtil.InputOutput;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultFuzzy;
import distance.EucDist;
import clusterUtil.*;

public class ClassNewInstFuzz implements SysConfig{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		double[] inst={3,3,2,1};
		
	
		FuzzyKMedoid fkm;
		fkm=(FuzzyKMedoid)InputOutput.ReadModel(path+"fkmModel.csv");

		int clu2=fkm.classifyInstance(inst);
		System.out.println("Cluster del algoritmo fkm para instancia nueva:"+clu2);
		
		PossCMeans pcm;
		pcm=(PossCMeans)InputOutput.ReadModel(path+"pcmModel.csv");

		int clu3=pcm.classifyInstance(inst);
		System.out.println("Cluster del algoritmo pcm para instancia nueva:"+clu3);

		
	
		
		
	}

}
