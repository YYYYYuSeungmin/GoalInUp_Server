package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DB.DB_Conn;

public class UserDAO {
    private Connection conn = null;
    private User user = new User();
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    String sql;
    
    public boolean InsertMember(User user){
    	int res = 0;
    	try {
			conn = DB_Conn.getConnection();
			
			sql = "insert into account(id, pw, name, pnum, birth) values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPw());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getPhoneNum());
			pstmt.setString(5, user.getBirth());
			
			res = pstmt.executeUpdate();

		    if(pstmt != null) 
		    	pstmt.close();
		    if(conn != null) 
		    	conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	if (res > 0) return true;
    	else return false;
    }
    
    public boolean loginMember(String id, String pw) {
    	try {
    		conn = DB_Conn.getConnection();
    		sql = "Select pw from account where id = ?";
    		pstmt = conn.prepareStatement(sql);
    		
    		pstmt.setString(1, id);
    		
    		ResultSet res = pstmt.executeQuery();
    		
    		if (res.next()) {
    			String savePW = res.getString("pw");
    			if (pw.equals(savePW)) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    public boolean updateMember(User user) {
    	int res = 0;
    	try {
    		conn = DB_Conn.getConnection();
    		sql = "UPDATE account SET name = ?, pnum = ?, birth = ? WHERE id = ?";
    		pstmt = conn.prepareStatement(sql);
    		System.out.println("유저 아이디 : " + user.getId());
    		
    		pstmt.setString(1, user.getName());
    		String pnum = user.getPhoneNum().replace("-", "");
    		String birth = user.getBirth().replace("-", "");
    		pstmt.setString(2, pnum);
    		pstmt.setString(3, birth);
    		pstmt.setString(4, user.getId());
    		
    		res = pstmt.executeUpdate();
    		
    		if(pstmt != null) 
		    	pstmt.close();
		    if(conn != null) 
		    	conn.close();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	if (res > 0) return true;
    	else return false;
    }
    
    public boolean deleteMember(String userID) {
    	int res = 0;
    	try {
    		conn = DB_Conn.getConnection();
    		sql = "DELETE FROM account WHERE id = ?";
    		pstmt = conn.prepareStatement(sql);
    		
    		pstmt.setString(1, userID);
    		
    		res = pstmt.executeUpdate();
    		if(pstmt != null) 
		    	pstmt.close();
		    if(conn != null) 
		    	conn.close();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	if (res > 0) return true;
    	else return false;
    }
    
    public User receiveMember(BufferedReader br) {
    	User user = new User();
    	try {
			user.setId(br.readLine());
			user.setPw(br.readLine());
			user.setName(br.readLine());
			user.setBirth(br.readLine());
			user.setPhoneNum(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return user;
    }
	public User getUser(String id) {
		User user = new User();
		String sql = "SELECT * FROM account where id = ?";
		try {
			conn = DB_Conn.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			rs.next();
			user.setId(rs.getString(1));
			user.setPw(rs.getString(2));
			user.setName(rs.getString(3));
			user.setPhoneNum(rs.getString(4));
			user.setBirth(rs.getString(5));
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
}
