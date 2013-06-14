//Ejemplos de como reducir la dimensionalidad con Relief y GainRatio

package Presenta;

import graphics.Plot3D2;
import redDim.*;
import cluster.implement.hard.KMeans;
import cluster.implement.hard.KMedoids;
import cluster.implement.fuzzy.*;
import clusterUtil.*;
import dataStruct.*;
import distance.EucDist;

public class ReducirDim implements SysConfig{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("------------------------------");
		System.out.println("---Relief - reduc dim ----");
		System.out.println("------------------------------");
		DoubleDataset d=new DoubleDataset(InputOutput.reads(path+"kmIrisRes.csv"));

		double[][] dst;
		RedRelief rr=new RedRelief(d.dataset,3);
		dst=rr.dataRed();
		for(int x=0;x<dst.length;x++){
			System.out.print("|");
			for(int j=0;j<dst[0].length;j++)
				System.out.print(dst[x][j]+"|");
			System.out.println(" ");
		}

		System.out.println(dst.toString());

		System.out.println("------------------------------");
		System.out.println("-----------GainRatioDimRed------------");
		System.out.println("------------------------------");

		GainRatio gr=new GainRatio(d.dataset,3);
		double[][] red=gr.dataRed();
		for(int x=0;x<red.length;x++){
			System.out.print("|");
			for(int j=0;j<red[0].length;j++)
				System.out.print(red[x][j]+"|");
			System.out.println(" ");
		}
	}

}
