package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import User.User;
import User.UserDAO;
import java.lang.reflect.Field;
import java.nio.charset.Charset;


public class Server {
	ServerSocket serverSocket = null; //서버소켓 생성
	final int PORT = 1234;
	
	ArrayList<ReceiveThread> childList = new ArrayList();
	String msg;
	int count = 0;
	public Server() {
		try {
			serverSocket = new ServerSocket(PORT); //서버소켓 생성
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("------ Goal In Up Start ------");

		while (true) { //서버 시작
			try {
				Socket child = null; //소켓 생성
				child = serverSocket.accept(); //새로운 클라이언트 접속 대기
				System.out.println(count++ + "번째 연결");
				ReceiveThread Thread = new ReceiveThread(child); //새로운 자식 스레드에게 정보 전달
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
