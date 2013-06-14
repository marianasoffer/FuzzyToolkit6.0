package clusterUtil;

public final class RedDim {

	//reduce la dimension a las primeras n columnas de la segunda row
/*	public static double[][] reducPri(double[][] a,int nCols){
		double[][] r = new double[a.length][nCols];
		for(int i=0;i<a.length;i++)
			for(int j=0;j<nCols;j++)
				r[i][j]=a[i][j];
		return r;
	}
*/	
	public static double calcEntro(int col,double[][] dr,int[] cluAssign){
		double men=5;
		for(int i=0;i<cluAssign.length;i++){
			
		}
		return men;
	}
	
	public static double[][] reducEntropy(double[][] dr,int nc,int[] cluAssign){
		boolean[] usado=new boolean[dr[0].length];
		for(int i=0;i<usado.length;i++)
			usado[i]=false;
		int max=ClusterMath.max(cluAssign);
		double[][] r=new double[dr.length][nc];
		int[] cols=new int[nc];
		int coln=0;
		while(coln<nc){
			double mejore=2;
			int indM=-1;
			for(int i=0;i<dr[0].length;i++)
				if(usado[i]==false){
					double entro=calcEntro(i,dr,cluAssign);
					if(entro<mejore){
						mejore=entro;
						indM=i;
					}
				}
			usado[indM]=true;
			cols[coln]=indM;
			coln++;
		}
		//ahora que tengo en cols las columnas tengo q copiarlas
		return r;
	}


}
