//Ejecuta todos los algoritmos Hard
package Presenta;

import cluster.implement.hard.*;
import cluster.implement.fuzzy.*;
import clusterUtil.*;
import dataStruct.*;
import distance.*;

public class SimpleHard implements SysConfig{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("------------------------------");
		System.out.println("-----------Kmeans-------------");
		System.out.println("------------------------------");
		DoubleDataset d=new DoubleDataset(InputOutput.reads(path+"iris.csv"));
		KMeans km=new KMeans(3,d);
		km.setDistance(new EucDist());
		km.setEpsilon(0.1);//seteo la minima variacion que tiene que tener la funcion objetivo para
		//que el algoritmo siga
		Result r=(ResultHard)km.processClusters();
		r.showData();
		InputOutput.writes(path+"kmIrisRes.csv",d.dataset,km.clusterInstanceBelongsTo, km.numberClusters);


		System.out.println("------------------------------");
		System.out.println("-----------Kmedoids-------------");
		System.out.println("------------------------------");
		DoubleDataset d2=new DoubleDataset(InputOutput.reads(path+"test1.csv"));
		KMedoids kmd=new KMedoids(2,d2);
		kmd.setDistance(new ManhatDist());
		Result rkm=(ResultHard)kmd.processClusters();
		rkm.showData();
		InputOutput.writes(path+"fftest8.csv",kmd.ds.dataset,kmd.clusterInstanceBelongsTo, kmd.numberClusters);

		
		System.out.println("------------------------------");
		System.out.println("-----------FarthestFirst-------------");
		System.out.println("------------------------------");
		DoubleDataset dd=new DoubleDataset(InputOutput.reads(path+"test1.csv"));
		FarthestFirst ff=new FarthestFirst(3,dd);
		ff.setDistance(new ManhatDist());
		Result rff=(ResultHard)ff.processClusters();
		rff.showData();
		InputOutput.writes(path+"fftest6.csv",dd.dataset,ff.clusterInstanceBelongsTo, ff.numberClusters);

  		System.out.println("------------------------------");
		System.out.println("-----------Dbscan-------------");
		System.out.println("------------------------------");
		//en el radio 1.1 tiene que haber al menos 2 puntos para seguir en el cluster
		//este metodo de clustering no recibe cantidad de clusters, los asigna solo
		Dbscan db=new Dbscan(dd,3,2);
		db.setDistance(new EucDist());
		Result rdb=(ResultHard)db.processClusters();
		rdb.showData();
		InputOutput.writes(path+"fftest9.csv",db.ds.dataset,db.clusterInstanceBelongsTo,db.numberClusters);


		System.out.println("------------------------------");
		System.out.println("-----------KNN-------------");
		System.out.println("------------------------------");
		KNN m=new KNN(3,dd);
		m.setDistance(new EucDist());
		//seteo la cantidad de nearest neighbours a lost que le doy bola
		m.setNN(3);
		Result re=(ResultHard)m.processClusters();
		re.showData();

		System.out.println("------------------------------");
		System.out.println("-----------SoftKmeans-------------");
		System.out.println("------------------------------");
		SoftKMeans m3=new SoftKMeans(3,d,2);
		m3.setDistance(new EucDist());
		m3.setIters(2);//seteo en 500 la maxima cantidad de iteraciones de el algoritmo
		Result re3=(ResultHard)m3.processClusters();
		re3.showData();
		
	}

}
