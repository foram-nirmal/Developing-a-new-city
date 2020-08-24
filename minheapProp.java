//package buildings;
public class minheapProp {

	public int buildingNum, executed_time, total_time; //given fields of a building
	public RBT_node correspondingRBTnode = null; //pointer to RBT node

    //constructor initializing the above fields
	public minheapProp(int buildingNum, int total_time) {
		this.executed_time = 0;
		this.buildingNum = buildingNum;
		this.total_time = total_time;
	}
}
