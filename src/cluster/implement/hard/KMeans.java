package cluster.implement.hard;

import cluster.*;
import dataStruct.*;

public class KMeans extends HardCluster {
	public KMeans(int numClu, DoubleDataset ds) {
		super(numClu, ds);
	}

	public KMeans(int numClu, DoubleDataset ds, boolean supervised) {
		super(numClu, ds, supervised);
	}

	// public int classInst(double[] i){
	//		
	// }
	// recalculo los centroides
	double[][] recalculateCentroids() {

		double[][] centt = new double[numberClusters][nFields];
		int[] totInst = new int[numberClusters];

		for (int i = 0; i < ds.dataset.length; i++) {
			int clun = clusterInstanceBelongsTo[i];
			// sumo los vectores al clu correspond en centt;
			double[] mirow = ds.dataset[i].clone();
			// sumo la row y dpes sumo 1 a la cant de inst en el cluster;
			for (int m = 0; m < nFields; m++)
				centt[clun][m] += mirow[m];
			totInst[clun]++;
		}
		// divido por la cantidad de instancias del cluster para tener el prom
		for (int i = 0; i < numberClusters; i++)
			for (int j = 0; j < nFields; j++)
				centt[i][j] = centt[i][j] / totInst[i];
		return centt;
	}

	public Result processClusters() {

		initCentroidsRand();
		double ant = 0, now = 0;
		int iter = 0;
		do {
			ant = this.SSE();
			clusterInstanceBelongsTo = assignInstanceToCluster(centroids);
			centroids = recalculateCentroids();
			iter++;
			now = this.SSE();

		} while (iter < maxIterations && Math.abs(now - ant) > epsilon);
		computPercentageOfInstanceInClusterc();// porcentaje de instancias en cada cluster
		calculateClusterValidityMeasures();
		return results();
	}

}