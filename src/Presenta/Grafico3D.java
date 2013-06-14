//Grafico en 3 dimensiones el clustering de kmeans, pero solo con las 3 primeras dimensiones
//Grafico en 3 dimensiones el clustering de kmedoids, dpes de aplicar pca y quedarme con 3 factores
package Presenta;

import graphics.*;
import redDim.PcaRed;
import redDim.PrimCols;
import cluster.implement.hard.*;
import clusterUtil.InputOutput;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultHard;
import distance.EucDist;
import clusterUtil.*;
public class Grafico3D implements SysConfig{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*		// TODO Auto-generated method stub
		System.out.println("------------------------------");
		System.out.println("-----------Kmeans-------------");
		System.out.println("------------------------------");
		DoubleDataset d=new DoubleDataset(clusterUtil.InputOutput.reads(path+"test1.csv"));
		KMeans km=new KMeans(3,d,true);
		km.setDistance(new EucDist());
		Result r=(ResultHard)km.processClusters();
		r.showData();
		double[][] dst;
		PrimCols pr=new PrimCols(d.dataset,3);
		dst=pr.dataRed();

		Plot3D p=new Plot3D("Iiris kmeans grafica 3 primeras Cols",dst,km.clusterInstanceBelongsTo,km.numberClusters);
		p.show();


		System.out.println("-----------Kmedoids------------");
		DoubleDataset x=new DoubleDataset(InputOutput.reads(path+"iris.csv"));
		KMedoids xm=new KMedoids(3,x);
		xm.setDistance(new EucDist());
		Result xr=(ResultHard)xm.processClusters();
		xr.showData();
		PcaRed pca=new PcaRed(x.dataset,3);
		double[][] red=pca.dataRed();

		Plot3D2 pp=new Plot3D2("Iiris kmedoids PCA",red,xm.clusterInstanceBelongsTo,xm.numberClusters);
		pp.show();
		*/
		System.out.println("-----------------------------------------------");
		System.out.println("-----------Previously saved Cmeans-------------");
		System.out.println("-----------------------------------------------");

		DoubleDataset k=new DoubleDataset(InputOutput.reads(path+"kmIrisRes.csv"));
		int ult=k.dataset[0].length;
		ult--;
		//me quedo solo con la ultima fila del dataset que tiene la clase
		double[]resu=ClusterMath.reducToOne(k.dataset,ult);
		//esta fila de double la convierto a integer que es el nro de cluster
		int[] assClu=ClusterMath.doubleToInt(resu);
		//obtengo los valores de las instancias sin el valor de la clase
		PcaRed ppi=new PcaRed(k.dataset,ult);
		double[][] instVals=ppi.dataRed();
		//reduzco la dimensionalidad de los datos de las instancias sin las clases
		PcaRed pc=new PcaRed(instVals,3);
		double[][] red2=pc.dataRed();
		int cantClu=ClusterMath.max(assClu);
		Plot3D2 ppp=new Plot3D2("Retrieved CMeans",red2,assClu,cantClu);
		ppp.show();
		

	

}
}
