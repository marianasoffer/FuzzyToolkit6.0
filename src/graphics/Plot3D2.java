package graphics;

import javax.swing.JFrame;

import clusterUtil.ClusterMath;
import clusterUtil.ClusterMath.*;
import org.math.plot.*;

import javax.swing.*;
import java.awt.Color;

public class Plot3D2 extends Graph{

	
	public Plot3D2(String title,double[][] data,int[]label,double[] cluRadius,double[][] centroi){
		super(title,data,label,cluRadius,centroi);
	}
	public Plot3D2(String title,double[][] data,int[]label,int cent){
		super(title,data,label,cent);
	}
	
	public void show(){
		Plot3DPanel plotpanel = new Plot3DPanel();
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
			plotpanel.addScatterPlot("Cluster:"+i, col[i%5], dc);

		}
		JFrame frame = new JFrame(title);
		frame.setSize(600, 600);
		frame.setContentPane(plotpanel);
		frame.setVisible(true);

	}
	public void close(){
		//
		this.close();
	}

	
}
