package Goal;

import java.util.Date;

public class Goal {
	private int gID; //(ū)��ǥ ID
	private String userID; //��ǥ�� �����ϴ� ���� ID
	private String title; //��ǥ Ÿ��Ʋ(����)
	private boolean goal; //���� ����
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
