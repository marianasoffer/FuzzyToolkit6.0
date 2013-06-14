//deprecated
package clusterUtil;
import cern.colt.matrix.*;
import cern.colt.matrix.doublealgo.Statistic;
import java.lang.Math;
import distance.*;

public final class ClusterMath {

	
	//intra cluster cluster distance in a vector[i], i=cluster number 
	public static double[] cluDist(double[][] centroids,double[][] instances, int[] cluAssign,Distance dist) {
		int nClu=centroids.length;
		int nInst=instances.length;
		double[] ICluDists=new double[nClu];
		
		for(int i=0;i<nClu;i++)
			ICluDists[i]=0;
		
		for(int i=0;i<nClu;i++)
			for(int j=0;j<nInst;j++)
				if(cluAssign[j]==i)
					ICluDists[i]+=dist.calculate(centroids[i],instances[j]);
		return ICluDists;
	}

	//transponer matriz de 2 d
	public static double[][] transpose(double[][] m){
		double[][] n=new double[m[0].length][m.length];
		for(int i=0;i<n[0].length;i++)
			for(int j=0;j<n.length;j++)
				n[j][i]=m[i][j];
		return n;
	}

	public static double [][] addMat(double[][] v,double[][] b){
		double [][] m=new double[v.length][v[0].length];
		for(int i=0;i<v.length;i++)
			for(int j=0;j<v.length;j++)
				m[i][j]=v[i][j]+b[i][j];
		return m.clone();
	}

	public static double addVect(double[] v){
		double res=0;
		for(int i=0;i<v.length;i++)
			res+=v[i];
		return res;
	}

	//mayor numero del vector
	public static int max(int[] v){
		int res=0;
		for(int i=0;i<v.length;i++)
			if(res<v[i])
				res=v[i];
		return res;
	}

	public static double media(int[] v){
		double res=0;
		for(int i=0;i<v.length;i++)
			if(res<v[i])
				res=v[i];
		return res/(v.length-1);
	}
	public static double[] media(double[][] v){
		double[] tmp=new double[v[0].length];
		for(int i=0;i<tmp.length;i++)
			tmp[i]=0;
		for(int j=0;j<v.length;j++)
		for(int i=0;i<tmp.length;i++)
			tmp[i]+=v[j][i];
		for(int i=0;i<tmp.length;i++)
			tmp[i]=tmp[i]/v.length;
		return tmp;
	}
	
	public final double[][] covariance(double[][] matrix,double fuz) {
        final DoubleMatrix2D b=null;
         DoubleMatrix2D c=null;
        b.assign(matrix);
        c=(DoubleMatrix2D)Statistic.covariance(b);
        double[][] r;
        r=c.toArray();
        return r;
	}


	public static double[] var(double[][] v,double[] mu){
		double[] tmp=new double[v[0].length];
		for(int i=0;i<tmp.length;i++)
			tmp[i]=0;
		for(int j=0;j<v.length;j++)
			for(int i=0;i<tmp.length;i++)
			tmp[i]+=Math.pow(v[j][i]-mu[i],2);
		for(int i=0;i<tmp.length;i++)
			tmp[i]=tmp[i]/v.length;
		return tmp;
	}

	public static double[] reducToOne(double[][] a,int ColNum){
		double[] r = new double[a.length];
		for(int i=0;i<a.length;i++)
			r[i]=a[i][ColNum];
		return r;
	}

	public static int[] doubleToInt(double[]a){
		int[] r = new int[a.length];
		for(int i=0;i<a.length;i++)
			r[i]=(int)a[i];
		return r;
	}


	
	//cuantos hay de cada cluster
	public static int cantnVect(int[] v,int n){
		int tot=0;
		for(int i=0;i<v.length;i++)
			if(v[i]==n)
				tot++;
		return tot;
	}

	
	//existe el valor en el vector
	public static boolean exists(int[] vec,int val){
		boolean res=false;
		for(int i=0;i<vec.length;i++)
			if(vec[i]==val)
				res=true;
		return res;
	}

	//existe el valor en el vector
	public static boolean estaStr(String value,String[] str,int next){
		boolean res=false;
		for(int i=0;i<next;i++)
			if(value.equalsIgnoreCase(str[i]))
				res=true;
		return res;
	}

	
	//reemplazo en el vector el valor 1 todas las veces que aparece por el 2
	public static void replace(int[] vec,int v1,int v2){
		for(int i=0;i<vec.length;i++)
			if(vec[i]==v1)
				vec[i]=v2;
	}

	
	//es Centroide
	public static boolean isCentroid(double[][] centroids,double[] instance) {
		int nClu=centroids.length;
		boolean resu=false;
		for(int i=0;i<nClu;i++){
			boolean is=true;
			for(int j=0;j<instance.length;j++)
			   if (instance[j]!=centroids[i][j])
				   is=false;
			if(is)
				resu=true;
		}	
    return resu;
	}

	public static boolean isCentroid(String[][] centroids,String[] instance) {
		int nClu=centroids.length;
		boolean resu=false;
		for(int i=0;i<nClu;i++){
			boolean is=true;
			for(int j=0;j<instance.length;j++)
			   if (instance[j]!=centroids[i][j])
				   is=false;
			if(is)
				resu=true;
		}	
    return resu;
	}	
	public static double[][] normalizeDataset(double[][] d){
		double[][] ds=d.clone();
	  double[] totals=new double[ds[0].length];
	    for (int i = 0; i < totals.length; i++) 
	        totals[i]=0;
	    for (int i = 0; i < ds.length; i++) 
		    for (int j = 0; j < ds[0].length; j++) 
		    	totals[j]+=ds[i][j];
	    for (int i = 0; i < ds.length; i++) 
		    for (int j = 0; j < ds[0].length; j++) 
		    	ds[i][j]=ds[i][j]/totals[j];
  	return ds;
  }
	
   public static double determinant(double[][] m){
	  double v=0;
	  if (m.length==1)
		  return m[0][0];
	  
      for (int i = 0; i < m.length; i++) {
              int sign = ((i % 2) == 0) ? 1 : -1;
              v += sign * m[0][i] * determinant(beta(0, i,m));
      }              
	  return v;
  }
   //norma del vector (longitud espacial)
	public static double norm(double[] v1){
		double acum=0;
		for (int i=0;i<v1.length;i++)
			acum+=Math.pow(v1[i],2);
		return Math.sqrt(acum);
	}

	public static double[] normalize(double[] doubles) {
	    double sum = 0;
	    for (int i = 0; i < doubles.length; i++) {
	      sum += doubles[i];
	    }
	    for (int i = 0; i < doubles.length; i++) 
	        doubles[i]=doubles[i]/sum;
	    return doubles;
	  }
	
	//diferencia entre 2 vectores (da vector de vi-v1i
	public static double[] difference(double[]v1, double[] v2){
		if (v1.length!=v2.length)
			throw new RuntimeException("vectores de dataset y centroide de distinta cantidad de elementos");
		double[] d=new double[v1.length];
		for (int i=0;i<v1.length;i++)
			d[i]=v1[i]-v2[i];
		return d;
	}
//multiplico el primer vector por la transpuesta del segundo
//es solo para el caso en que la mat mult devuelve un nro	
 	public static double mulTranspVecNum(double[] d1,double[] d2){
 		double r=0;
 		for(int i=0;i<d1.length;i++)
 			r=r+d1[i]*d2[i];
 		return r;
 	}

 	
 	public static double[] sumVectNum(double[] d1,double[] d2){
 		double[] a=new double[d1.length];
 		for(int i=0;i<d1.length;i++)
 			a[i]=d1[i]+d2[i];
 		return a;
 	}
 	
 	//multiplico el primer vector por el segundo (mismo tamanio)
 	//y obtengo una matriz
 	public static double[][] mulTranspVecMat(double[] d1,double[]d2){
 	 		double[][] mm=new double[d1.length][d1.length];
            for(int i=0;i<d1.length;i++)
                for(int j=0;j<d1.length;j++)
                	mm[i][j]=0;
            for(int i=0;i<d1.length;i++)
                for(int j=0;j<d1.length;j++)
                		mm[i][j]=d1[i]*d2[j];
            return mm; 	
 	 	}
 	
 	
 	//invert matrix
 	public static double[][] inverseMat(double[][] m){
 		int l=m.length;
 		double det=determinant(m);
 		double[][] tr=new double[l][l];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                    int sign = (((i + j) % 2) == 0) ? 1 : -1;
                    tr[i][j]= sign * determinant(beta(i, j,m)) / det;
            }
    }

 		return tr;
 	}
    // get new matrix without r row and c col
 	public static double[][] beta(int r, int c,double[][] m) {
        int iRow = -1, iCol = -1;
        int l=m.length;
 		double[][] tr=new double[l-1][l-1];

        for (int i = 0; i < l; i++) {
                if (i == r) continue;
                if (i > r) {
                        iRow = i - 1;
                } else {
                        iRow = i;
                }
                for (int j = 0; j < l; j++) {
                        if (j == c) continue;
                        if (j > c) {
                                iCol = j - 1;
                        } else {
                                iCol = j;
                        }
                        tr[iRow][iCol]=m[i][j];
                }
        }
        return tr;
}

  //multiply vect por matrix queda vect
 	public static double[] multVecMat(double[] v,double[][] m){
 		int l=m.length;
 		double[] res=new double[l];
        for(int i=0;i<l;i++)
        	res[i]=0;
        for(int i=0;i<l;i++)
        	for(int j=0;j<l;j++)
        		res[i]+=v[i]*m[j][i];
 		return res;
 	}

public static double[][]  multMat(double[][] m1,double[][] m2) 
{
	int r1=m1.length;
	int c1=m1[0].length;
	int c2=m2[0].length;
	
    double[][] m = new double[r1][c2];
    for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                    double v = 0;
                    for (int k = 0; k < c1; k++) {
                            v += m1[i][k]*m2[k][j];
                    }
                    m[i][j]=v;
            }
    }
    return m;
	}

public static double[] removeNItemsVect(double[]v,int n){

	double[] a=new double [v.length-n];
	for(int i=0;i<a.length;i++)
		a[i]=v[i];
	return a.clone();
}
public static double[] multVectNum(double[]v,double n){

	double[] a=new double [v.length];
	for(int i=0;i<a.length;i++)
		a[i]=v[i];
	for(int i=0;i<a.length;i++)
		a[i]=a[i]*n;
	return a;
}
public static double[][] multMattNum(double[][] v,double n){

for(int j=0;j<v.length;j++)
	for(int i=0;i<v[0].length;i++)
		v[i][j]*=n;

return v.clone();
 }
}