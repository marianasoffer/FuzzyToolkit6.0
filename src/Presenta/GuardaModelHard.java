//Guardo los modelos de cluster aprendidos del tipo hard

package Presenta;
import cluster.implement.fuzzy.CMeans;
import cluster.implement.hard.Dbscan;
import cluster.implement.hard.KMeans;
import cluster.implement.hard.KNN;

import cluster.implement.hard.SoftKMeans;
import clusterUtil.InputOutput;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultFuzzy;
import dataStruct.ResultHard;
import distance.EucDist;
import clusterUtil.*;
import cluster.Cluster;
import cluster.implement.hard.*;
public class GuardaModelHard implements SysConfig {
			public static void main(String[] args) {
				System.out.println("------------------------------");
				System.out.println("-----------Kmeans-------------");
				System.out.println("------------------------------");
				DoubleDataset d=new DoubleDataset(InputOutput.reads(path+"iris.csv"));
				KMeans km=new KMeans(3,d);
				km.setDistance(new EucDist());
				km.processClusters();

				System.out.println("------------------------------");
				System.out.println("-----------Kmedoids-------------");
				System.out.println("------------------------------");
				KMedoids kmd=new KMedoids(3,d);
				kmd.setDistance(new EucDist());
				kmd.processClusters();
				
				kmd.SaveModel(path+"kmdModel.csv");


		  		System.out.println("------------------------------");
				System.out.println("-----------Dbscan-------------");
				System.out.println("------------------------------");
				//en el radio 1.1 tiene que haber al menos 2 puntos para seguir en el cluster
				//este metodo de clustering no recibe cantidad de clusters, los asigna solo
				Dbscan db=new Dbscan(d,1.1,2);
				db.setDistance(new EucDist());
				Result rdb=(ResultHard)db.processClusters();
				db.SaveModel(path+"dbModel.csv");
	

				System.out.println("------------------------------");
				System.out.println("-----------KNN-------------");
				System.out.println("------------------------------");
				KNN m=new KNN(3,d);
				m.setDistance(new EucDist());
				//seteo la cantidad de nearest neighbours a lost que le doy bola
				m.setNN(4);
				Result re=(ResultHard)m.processClusters();
				m.SaveModel(path+"knnmodel.csv");
				
				
			
				System.out.println("------------------------------");
				System.out.println("-----------SoftKmeans-------------");
				System.out.println("------------------------------");
				SoftKMeans m3=new SoftKMeans(3,d,2);
				m3.setDistance(new EucDist());
				m3.setIters(2);//seteo en 500 la maxima cantidad de iteraciones de el algoritmo
				Result re3=(ResultHard)m3.processClusters();
				m3.SaveModel(path+"skmm.csv");
				re3.showData();
				
			}
				
				
			

	}


