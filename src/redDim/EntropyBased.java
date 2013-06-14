package redDim;

public class EntropyBased extends ReducDim{

	int[] cluAssign;
	int numClu;
	EntropyBased (double[][] a,int nCols,int numClu,int[] cluAssign){
		super(a,nCols);
		this.numClu=numClu;
		this.cluAssign=cluAssign;
	}	

	public void Calculate(){
		boolean[] usado=new boolean[dr[0].length];
		for(int i=0;i<usado.length;i++)
			usado[i]=false;
		//Mientras no halla decidido todas las columnas
		
		//calculo a cada nivel la mejor entropia para una columna
		
	}
}
