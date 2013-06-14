
package Presenta;

import cluster.implement.fuzzy.PossCMeans;
import cluster.implement.hard.KMeans;
import clusterUtil.InputOutput;
import dataStruct.ConfMat;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultFuzzy;
import dataStruct.ResultHard;
import distance.*;
import clusterUtil.*;

public class CompararResultados implements SysConfig{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int[][] rawDat;
		//la funcion readSupResults me devuelve directamente una lista con la asignacion de el
		//algoritmo1 primero y la asignacion del algoritmo2 despues, y en la ultima row indica la
		//cantidad de clusters
		rawDat=InputOutput.readSupResults(path+"cmIrisRescmp.csv");
		ConfMat CM=new ConfMat(rawDat);
		CM.percentCorrectos();
		CM.showData();
		
		
		
	}

}
