//package buildings;
//import java.util.*;
//import java.lang.*;

public class minheap {
	// assuming the maximum size of array = 2000 as given
	public static final int MAXSIZE = 2000;
	minheapProp arr[] = new minheapProp[MAXSIZE]; //contains all the elements of the minheap
	static int size = 0; // size of minheap

	// finding the parent of an element
	private static int parent(int index) {
		return (int) ((index - 1) / 2);
	}

	// finding the left child of an element
	private static int left(int index) {
		return (int) index * 2 + 1;
	}

	// finding the right child of an element
	private static int right(int index) {
		return (int) index * 2 + 2;
	}

	// inserting elements to minheap
	public void insert_heap(minheapProp element){
		arr[size] = element; //inserting element at the last index
		int index = size;
		size++; // increasing the size of minheap
		reverseHeapify(index);
	}

	// this function re-balances the minheap if the last element violates the minheap property
	private void reverseHeapify(int index) {
		if(index == 0)
			return; // base condition
		// first checks for executed time condition
		if(arr[index].executed_time < arr[parent(index)].executed_time) {
			exchange(index, parent(index));
			reverseHeapify(parent(index));
		}
		// if executed time of both the buildings match then building nos. are compared
		else if(arr[index].buildingNum < arr[parent(index)].buildingNum && arr[index].executed_time == arr[parent(index)].executed_time){
			exchange(index, parent(index));
			reverseHeapify(parent(index));
		}
	}
	//swap elements in a minheap
	private void exchange(int n1, int n2) {
		minheapProp temp;
		temp = arr[n1];
		arr[n1] = arr[n2];
		arr[n2] = temp;
	}
	// delete the root
	public void delete_heap(){
		int index = size - 1;
		arr[0] = arr[index]; // first element is replaced with the last element of minheap
		arr[index] = null; // last element assigned null
		size = size - 1; // decrease the size of minheap
		if(size == 0)
			return; //all the elements are deleted
		minheapify(0);
	}

	public void minheapify(int index) {
		if(!(index >= (size/2) && index <= size)) { //lies in the range
			if(left(index) < size && right(index) <size) {
				if(arr[index].executed_time > arr[left(index)].executed_time || arr[index].executed_time > arr[right(index)].executed_time) { // checking if the parent is bigger than any of its children
					if(arr[index].executed_time > arr[left(index)].executed_time && arr[index].executed_time > arr[right(index)].executed_time) {
						if(arr[left(index)].executed_time == arr[right(index)].executed_time) {
							if((arr[left(index)].buildingNum < arr[right(index)].buildingNum)) { //swap the left child with parent if all of the above conditions hold true
								exchange(index,left(index));
								minheapify(left(index));
							}
							else { //otherwise swap parent with the right child
								exchange(index,right(index));
								minheapify(right(index));
							}
						}
						else {
							if(arr[left(index)].executed_time < arr[right(index)].executed_time) { //if executed time of left child is less than the executed time of right child, swap parent with left child
								exchange(index,left(index));
								minheapify(left(index));
							}
							else {
								exchange(index,right(index)); //else swap with right child
								minheapify(right(index));
							}
						}
					}
					else if(arr[index].executed_time > arr[left(index)].executed_time) {  //swap parent with left child
						exchange(index,left(index));
						minheapify(left(index));
					}
					else if(arr[index].executed_time > arr[right(index)].executed_time) { //else swap parent with right child
						exchange(index,right(index));
						minheapify(right(index));
					}
				}
				else if(arr[index].executed_time == arr[left(index)].executed_time || arr[index].executed_time == arr[right(index)].executed_time) { //check if either of child's executed time equals parent
					if(arr[index].executed_time == arr[left(index)].executed_time && arr[index].executed_time == arr[right(index)].executed_time) { // check if both the child's executed time equals parent
						if(arr[index].buildingNum > arr[left(index)].buildingNum && arr[index].buildingNum > arr[right(index)].buildingNum) { //compare parent's building number with left and right child
							if((arr[left(index)].buildingNum < arr[right(index)].buildingNum)) { //after checking all the above conditions, is left child's building number less than the right child's building number, swap parent with left child
								exchange(index,left(index));
								minheapify(left(index));
							}
							else { //otherwise swap with right child
								exchange(index,right(index));
								minheapify(right(index));
							}
						}
						else if(arr[index].buildingNum > arr[left(index)].buildingNum) { //if left child's bldg no is less than parent, swap
							exchange(index,left(index));
							minheapify(left(index));
						}
						else if(arr[index].buildingNum > arr[right(index)].buildingNum) { //if right child's bldg no is less than parent
							exchange(index,right(index));
							minheapify(right(index));
						}
					}
					else if(arr[index].executed_time == arr[left(index)].executed_time) {
						if(arr[index].buildingNum > arr[left(index)].buildingNum) { //compare building nos. if executed time are equal, and swap if parent is bigger
							exchange(index,left(index));
							minheapify(left(index));
						}
					}
					else if(arr[index].executed_time == arr[right(index)].executed_time) {
						if(arr[index].buildingNum > arr[right(index)].buildingNum) { // compare building nos. if executed time are equal, and swap if parent is bigger
							exchange(index,right(index));
							minheapify(right(index));
						}
					}
				}
			}

			else if(right(index) >= size  && left(index) < size) {
				if(arr[index].executed_time > arr[left(index)].executed_time) { //if executed time of parent greater than left child, swap accordingly
					exchange(index,left(index));
					minheapify(left(index));
				}
				else if(arr[index].executed_time == arr[left(index)].executed_time) { //if executed time of parent same as left child, check for bldg nos.
					if(arr[index].buildingNum > arr[left(index)].buildingNum) {
						exchange(index,left(index));
						minheapify(left(index));
					}
				}
			}
			else if(right(index)<size && left(index) >= size) {
				if(arr[index].executed_time > arr[right(index)].executed_time) { //if executed time of parent greater than left child, swap accordingly
					exchange(index,right(index));
					minheapify(right(index));
				}
				else if(arr[index].executed_time == arr[right(index)].executed_time) {
					if(arr[index].buildingNum > arr[right(index)].buildingNum) {  //if executed time of parent same as right child, check for bldg nos.
						exchange(index,right(index));
						minheapify(right(index));
					}
				}
			}
		}
	}

}
