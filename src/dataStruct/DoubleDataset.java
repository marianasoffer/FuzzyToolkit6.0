
package dataStruct;

import java.io.*;
public class DoubleDataset implements Serializable{
//	private static final long serialVersionUID = 5526471155622776147L;

	public double[][] dataset;


	public DoubleDataset(){
	}
	public DoubleDataset(double[][] dataset){
		this.dataset=dataset;
	}

	public void setData(double[][] dataset){
		this.dataset=dataset;
	}
	
	public double getData(int i, int j){
		return dataset[i][j];
	}
/*
	public String toString(){
		String s="";
		for(int i=0;i<nItems;i++){
			s+="|";	
			for(int j=0;j<nFields;j++)
				s+=dataset[i][j]+"|";
			s+="\n";
		}
			return s;
	}
	*/
	public void showData(){
//		System.out.println("#individuals:"+nItems);
//		System.out.println("#fields:"+nFields);
		System.out.println("------Dataset------------");
		System.out.println("------Grado de Pertenencia U------------");
		for(int i=0;i<dataset.length;i++){
			System.out.print("|");
			for(int j=0;j<dataset[0].length;j++){
				System.out.print(dataset[i][j]);
				System.out.print("|");
			}
			System.out.println(" ");
		}
	}

}



