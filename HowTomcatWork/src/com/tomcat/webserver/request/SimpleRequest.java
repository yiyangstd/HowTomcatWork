package com.tomcat.webserver.request;

import java.io.IOException;
import java.io.InputStream;

public class SimpleRequest {
	private InputStream input;
	private String uri;
	
	public SimpleRequest(InputStream input){
		this.input = input;
	}
	
	public void parse(){
		StringBuffer request = new StringBuffer();
		int i = 0;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
				for(int j = 0; j < i; j ++){
					request.append((char) buffer[j]);
				}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(request.toString());
		this.uri = parseUri(request.toString());
	}
	
	public String getUri(){
		return uri;
	}
	
	private String parseUri(String requset){
		int index1, index2;
		index1 = requset.indexOf(" ");
		if(index1 != -1){
			index2 = requset.indexOf(" ", index1 + 1);
			if(index2 > index1){
				return requset.substring(index1, index2);
			}
		}
		return null;
	}
}
