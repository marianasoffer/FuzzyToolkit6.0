package distance;

public class minkowskyDist extends Distance{

	    public double calcular(double[] ins1,double[] ins2,double m){
	            double sum=0;
	            for(int i=0;i<ins1.length;i++)
	            	sum+=Math.pow(ins1[i]-ins2[i],m);
	    return Math.pow(sum,1/m);
	  }
}
