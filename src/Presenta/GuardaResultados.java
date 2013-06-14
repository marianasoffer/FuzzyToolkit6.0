//guardo resultados de los diversos metodos de clustering
package Presenta;

import cluster.implement.fuzzy.CMeans;
import cluster.implement.hard.KMeans;
import cluster.implement.hard.KMedoids;
import clusterUtil.InputOutput;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultFuzzy;
import dataStruct.ResultHard;
import distance.EucDist;
import clusterUtil.*;

public class GuardaResultados implements SysConfig {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("------------------------------");
		System.out.println("-----------Kmeans-------------");
		System.out.println("------------------------------");
		DoubleDataset d=new DoubleDataset(InputOutput.reads(path+"iris.csv"));
		KMeans km=new KMeans(3,d);
		km.setDistance(new EucDist());
		Result r=(ResultHard)km.processClusters();
		r.showData();
		//paso el nombre del archivo, el dataset,los clusters a los que se asignaron y cant clusters
		InputOutput.writes(path+"kmIrisRes.csv",d.dataset,km.clusterInstanceBelongsTo, km.numberClusters);
		
		System.out.println("------------------------------");
		System.out.println("-----------Cmeans-------------");
		System.out.println("------------------------------");
		DoubleDataset cd=new DoubleDataset(InputOutput.reads(path+"iris.csv"));
		CMeans cm=new CMeans(3,cd,1.24);
		cm.setDistance(new EucDist());
		Result cmr=(ResultFuzzy)cm.processClusters();
		cmr.showData();
		InputOutput.writes(path+"cmIrisRes.csv",cd.dataset,cm.clusterInstanceBelongsTo, cm.numberClusters);
		

	}

}
