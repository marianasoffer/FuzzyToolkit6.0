package distance;

public class NominDist extends Distance{
		    public double calcular(String[] ins1,String[] ins2){
		            double sum=0;
		            for(int i=0;i<ins1.length;i++)
		            	if(ins1[i].equalsIgnoreCase(ins2[i]))
		            		sum++;
		            double p=ins1.length;
		            sum=(p-sum)/p;
		    return sum;
		  }
	}