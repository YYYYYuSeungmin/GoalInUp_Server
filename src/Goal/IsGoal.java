package Goal;

import java.util.ArrayList;

public class IsGoal {
	ArrayList<DetailGoal> detailGoalList;
	int gID = 0;
	public void setGID(int gID) {
		this.gID = gID;
	}
	public void setDetailGoalList(ArrayList<DetailGoal> detailGoalList) {
		this.detailGoalList = detailGoalList;
	}
	
    public boolean checkIsGoal(){
        int size = detailGoalList.size();
        int count = 0;
        
        for (int i = 0; i < size; i++){
            if(detailGoalList.get(i).isGoal() == true){
                count++;
            }
        }
        System.out.println("checkIsGoal >> size : " + size + " count : " + count);
        if (count == size){
            return true;
        }
        else {
        	return false;
        }
    }
}
