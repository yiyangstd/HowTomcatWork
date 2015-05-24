package com.tomcat.webserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tomcat.webserver.request.SimpleRequest;
import com.tomcat.webserver.response.SimpleResponse;

public class SimpleServer {
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	private boolean shutdown = false;
	
	public static void main(String[] args){
		Path webRoot = Paths.get(WEB_ROOT);
		if(Files.notExists(webRoot)){
			try {
				Files.createDirectories(webRoot);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SimpleServer server = new SimpleServer();
		server.await();
	}
	
	public void await(){
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!shutdown){
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				SimpleRequest request = new SimpleRequest(input);
				request.parse();
				
				SimpleResponse response = new SimpleResponse(output);
				response.setRequest(request);
				response.setStaticResources();
				
				socket.close();
				shutdown = request.getUri().equals(" /SHUTDOWN");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
