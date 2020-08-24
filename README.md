# Developing-a-new-city

### Description
Wayne Enterprises is developing a new city. They are constructing many buildings and plan to use software to keep track of all buildings under construction in this new city. A building record has the following fields:

buildingNum: unique integer identifier for each building.
executed_time: total number of days spent so far on this building.
total_time: the total number of days needed to complete the construction of the building.
 
The needed operations are:

1. Print (buildingNum) prints the triplet buildingNume,executed_time,total_time.

2. Print (buildingNum1, buildingNum2) prints all triplets bn, executed_tims, total_time for which buildingNum1 <= bn <= buildingNum2.

3. Insert (buildingNum,total_time) where buildingNum is different from existing building numbers and executed_time = 0.


### Input Format

Input test data will be given in the following format.
 
Insert(buildingNum, total_time)
Print(buildingNum)
Print (buildingNum1, buildingNum2)
 
Following is an example of input.

 
0: Insert(5,25)

2: Insert(9,30)

7: Insert(30,3)

9: Print (30)

10: Insert(1345,12)

13: Print (10,100)

14: Insert(345,14)

39: Insert(4455,14)

 
The number at the beginning of each command is the global time that the command has appeared in the system. You must have a global time counter, which starts at 0. You can read the input command only when the global time matches the time in the input command. You can assume this time is an integer in increasing order. When no input remains, construction continues on the remaining buildings until all are complete.
 
PrintBuilding (buildingNum) query should be executed in O(log(n)) and PrintBuilding (buildingNum1 1, buildingNum2) should be executed in O(log(n)+S) where n is number of active buildings and S is the number of triplets printed. For this, your search of the RBT should enter only those subtrees that may possibly have a building in the specified range. All other operations should take O(log(n)) time.
 
### Output Format
 
· Insert(buildingNum ,total_time) should produce no output unless buildingNum is a duplicate in which case you should output an error and stop.

 
· PrintBuilding (buildingNum) will output the (buildingNum,executed_time,total_time) triplet if the buildingNum exists. If not print (0,0,0).

 
· PrintBuilding (buildingNum1, buildingNum2) will output all (buildingNum,executed_time,total_time) triplets separated by commas in a single line including buildingNum1 and buildingNum2; if they exist. If there is no building in the specified range, output (0,0,0). You should not print an additional comma at the end of the line.


. Other oupt includes completion date of each building and completion date of city.

 
All output will go to a file named “output_file.txt”.

