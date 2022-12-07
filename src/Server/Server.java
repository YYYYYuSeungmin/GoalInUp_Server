package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import User.User;
import User.UserDAO;
import java.lang.reflect.Field;
import java.nio.charset.Charset;


public class Server {
	ServerSocket serverSocket = null; //�������� ����
	final int PORT = 1234;
	
	ArrayList<ReceiveThread> childList = new ArrayList();
	String msg;
	int count = 0;
	public Server() {
		try {
			serverSocket = new ServerSocket(PORT); //�������� ����
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("------ Goal In Up Start ------");

		while (true) { //���� ����
			try {
				Socket child = null; //���� ����
				child = serverSocket.accept(); //���ο� Ŭ���̾�Ʈ ���� ���
				System.out.println(count++ + "��° ����");
				ReceiveThread Thread = new ReceiveThread(child); //���ο� �ڽ� �����忡�� ���� ����
				childList.add(Thread);
				
				Thread.run();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			} 		
		}
	}
	
	public static void main(String[] args){
		Server server = new Server();
	}
}
