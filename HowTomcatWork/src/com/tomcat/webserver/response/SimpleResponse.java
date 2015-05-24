package com.tomcat.webserver.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.tomcat.webserver.SimpleServer;
import com.tomcat.webserver.request.SimpleRequest;

public class SimpleResponse {

	private OutputStream outputStream;
	private SimpleRequest request;
	
	public SimpleResponse(OutputStream output){
		outputStream = output;
	}
	
	public void setRequest(SimpleRequest request){
		this.request = request;
	}
	
	public void setStaticResources(){
		byte[] bytes = new byte[2048];
		FileInputStream fileInput = null;
		try{
			File file = new File(SimpleServer.WEB_ROOT, request.getUri());
			if(file.exists()){
				fileInput = new FileInputStream(file);
				int i = 0;
				while((i = fileInput.read(bytes)) != -1){
					outputStream.write(bytes, 0, i);
				}
			}else{
				String error = "HTTP/1.1 404 File Not Fount\r\n" + 
								"Content-Type: text/html\r\n" +
								"Content-Length: 23\r\n" + "\r\n" + //尼玛 没加这个空格 调试尼玛半天
								"<h1>File Not Found</h1>";
				outputStream.write(error.getBytes());
			}
		}catch(IOException e){
			System.out.println(e);
		}finally{
			if(fileInput != null)
				try {
					fileInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
