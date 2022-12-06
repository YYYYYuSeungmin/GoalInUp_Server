package User;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
	
    private String name;
    private String userid;
    private String userpw;
    private String birth;
    private String phoneNum;


    public void setName(String name){
        this.name = name;
    }
    public void setId(String id){
        this.userid = id;
    }
    public void setPw(String pw){
        this.userpw = pw;
    }
    public void setBirth(String birth){
        this.birth = birth;
    }
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return userid;
    }

    public String getPw() {
        return userpw;
    }

    public String getBirth() {
        return birth;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}