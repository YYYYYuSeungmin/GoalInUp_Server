package Server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Goal.DetailGoal;
import Goal.DetailGoalDAO;
import Goal.Goal;
import Goal.GoalDAO;
import User.User;
import User.UserDAO;

public class ReceiveThread implements Runnable {
	Socket child = null;

	InputStream is;
	OutputStream os;

	BufferedReader br = null;
	PrintWriter pw = null;

	User user = null;
	UserDAO uDAO;
	GoalDAO gDAO;
	DetailGoalDAO detailGoalDAO;
	String msg;

	// 쓰레드 생성자
	public ReceiveThread(Socket socket) {
		child = socket;

		System.out.println(child.getInetAddress() + "님 입장");
		try {
			is = child.getInputStream(); // 사용자로 부터 데이터를 입력받는 스트림
			os = child.getOutputStream(); // 사용자로 데이터 전송

			br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			pw = new PrintWriter(new OutputStreamWriter(os, "utf-8"), true);

		} catch (IOException e) {
			e.printStackTrace();
		}
		run();
	}

	@Override
	public void run() {
		try {
			msg = br.readLine(); // 클라이언트 요청 기능 분별
			System.out.println("요청 발생 msg : " + msg);
			boolean check;
			String okSign;

			switch (msg) {
			case "A01": // A01메세지는 회원가입을 하겠다는 의미.
				System.out.println("회원가입 요청" + child.getInetAddress());
				uDAO = new UserDAO();
				User user = uDAO.receiveMember(br);

				check = uDAO.InsertMember(user);

				if (check == true) {
					okSign = "0";
				} else {
					okSign = "1";
				}

				pw.println(okSign);
				pw.flush();

				break;
			case "A02": // A02메세지는 로그인의 의미
				uDAO = new UserDAO();
				System.out.println("로그인 요청" + child.getInetAddress());

				String userID = br.readLine();
				String userPW = br.readLine();
				check = uDAO.loginMember(userID, userPW);

				if (check == true) {
					okSign = "0"; // 로그인 허가 = 0; 불가 = 1;
				} else {
					okSign = "1";
				}

				pw.println(okSign); // 로그인 허가/불가 메세지 송신
				pw.flush();
				System.out.println("로그인 여부 : " + okSign);

				if (check == true) {
					User returnUser = uDAO.getUser(userID);

					pw.println(returnUser.getId());
					pw.println(returnUser.getPw());
					pw.println(returnUser.getName());
					pw.println(returnUser.getPhoneNum());
					pw.println(returnUser.getBirth());
					pw.flush();
				}
				break;
				
			case "A03": //회원 정보 수정 요청
				System.out.println("회원 정보 수정 요청");
				
				uDAO = new UserDAO();
				User updateUser = uDAO.receiveMember(br);
				
				System.out.println("유저 받아옴");
				check = uDAO.updateMember(updateUser);
				
				System.out.println("업데이트 완료");
				pw.println(check);
				pw.flush();
				
				break;
				
			case "A04": //회원 탈퇴 메세지
				uDAO = new UserDAO();
				System.out.println("회원 탈퇴 요청");
				
				userID = br.readLine();
				check = uDAO.deleteMember(userID);
				
				pw.println(check);
				pw.flush();
				
				break;

			case "B01": // B01 메세지는 목표 등록 메세지
				Goal goal = new Goal();
				System.out.println("목표 등록 요청" + child.getInetAddress());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

//				goal.setgID(Integer.parseInt(br.readLine()));
				goal.setUserID(br.readLine());
				goal.setTitle(br.readLine());
				goal.setStartDay(br.readLine());
				goal.setEndDay(br.readLine());
				goal.setGoal(Boolean.parseBoolean(br.readLine()));
				System.out.println("골 여부 : " + goal.isGoal());
				System.out.println("goal Title : " + goal.getTitle());
				
				gDAO = new GoalDAO();
				check = gDAO.insertGoal(goal);

				if (check == true) {
					okSign = "0";
				} else {
					okSign = "1";
				}

				pw.println(okSign);
				pw.flush();

				break;

			case "B02": // B02 메세지는 목표리스트 반환 요청 메세지
				System.out.println("목표리스트 반환 요청");

				String findUserID;

				userID = br.readLine(); // 사용자의 아이디를 받는다.
				
				gDAO = new GoalDAO();
				ArrayList<Goal> goalList = gDAO.getGoalList(userID);

				int count = goalList.size();

				System.out.println("사용자의 목표 개수 : " + count);
				pw.println(Integer.toString(count)); // 목표 리스트 길이를 알려준다.
				pw.flush();

				for (int i = 0; i < count; i++) {
					pw.println(goalList.get(i).getgID());
					pw.println(goalList.get(i).getUserID());
					pw.println(goalList.get(i).getTitle());
					pw.println(goalList.get(i).getStartDay());
					pw.println(goalList.get(i).getEndDay());
					pw.println(goalList.get(i).isGoal());
					pw.flush();
				}
				System.out.println(userID + "의 목표 리스트 반환 완료");
				break;

			case "B03": // 목표 삭제 요청
				System.out.println("목표 삭제 요청");
				int g_ID = 0;
				boolean removeSuccess = false;
				
				try {
					g_ID = Integer.parseInt(br.readLine());
					gDAO = new GoalDAO();
					removeSuccess = gDAO.removeGoal(g_ID);

					if (removeSuccess == true) {
						pw.println("0");
					} else {
						pw.println("1");
					}

					pw.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}

				break;

			case "B04":
				System.out.println("목표 수정 요청");
				Goal updateGoal = new Goal();

				System.out.println("목표 업데이트 요청" + child.getInetAddress());
				// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

				updateGoal.setgID(Integer.parseInt(br.readLine()));
				updateGoal.setUserID(br.readLine());
				updateGoal.setTitle(br.readLine());
				updateGoal.setStartDay(br.readLine());
				updateGoal.setEndDay(br.readLine());
				updateGoal.setGoal(Boolean.parseBoolean(br.readLine()));
				
				gDAO = new GoalDAO();
				check = gDAO.updateGoal(updateGoal);

				if (check == true) {
					okSign = "0";
				} else {
					okSign = "1";
				}

				pw.println(okSign);
				pw.flush();

				break;
			case "C01": // 세부 목표 리스트 반환 요청
				System.out.println("세부 목표리스트 송신 요청");

				int findGID;
				
				detailGoalDAO = new DetailGoalDAO();
				findGID = Integer.parseInt(br.readLine());
				ArrayList<DetailGoal> detailGoalList = detailGoalDAO.getDetailGoalList(findGID);

				int detailCount = detailGoalList.size();

				System.out.println(findGID + "번호의 세부 목표 개수 : " + detailCount);
				pw.println(Integer.toString(detailCount)); // 목표 리스트 길이를 알려준다.
				pw.flush();

				for (int i = 0; i < detailCount; i++) {
					pw.println(detailGoalList.get(i).getDetail_gID());
					pw.println(detailGoalList.get(i).getgID());
					pw.println(detailGoalList.get(i).getTitle());
					pw.println(detailGoalList.get(i).getContents());
					pw.println(detailGoalList.get(i).getStartDay());
					pw.println(detailGoalList.get(i).getEndDay());
					pw.println(detailGoalList.get(i).isGoal());
					pw.flush();
				}
				System.out.println(findGID + "의 세부 목표 리스트 송신 완료");
				break;
			case "C02":
				System.out.println("세부 목표 등록 요청");
				
				detailGoalDAO = new DetailGoalDAO();
				
				DetailGoal insertDetailGoal = new DetailGoal();
				insertDetailGoal.setgID(Integer.parseInt(br.readLine()));
				insertDetailGoal.setDetail_gID(Integer.parseInt(br.readLine()));
				insertDetailGoal.setTitle(br.readLine());
				insertDetailGoal.setContents(br.readLine());
				insertDetailGoal.setStartDay(br.readLine());
				insertDetailGoal.setEndDay(br.readLine());
				
				insertDetailGoal.setGoal(Boolean.parseBoolean(br.readLine()));
				
				System.out.println("세부 목표 정보 수신 완료");
				check = detailGoalDAO.insertDetailGoal(insertDetailGoal);
				
				pw.println(check);
				pw.flush();
				break;
				
			case "C03":
				System.out.println("세부 목표 수정 요청");
				
				detailGoalDAO = new DetailGoalDAO();
				
				DetailGoal updateDetailGoal = new DetailGoal();
				updateDetailGoal.setgID(Integer.parseInt(br.readLine()));
				updateDetailGoal.setDetail_gID(Integer.parseInt(br.readLine()));
				updateDetailGoal.setTitle(br.readLine());
				updateDetailGoal.setContents(br.readLine());
				updateDetailGoal.setStartDay(br.readLine());
				updateDetailGoal.setEndDay(br.readLine());
				updateDetailGoal.setGoal(Boolean.parseBoolean(br.readLine()));
				
				System.out.println("세부 목표 수정 정보 수신 완료");
				check = detailGoalDAO.updateDetailGoal(updateDetailGoal);
				
				pw.println(check);
				pw.flush();
				break;
				
			case "C04":
				System.out.println("세부 목표 삭제 요청");
				
				detailGoalDAO = new DetailGoalDAO();
				
				int detail_gid = Integer.parseInt(br.readLine());
				check = detailGoalDAO.deleteDetailGoal(detail_gid);
				
				pw.println(check);
				pw.flush();
				break;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (br != null)
					br.close();
				if (pw != null)
					pw.close();
				if (is != null)
					is.close();
				if (os != null)
					os.close();
				if (child != null) {
					System.out.println("사용자 연결 종료. . . " + child.getInetAddress());
					child.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
