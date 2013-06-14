//Ejecuta todos los algoritmos fuzzy
package Presenta;

	import cluster.implement.hard.*;
	import clusterUtil.*;
	import dataStruct.ConfMat;
	import dataStruct.DoubleDataset;
	import dataStruct.Result;
import dataStruct.ResultFuzzy;
	import dataStruct.ResultHard;
	import distance.*;
	import cluster.implement.fuzzy.*;
import dataStruct.*;
	public class SimpleFuzzy implements SysConfig{

		public static void main(String[] args) {
			
			DoubleDataset d2=new DoubleDataset(InputOutput.reads(path+"iris.csv"));


			System.out.println("------------------------------");
			System.out.println("-----------Cmeans-------------");
			System.out.println("------------------------------");
			double[][] pru={{ 2,12},{ 4, 9},{ 7,13},{11, 5},{12, 7},{14, 4} };
			DoubleDataset d22=new DoubleDataset(pru);
			CMeans fcm=new CMeans(2,d22,2);
			fcm.setDistance(new EucDist());
			Result cmr=(ResultFuzzy)fcm.processClusters();
			cmr.showData();
			InputOutput.writes(path+"fftest10.csv",fcm.ds.dataset,fcm.clusterInstanceBelongsTo, fcm.numberClusters);

			System.out.println("------------------------------");
			System.out.println("-----------PCmeans-------------");
			System.out.println("------------------------------");
			//paso nivel de fuzzyness
			PossCMeans pfcm=new PossCMeans(3,d2,1.8);
			pfcm.setDistance(new EucDist());
			Result pmr=(ResultFuzzy)pfcm.processClusters();
			pmr.showData();

			
			System.out.println("------------------------------");
			System.out.println("-----------FuzzyKMedoids-------------");
			System.out.println("------------------------------");
			//paso nivel de fuzzyness
			//otra medida de distancia
			FuzzyKMedoid fkm=new FuzzyKMedoid(3,d2,1.8);
			fkm.setDistance(new EucDist());
			Result rfk=(ResultFuzzy)fkm.processClusters();
			rfk.showData();


		}
	}
