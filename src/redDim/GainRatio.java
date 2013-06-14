package redDim;



import java.util.SortedSet;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.InstanceTools;

import junit.filter.*;

import java.io.File;
import java.io.IOException;

import javastat.multivariate.PCA;
import clusterUtil.ClusterMath;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.featureselection.RELIEF;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.featureselection.SymmetricalUncertainty;

public class GainRatio extends ReducDim{
	public GainRatio(double[][] dr,int nc){
		super(dr,nc);

        Dataset data = new DefaultDataset();
        for (int i = 0; i < dr.length; i++) {
            double[] vals = ClusterMath.removeNItemsVect(dr[i],1);
            Instance inst = new DenseInstance(vals,dr[i][dr[0].length-1]);
            data.add(inst);

        }
        SymmetricalUncertainty ga = new SymmetricalUncertainty();
        ga.build(data);

        double[] ord=new double [dr[0].length-1];
        boolean[] ta=new boolean [dr[0].length-1];
        int[] indic=new int[dr[0].length-1];
		for (int i = 0; i < dr[0].length-1; i++){ 
			ord[i]=ga.score(i);
			ta[i]=false;
			indic[i]=-1;
		}
		int i=0;
		while (i<nc){
			double max=0;
			int ind=0;
			for(int j=0;j<dr[0].length-1;j++)
				if(max<ord[j] && !ta[j]){
					max=ord[j];
					ind=j;
				}
			indic[i]=ind;
			ta[ind]=true;
			i++;
		}
		double[][] resu=new double[dr.length][nc];
		for(int k=0;k<dr.length;k++)
			for(int l=0;l<nc;l++)
				resu[k][l]=dr[k][indic[l]];
		r=resu.clone();
		}
	
		public double[][] dataRed(){
			return r;
		}

	}
		


