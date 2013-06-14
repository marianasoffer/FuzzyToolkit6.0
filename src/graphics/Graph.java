package graphics;
import java.awt.Color;

public abstract class Graph {
	String title;
	double[][] data;
	int[] cluAssign;
	int numClu;
	double[] cluRadious;
	double[][]cent;

	Color[] col={Color.DARK_GRAY,Color.red,Color.blue,Color.orange};
	Color[] col2={Color.GRAY,Color.pink,Color.cyan,Color.yellow};
	public Graph(String t,double[][] data,int[] cluAssign,double[] cluRadius,double[][] cent){

		this.title=t;
		this.data=data.clone();
		this.cluAssign=cluAssign.clone();
		this.cluRadious=cluRadius.clone();
		this.cent=cent.clone();
	}
	public Graph(String t,double[][] data,int[] cluAssign,int numClu){

		this.title=t;
		this.data=data.clone();
		this.cluAssign=cluAssign.clone();
		this.numClu=numClu;
	}

//	public void show();
//	public void close();;
	}

