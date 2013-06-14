package dataStruct;

public class NominalData {

	public String[][] ds;
	public int[] cant;//cantidad de valores distintos que hay en cada col de ds

	public void showData(){
		System.out.println("------Dataset------------");
		System.out.println("------Grado de Pertenencia U------------");
		for(int i=0;i<ds.length;i++){
			System.out.print("|");
			for(int j=0;j<ds[0].length;j++){
				System.out.print(ds[i][j]);
				System.out.print("|");
			}
			System.out.println(" ");
		}
	}


}
