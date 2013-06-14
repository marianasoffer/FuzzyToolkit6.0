

package graphics;

import javax.swing.JFrame;

import org.math.plot.Plot3DPanel;
import org.math.plot.Plot2DPanel;
import clusterUtil.ClusterMath;

public class Plot2D2 extends Graph{

	public Plot2D2(String title,double[][] data,int[]label,double[] cluRadius,double[][] centroi){
		super(title,data,label,cluRadius,centroi);
	}
	public Plot2D2(String title,double[][] data,int[]label,int cent){
		super(title,data,label,cent);
	}


	public void show(){
		Plot2DPanel plotpanel = new Plot2DPanel();
		plotpanel.addLegend("SOUTH");
		double [][] dc;
		dc=null;
		//veo cuantos hay para dimensionarlos
		for (int i=0;i<numClu;i++){
		    int cant=ClusterMath.cantnVect(cluAssign, i);
			dc=new double[cant][data[0].length];
			int newsubi=0;
				for (int j=0;j<cluAssign.length;j++)
					if(cluAssign[j]==i){
						dc[newsubi]=data[j].clone();
						newsubi++;}
			plotpanel.addBarPlot("Cluster:"+i, col[i%5], dc);
		}
		JFrame frame = new JFrame(title);
		frame.setSize(600, 600);
		frame.setContentPane(plotpanel);
		frame.setVisible(true);

	}
	public void close(){
		this.close();
	}
	
}
