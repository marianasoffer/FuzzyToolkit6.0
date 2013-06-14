package distance;
//Esta sirve para medidas no negativas solamente
public class Canberra	extends Distance{
	    public double calculate(double[] ins1,double[] ins2){
	            double up=0;
	            double dn=0;
	            for(int i=0;i<ins1.length;i++){
	            	up+=Math.abs(ins1[i]-ins2[i]);
	            	dn+=ins1[i]+ins2[i];
	            }
	            return up/dn;
	  }

}
