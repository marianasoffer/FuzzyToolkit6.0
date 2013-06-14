package redDim;

import javastat.multivariate.PCA;
import clusterUtil.ClusterMath;

public class PcaRed extends ReducDim{
		public PcaRed(double[][] dr,int nc){
			super(dr,nc);
			double[][] t2;

			t2=ClusterMath.transpose(dr);
			PCA pcp=new PCA(0.99,t2);
			    
			double [][] pcv=pcp.principalComponents(t2);
			
			double [][] t3=new double[nc][t2[0].length];
			for(int i=0;i<t3.length;i++)
				for(int j=0;j<t3[0].length;j++)
					t3[i][j]=0;
			for(int i=0;i<t3.length;i++)
				for(int j=0;j<t3[0].length;j++)
					for(int k=0;k<nc;k++)
						t3[k][j]+=t2[i][j]*pcv[k][i];
				
			r=ClusterMath.transpose(t3);
		}
		public double[][] dataRed(){
			return r;
		}
}
