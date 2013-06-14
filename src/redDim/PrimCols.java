package redDim;

public class PrimCols extends ReducDim{
	public PrimCols(double[][] dr,int nc){
		super(dr,nc);
		r=new double[dr.length][nc];
		for(int i=0;i<dr.length;i++)
			for(int j=0;j<nc;j++)
				r[i][j]=dr[i][j];
	}
	public double[][] dataRed(){
		return r;
	}

}
