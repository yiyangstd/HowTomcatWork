package com.tomcat.webserver;

import java.net.ServerSocket;

public class HttpServer1 {
	private boolean shutdown = false;
	
	public static void main(String[] args){
		HttpServer1 server1 = new HttpServer1();
		server1.await();
	}
	
	public void await(){
		ServerSocket socket = null;
		int port = 8080;
		
	}
}
