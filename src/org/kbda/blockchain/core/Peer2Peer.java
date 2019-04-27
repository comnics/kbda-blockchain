package org.kbda.blockchain.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Peer2Peer {

	private int port;
	private ArrayList<Socket> peers;
	private DataOutputStream outputStream;
	
	private Thread thServer;
	private boolean isRunningServer;
	
	private ServerSocket server;
	private Socket socket = null;

	public Peer2Peer(int port) {
		this.port = port;
		peers = new ArrayList<>();
		
		thServer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					listen();
					System.out.println("Connection Ended.");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public void start() {
		if(thServer.isAlive()) {
			System.out.println("Server is already running.");
			return ;
		}
		
		isRunningServer = true;
		thServer.start();		
	}
	
	public void stop() throws IOException{
		isRunningServer = false;
		try {
			thServer.interrupt();
			socket.close();
		} catch(NullPointerException e) {
			System.out.println("Null pointer when closing server socket.");
		}
		
		System.out.println("Server Stopped.");
	}

	private void listen() throws IOException, SocketTimeoutException {
		System.out.println("Server starting...");
		
		server = new ServerSocket(this.port);
		server.setSoTimeout(3000);
		
		Socket socket;
		while (isRunningServer) {
			try {
				socket = server.accept();
				System.out.println("Passwd Accept");
				
				peers.add(socket);
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void connect(Socket socket) {
		try {
			outputStream = new DataOutputStream(socket.getOutputStream());
			outputStream.writeUTF("ping");
			outputStream.flush();
		} catch(IOException e) {
			
		}
	}

}
