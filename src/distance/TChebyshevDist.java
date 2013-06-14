package distance;

public class TChebyshevDist extends Distance{
	    public double calculate(double[] ins1,double[] ins2){
	            double max=0;
	            for(int i=0;i<ins1.length;i++)
	            	if(Math.abs(ins1[i]-ins2[i])>max)
	            		max=Math.abs(ins1[i]-ins2[i]);
	    return max;
	  }
}