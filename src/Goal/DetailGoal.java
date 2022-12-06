package Goal;

public class DetailGoal {
	private int gID; //큰 타이틀 번호
	private int detail_gID; //세부 목표 고유번호
	private String contents; // 세부 목표 내용
	private String startDay; // 세부 목표의 시작 (큰 목표 시작날자 이후여야 함)
	private String endDay; // 세부 목표의 끝 (큰 목표의 끝 날짜 이내여야 함)
	private boolean goal; //성공 여부
	private String title;
	
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    
	public int getgID() {
		return gID;
	}
	public void setgID(int gID) {
		this.gID = gID;
	}
	public int getDetail_gID() {
		return detail_gID;
	}
	public void setDetail_gID(int detail_gID) {
		this.detail_gID = detail_gID;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
	public boolean isGoal() {
		return goal;
	}
	public void setGoal(boolean goal) {
		this.goal = goal;
	}
	
}
