//Clustering hard y nominal, con la medida de distancia clasica para este tipo de dato

package Presenta;

import redDim.RedRelief;
import clusterNominal.*;
import dataStruct.*;
import clusterUtil.*;
import cluster.implement.hard.*;
import cluster.implement.fuzzy.*;
import clusterUtil.ClusterMath;
import clusterUtil.InputOutput;
import dataStruct.ConfMat;
import dataStruct.DoubleDataset;
import dataStruct.Result;
import dataStruct.ResultFuzzy;
import dataStruct.ResultHard;
import distance.EucDist;
import cluster.*;
import cluster.HardCluster.*;
import javastat.multivariate.PCA;
import distance.*;
import javastat.util.DataManager;

/**
 * @author Mariana Sofer
 * @version 1.0
 * Esta clase sirve para xxxx
 */

import clusterUtil.ClusterMath;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.MaxProductSimilarity;
import net.sf.javaml.utils.LogLikelihoodFunction;
public class CluHardNom implements SysConfig {

	/**
	 * @param args
	 */
	public static void main(String[] args){

		System.out.println("------------------------------");
		System.out.println("-----------ffnominal-------------");
		System.out.println("------------------------------");
		NominalData dn=InputOutput.readNominal(path+"lensRed2.txt");
		FFNominal m3=new FFNominal(2,dn);
		m3.setDistance(new NominDist());
		m3.setIters(50);//seteo en 500 la maxima cantidad de iteraciones de el algoritmo
		ResultN r=m3.processClusters();
		r.showData();
		InputOutput.writesNom(path+"ffNom2NomDist.csv",m3.nd.ds,m3.cluAssign, m3.numClu);

		System.out.println("------------------------------");
		System.out.println("-----------kmNominal-------------");
		System.out.println("------------------------------");
		KMeansNom kmn=new KMeansNom(3,dn);
		kmn.setDistance(new NominDist());
		kmn.setIters(50);//seteo en 500 la maxima cantidad de iteraciones de el algoritmo
		ResultN r1=kmn.processClusters();
		r1.showData();
		InputOutput.writesNom(path+"kmNom2NomDist.csv",kmn.nd.ds,kmn.cluAssign, kmn.numClu);

		System.out.println("------------------------------");
		System.out.println("-----------kmedNom-------------");
		System.out.println("------------------------------");
		KMedNom kmd=new KMedNom(2,dn);
		kmd.setDistance(new NominDist());
		kmd.setIters(50);//seteo en 500 la maxima cantidad de iteraciones de el algoritmo
		ResultN r2=kmd.processClusters();
		r2.showData();
		InputOutput.writesNom(path+"kmed3Nom.txt",kmd.nd.ds,kmd.cluAssign,kmd.centroids.length);

	}
}
