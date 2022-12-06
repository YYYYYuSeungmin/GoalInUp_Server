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

	// ������ ������
	public ReceiveThread(Socket socket) {
		child = socket;

		System.out.println(child.getInetAddress() + "�� ����");
		try {
			is = child.getInputStream(); // ����ڷ� ���� �����͸� �Է¹޴� ��Ʈ��
			os = child.getOutputStream(); // ����ڷ� ������ ����

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
			msg = br.readLine(); // Ŭ���̾�Ʈ ��û ��� �к�
			System.out.println("��û �߻� msg : " + msg);
			boolean check;
			String okSign;

			switch (msg) {
			case "A01": // A01�޼����� ȸ�������� �ϰڴٴ� �ǹ�.
				System.out.println("ȸ������ ��û" + child.getInetAddress());
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
			case "A02": // A02�޼����� �α����� �ǹ�
				uDAO = new UserDAO();
				System.out.println("�α��� ��û" + child.getInetAddress());

				String userID = br.readLine();
				String userPW = br.readLine();
				check = uDAO.loginMember(userID, userPW);

				if (check == true) {
					okSign = "0"; // �α��� �㰡 = 0; �Ұ� = 1;
				} else {
					okSign = "1";
				}

				pw.println(okSign); // �α��� �㰡/�Ұ� �޼��� �۽�
				pw.flush();
				System.out.println("�α��� ���� : " + okSign);

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

			case "B01": // B01 �޼����� ��ǥ ��� �޼���
				Goal goal = new Goal();
				System.out.println("��ǥ ��� ��û" + child.getInetAddress());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

//				goal.setgID(Integer.parseInt(br.readLine()));
				goal.setUserID(br.readLine());
				goal.setTitle(br.readLine());
				goal.setStartDay(br.readLine());
				goal.setEndDay(br.readLine());
				goal.setGoal(Boolean.parseBoolean(br.readLine()));
				System.out.println("�� ���� : " + goal.isGoal());
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

			case "B02": // B02 �޼����� ��ǥ����Ʈ ��ȯ ��û �޼���
				System.out.println("��ǥ����Ʈ ��ȯ ��û");

				String findUserID;

				userID = br.readLine(); // ������� ���̵� �޴´�.
				
				gDAO = new GoalDAO();
				ArrayList<Goal> goalList = gDAO.getGoalList(userID);

				int count = goalList.size();

				System.out.println("������� ��ǥ ���� : " + count);
				pw.println(Integer.toString(count)); // ��ǥ ����Ʈ ���̸� �˷��ش�.
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
				System.out.println(userID + "�� ��ǥ ����Ʈ ��ȯ �Ϸ�");
				break;

			case "B03": // ��ǥ ���� ��û
				System.out.println("��ǥ ���� ��û");
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
				System.out.println("��ǥ ���� ��û");
				Goal updateGoal = new Goal();

				System.out.println("��ǥ ������Ʈ ��û" + child.getInetAddress());
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
			case "C01": // ���� ��ǥ ����Ʈ ��ȯ ��û
				System.out.println("���� ��ǥ����Ʈ �۽� ��û");

				int findGID;
				
				detailGoalDAO = new DetailGoalDAO();
				findGID = Integer.parseInt(br.readLine());
				ArrayList<DetailGoal> detailGoalList = detailGoalDAO.getDetailGoalList(findGID);

				int detailCount = detailGoalList.size();

				System.out.println(findGID + "��ȣ�� ���� ��ǥ ���� : " + detailCount);
				pw.println(Integer.toString(detailCount)); // ��ǥ ����Ʈ ���̸� �˷��ش�.
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
				System.out.println(findGID + "�� ���� ��ǥ ����Ʈ �۽� �Ϸ�");
				break;
			case "C02":
				System.out.println("���� ��ǥ ��� ��û");
				
				detailGoalDAO = new DetailGoalDAO();
				
				DetailGoal insertDetailGoal = new DetailGoal();
				insertDetailGoal.setgID(Integer.parseInt(br.readLine()));
				insertDetailGoal.setDetail_gID(Integer.parseInt(br.readLine()));
				insertDetailGoal.setTitle(br.readLine());
				insertDetailGoal.setContents(br.readLine());
				insertDetailGoal.setStartDay(br.readLine());
				insertDetailGoal.setEndDay(br.readLine());
				insertDetailGoal.setGoal(Boolean.parseBoolean(br.readLine()));
				
				System.out.println("���� ��ǥ ���� ���� �Ϸ�");
				check = detailGoalDAO.insertDetailGoal(insertDetailGoal);
				
				pw.println(check);
				pw.flush();
				break;
				
			case "C03":
				System.out.println("���� ��ǥ ���� ��û");
				
				detailGoalDAO = new DetailGoalDAO();
				
				DetailGoal updateDetailGoal = new DetailGoal();
				updateDetailGoal.setgID(Integer.parseInt(br.readLine()));
				updateDetailGoal.setDetail_gID(Integer.parseInt(br.readLine()));
				updateDetailGoal.setTitle(br.readLine());
				updateDetailGoal.setContents(br.readLine());
				updateDetailGoal.setStartDay(br.readLine());
				updateDetailGoal.setEndDay(br.readLine());
				updateDetailGoal.setGoal(Boolean.parseBoolean(br.readLine()));
				
				System.out.println("���� ��ǥ ���� ���� ���� �Ϸ�");
				check = detailGoalDAO.updateDetailGoal(updateDetailGoal);
				
				pw.println(check);
				pw.flush();
				break;
				
			case "C04":
				System.out.println("���� ��ǥ ���� ��û");
				
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
					System.out.println("����� ���� ����. . . " + child.getInetAddress());
					child.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
