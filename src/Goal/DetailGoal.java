package Goal;

public class DetailGoal {
	private int gID; //ū Ÿ��Ʋ ��ȣ
	private int detail_gID; //���� ��ǥ ������ȣ
	private String contents; // ���� ��ǥ ����
	private String startDay; // ���� ��ǥ�� ���� (ū ��ǥ ���۳��� ���Ŀ��� ��)
	private String endDay; // ���� ��ǥ�� �� (ū ��ǥ�� �� ��¥ �̳����� ��)
	private boolean goal; //���� ����
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
