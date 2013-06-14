//Grafico en 2 dimensiones el clustering de kmeans, pero solo con las 2 primeras dimensiones
//Grafico en 2 dimensiones el clustering de kmeans, dpes de aplicar pca y quedarme con 2 factores

package Presenta;

import graphics.Plot3D2;
import redDim.*;
import cluster.implement.hard.KMeans;
import cluster.implement.hard.KMedoids;
import clusterUtil.ClusterMath;
import clusterUtil.InputOutput;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultHard;
import distance.EucDist;
import clusterUtil.*;
import graphics.*;


public class Grafico2D implements SysConfig{

	public static void main(String[] args) {
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
		PcaRed pc=new PcaRed(instVals,2);
		double[][] red2=pc.dataRed();
		int cantClu=ClusterMath.max(assClu);
		Plot2D2 ppp=new Plot2D2("Retrieved CMeans",red2,assClu,cantClu);
		ppp.show();
		


	}

}
