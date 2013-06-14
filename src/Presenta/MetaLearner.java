//Clusteriza con todos los posibles numeros de clusters que se le indican en el rango, y luego elije
//el numero de clusters que tiene el menor sse
package Presenta;

import cluster.implement.fuzzy.*;
import cluster.implement.hard.FarthestFirst;
import cluster.implement.hard.KMeans;
import cluster.implement.hard.KMedoids;
import cluster.metaAlgo.NumClusF;
import cluster.metaAlgo.NumClusH;
import clusterUtil.*;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultFuzzy;
import dataStruct.ResultHard;
import distance.EucDist;

public class MetaLearner implements SysConfig{

	/**
	 * @param args
	 */
	
	//Para los algoritmos hard minimiza sse 
	public static void main(String[] args) {
		System.out.println("----------------MetaLearner ClusterNum KMeans hard ---------------");
		DoubleDataset d=new DoubleDataset(InputOutput.reads(path+"iris.csv"));

		KMeans km=new KMeans(2,d);
		km.setDistance(new EucDist());
		NumClusH nc=new NumClusH(2,4,d);
		nc.setCluType(km);
		Result r=(ResultHard)nc.metaProcess();
		r.showData();

		System.out.println("----------------MetaLearner ClusterNum KMedoids hard---------------");
		d=new DoubleDataset(InputOutput.reads(path+"test1.csv"));

		KMedoids kmd=new KMedoids(2,d);
		kmd.setDistance(new EucDist());
		NumClusH ncd=new NumClusH(2,6,d);
		ncd.setCluType(kmd);
		Result r2=(ResultHard)ncd.metaProcess();
		r2.showData();

		System.out.println("----------------MetaLearner ClusterNum CMeans---------------");
		CMeans pcm=new CMeans(2,d,1.25);
		pcm.setDistance(new EucDist());
		NumClusF pnc=new NumClusF(2,5,d);
		pnc.setCluType(pcm);
		Result pr4=(ResultFuzzy)pnc.metaProcess();
		pr4.showData();
		InputOutput.writes(path+"irisMetaCM.csv",pr4.ds,pr4.clusterInstanceBelongsTo, pr4.centroids.length);

	}

}
