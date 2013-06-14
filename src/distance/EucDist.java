package distance;

public class EucDist extends Distance{
	    public double calculate(double[] ins1,double[] ins2){
	            double sum=0;
	            for(int i=0;i<ins1.length;i++)
	            sum+=Math.pow(ins1[i]-ins2[i],2);
	    return Math.sqrt(sum);
	  }
}