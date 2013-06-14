package cluster.implement.hard;

import cluster.*;
import dataStruct.*;

public class KNN extends HardCluster {
	int nn = 1; // cantidad de vecinos

	public KNN(int numClu, DoubleDataset ds) {
		super(numClu, ds);
	}

	public KNN(int numClu, DoubleDataset ds, boolean supervised) {
		super(numClu, ds, supervised);
	}

	public void setNN(int n) {
		nn = n;
	}

	public double[] shiftFromN(double[] dis, int n) {
		double[] r = new double[nn];
		r = dis.clone();
		for (int j = n; j < nn - 1; j++)
			r[j + 1] = dis[j];
		return r;
	}

	public int[] shiftFromN(int[] dis, int n) {
		int[] r = new int[nn];
		r = dis.clone();
		for (int j = n; j < nn - 1; j++)
			r[j + 1] = dis[j];
		return r;
	}

	public int[] reAssItCl() {
		int[] as = new int[nItems];
		double[] dis = new double[nn];
		int[] cluCou = new int[numberClusters];
		int[] clun = new int[nn];
		int indice = 0;
		for (int i = 0; i < nItems; i++) {
			for (int j = 0; j < nn; j++)
				dis[j] = Double.MAX_VALUE;
			for (int j = 0; j < nItems; j++)
				if (j != i) {
					double dist = getDistanceKind().calculate(ds.dataset[i],
							ds.dataset[j]);
					for (int k = 0; k < nn; k++)
						if (dist < dis[k]) {
							dis = shiftFromN(dis, k+1);
							clun = shiftFromN(clun, k+1);
							clun[k] = clusterInstanceBelongsTo[j];
							dis[k] = dist;
							k = nn;
						}
				}
			for (int m = 0; m < numberClusters; m++)
				cluCou[m] = 0;
			for (int m = 0; m < nn; m++)
				cluCou[clun[m]]++;
			int max = 0;
			for (int m = 0; m < numberClusters; m++)
				if (max < cluCou[m]) {
					max = cluCou[m];
					indice = m;
				}
			// aca asigno al cluster
			for (int j = 0; j < nn; j++)
				dis[j] = Double.MAX_VALUE;
			as[i] = indice;
		}

		return as;
	}

	// recalculo los centroides
	double[][] recalcCentroids() {

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
		clusterInstanceBelongsTo = assignInstanceToCluster(centroids);
		double ant = 0, now = 0;
		int iter = 0;
		do {
			ant = this.SSE();
			centroids = recalcCentroids();
			clusterInstanceBelongsTo = reAssItCl();
			iter++;
			now = this.SSE();
		} while (iter < maxIterations && Math.abs(now - ant) > epsilon);
		computPercentageOfInstanceInClusterc();// porcentaje de instancias en cada cluster
		
		calculateClusterValidityMeasures();
		return results();
	}

	public int classifyInstance(double[] inst) {

		double[] dis = new double[nn];
		int[] cluCou = new int[numberClusters];
		int[] clun = new int[nn];
		int indice = 0;

		for (int j = 0; j < nn; j++)
			dis[j] = Double.MAX_VALUE;
		for (int j = 0; j < nItems; j++) {
			double dist = getDistanceKind().calculate(ds.dataset[j], inst);
			for (int k = 0; k < nn; k++)
				if (dist < dis[k]) {
					dis = shiftFromN(dis, k);
					clun = shiftFromN(clun, k);
					clun[k] = clusterInstanceBelongsTo[j];
					dis[k] = dist;
					k = nn;
				}
		}
		for (int m = 0; m < numberClusters; m++)
			cluCou[m] = 0;
		for (int m = 0; m < nn; m++)
			cluCou[clun[m]]++;
		int max = 0;
		for (int m = 0; m < numberClusters; m++)
			if (max < cluCou[m]) {
				max = cluCou[m];
				indice = m;
			}
		// aca asigno al cluster
		return indice;
	}

}
