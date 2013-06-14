package redDim;



public abstract class ReducDim {
	int nc;
	double[][]dr;
	double[][] r;
	String[][] st;
	public ReducDim(double[][] dr,int nc){
		this.dr=dr;
		this.nc=nc;
		r=new double[dr.length][nc];
		
	} 
	public ReducDim(String[][] st,int nc){
		this.dr=dr;
		this.nc=nc;
		this.st=st.clone();
		
	} 


}


