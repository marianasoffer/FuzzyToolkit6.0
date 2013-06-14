package distance;
//Esta distancia es solo para datasets de atributos binarios
public class BinaryDist extends Distance{
	    public double calcular(String[] ins1,String[] ins2){
	            double eq=0,dif=0;
	            for(int i=0;i<ins1.length;i++)
	            	if(ins1[i].equalsIgnoreCase(ins2[i]))
	            		eq++;
	            	else
	            		dif++;
	            
	    return dif/(eq+dif);
	  }
}