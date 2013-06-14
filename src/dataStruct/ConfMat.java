package dataStruct;
import java.util.*;



public class ConfMat {
	int numClu;
	int [][] CM;
	int nItems;
	double percentCorrectos;
	
	public ConfMat (int[][] rawInput){
		nItems=rawInput.length;
		numClu=rawInput[nItems-1][0];
		CM = new int [numClu][numClu];
		for (int i=0;i<numClu;i++)
			for (int j=0;j<numClu;j++)
				CM[i][j]=0;
		for (int i=0;i<nItems-1;i++){
			int i1=rawInput[i][0];
			int i2=rawInput[i][1];
			CM[i1][i2]++;
		}
	nItems--;
	}
	
	public void percentCorrectos(){
		percentCorrectos=0;
		int numCor=0;
		boolean[] uso=new boolean[numClu];
		for (int i=0;i<numClu;i++)
			uso[i]=false;
		for(int i=0;i<numClu;i++){
			int max=-1;
			int subiMax=-1;
			for(int j=0;j<numClu;j++){
				if (!uso[j])
					if (max<CM[i][j]){
						max=CM[i][j];
						subiMax=j;
				}
			}
			numCor+=max;
			uso[subiMax]=true;
		}
		percentCorrectos=1.0*numCor/nItems;
	}
	public void showData(){
	System.out.println("------Matrix de confusion------------");
	for(int i=0;i<numClu;i++){
		System.out.print("|");
		for(int j=0;j<numClu;j++){
			System.out.print(CM[i][j]);
			System.out.print("|");
		}
		System.out.println(" ");
	}
	System.out.println("Porcentajes de instancias correctas:"+ percentCorrectos);

	}
}