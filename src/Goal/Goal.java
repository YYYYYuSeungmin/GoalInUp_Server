package Goal;

import java.util.Date;

public class Goal {
	private int gID; //(큰)목표 ID
	private String userID; //목표를 소유하는 유저 ID
	private String title; //목표 타이틀(주제)
	private boolean goal; //성공 여부
	private String startDay;
	private String endDay;
	
	public int getgID() {
		return gID;
	}
	public void setgID(int gID) {
		this.gID = gID;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isGoal() {
		return goal;
	}
	public void setGoal(boolean goal) {
		this.goal = goal;
	}

} 
