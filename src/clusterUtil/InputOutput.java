package clusterUtil;

import java.io.*;
import dataStruct.*;
import clusterUtil.*;
import cluster.Cluster;
public final class InputOutput {
	
	public static void writes(String name,double[][]ds,int[] cluAssign, int numClu){
		try{
	        BufferedWriter br = new BufferedWriter(new FileWriter(name));
	        Integer row=ds.length;
	        Integer col=ds[0].length;
	        Integer colu=col+1;
	        String str=row.toString()+","+colu.toString()+","+Integer.toString(numClu);
	        br.write(str);br.newLine();
	        for(int i=0;i<row;i++){
	        	str="";
	        	for(int j=0;j<col;j++){
	        		str+=Double.toString(ds[i][j])+",";
	        	}
	        	str+=Integer.toString(cluAssign[i]);
	        	br.write(str);
	        	br.newLine();
	        }
	    br.close();
		}
     catch (IOException e) {
    System.out.println(e);
    }
	}
	public static void writesNom(String name,String[][]ds,int[] cluAssign, int numClu){
		try{
	        BufferedWriter br = new BufferedWriter(new FileWriter(name));
	        Integer row=ds.length;
	        Integer col=ds[0].length;
	        Integer colu=col+1;
	        String str=row.toString()+","+colu.toString()+","+Integer.toString(numClu);
	        br.write(str);br.newLine();
	        for(int i=0;i<row;i++){
	        	str="";
	        	for(int j=0;j<col;j++){
	        		str+=ds[i][j]+",";
	        	}
	        	str+=Integer.toString(cluAssign[i]);
	        	br.write(str);
	        	br.newLine();
	        }
	    br.close();
		}
     catch (IOException e) {
    System.out.println(e);
    }
	}
	
	public static int[][] readSupResults(String name){
	int [][] resu=null;
	 try {
	        BufferedReader in = new BufferedReader(new FileReader(name));
	        Integer row;
	        Integer col;
	        String[] rowCol;
	        String str;
	        str = in.readLine();
	        rowCol=str.split(",");
	        col=Integer.decode(rowCol[1]);
	        row=Integer.decode(rowCol[0]);
	        int clus=Integer.decode(rowCol[2]);
	        resu=new int[row+1][2];
	        row=0;
	        while ((str = in.readLine()) != null) {
		        rowCol=str.split(",");
		        resu[row][0]=Integer.parseInt(rowCol[col-1]);
		        double tmp=Double.parseDouble((rowCol[col-2]));
				int res=(int)tmp;
		        resu[row][1]=res;
		        row++;
	        }
	        in.close();
	    	resu[row][0]=clus;

	 } catch (IOException e) {
	    }
	return resu;
}

	//la primer row tiene cantidad de instancias,(coma)cantidad de filas
		public static double[][] reads(String name){
		double[][] ds=null;
		 try {
		        BufferedReader in = new BufferedReader(new FileReader(name));
		        Integer row;
		        Integer col;
		        String[] rowCol;
		        String str;
		        str = in.readLine();
		        rowCol=str.split(",");
		        row=Integer.decode(rowCol[0]);
		        col=Integer.decode(rowCol[1]);
		        ds=new double[row][col];
		        row=0;
		        while ((str = in.readLine()) != null) {
			        rowCol=str.split(",");
			        for(int i=0;i<col;i++)
			        	ds[row][i]=Double.parseDouble((rowCol[i]));
			        row++;
		        }
		        in.close();
		    } catch (IOException e) {
		    }
		return ds;
	}
		//la primer row tiene cantidad de instancias,(coma)cantidad de filas
		public static NominalData readNominal(String name){
		String[][] ds=null;
        Integer col=0;
        Integer row=0;
        try {
		        BufferedReader in = new BufferedReader(new FileReader(name));
		        String[] rowCol;
		        String str;
		        str = in.readLine();
		        rowCol=str.split(",");
		        row=Integer.decode(rowCol[0]);
		        col=Integer.decode(rowCol[1]);
		        ds=new String[row][col];
		        row=0;
		        while ((str = in.readLine()) != null) {
			        rowCol=str.split(",");
			        for(int i=0;i<col;i++)
			        	ds[row][i]=(rowCol[i]);
			        row++;
		        }
		        in.close();
		    } catch (IOException e) {
				e.printStackTrace();

		    }
	    NominalData nd=new NominalData();
	    nd.ds=ds.clone();
		return nd;
	}

		public static Cluster ReadModel(String fi){
			FileInputStream fis = null;
			ObjectInputStream in = null;
			Cluster c=null;
			try {
				fis = new FileInputStream(fi);
				in = new ObjectInputStream(fis);
				c = (Cluster) in.readObject();
				in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			return c;
		}

}
