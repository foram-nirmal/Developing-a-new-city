//package buildings;

public class RBT_node{
public int buildingNum, executed_time, total_time; //given fields of a building
public minheapProp correspondingMinHeapnode = null; //pointer to minheap

	//constructor initializing the above fields
	public RBT_node(int buildingNum, int total_time) {
		this.executed_time = 0;
		this.buildingNum = buildingNum;
		this.total_time = total_time;
	}
}
