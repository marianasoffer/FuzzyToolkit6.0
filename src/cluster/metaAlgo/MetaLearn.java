package cluster.metaAlgo;

import cluster.*;
import dataStruct.DoubleDataset;
import distance.Distance;

public class MetaLearn {
	public int minClu;
	public int maxClu;
	public int actualClu;
	public Distance dist;
	public Cluster cluType;
	public DoubleDataset ds;
	public MetaLearn(){
		
	}
	public MetaLearn(int minClu,int maxClu,DoubleDataset ds){
		this.minClu=minClu;
		this.maxClu=maxClu;
		this.ds=ds;
	}
	public void setCluType(Cluster clu){
		this.cluType=clu;
	}
	public void setDistance(Distance dist){
		this.dist=dist;
	}

}
