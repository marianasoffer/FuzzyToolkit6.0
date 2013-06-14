package dataStruct;

public class DataRed {

	public double[][] dr;
	public double [][] sr;
	public String[] cols;
	public int cant;
	public DataRed(){
	}
	public DataRed(double[][] dataset,String[] cols,int cant){
		this.dr=dataset;
		this.cols=cols;
		this.cant=cant;
	}


}
