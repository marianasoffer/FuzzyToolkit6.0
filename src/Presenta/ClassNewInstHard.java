//Indica en que cluster hard va una instancia nueva basado en el modelo de cluster previamente 
//aprendido y guardado

package Presenta;

import cluster.implement.fuzzy.CMeans;
import cluster.implement.hard.*;
import dataStruct.*;
import clusterUtil.*;

public class ClassNewInstHard implements SysConfig{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		KNN knn;
		knn=(KNN)InputOutput.ReadModel(path+"knnmodel.csv");

		double[] inst4={3,3,2,1};
		int clu4=knn.classifyInstance(inst4);
		System.out.println("Cluster del algoritmo knn para instancia nueva:"+clu4);
		



		

		SoftKMeans skm;
		skm=(SoftKMeans)InputOutput.ReadModel(path+"skmm.csv");

		double[] inst6={3,3,2,1};
		int clu6=skm.classifyInstance(inst6);
		System.out.println("Cluster del algoritmo SoftKMeans para instancia nueva:"+clu6);
		
		
	}
}
