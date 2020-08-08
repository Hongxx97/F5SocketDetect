package com.ist.teller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketTest {
	
	
	public static void main(String[] args) {
		
		Socket sc = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			
			sc = new Socket("50.1.5.155",10012);
			in = sc.getInputStream();
			out = sc.getOutputStream();
			
			
			byte[] reqMsg = null;
			String path = "M:/0126_20190116000001.xml";
			reqMsg = readFile(path);
			System.out.println(new String(reqMsg,"UTF-8"));
			String headStr = "00000000" + reqMsg.length;
			headStr = headStr.substring(headStr.length() - 8);
			//��������
			String reqStr = new String(reqMsg);
			System.out.println("�ж����������"+in.available());
			reqStr = headStr + reqStr;
			out.write(reqStr.getBytes());
			out.flush();
			
			
			
			Thread.sleep(30000);
			byte[] resMsg = new byte[in.available()];
			in.read(resMsg);
			String resStr = new String(resMsg,"UTF-8");
			System.out.print("�������ݣ�"+resStr);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			// �ر����������
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out = null;
			}
			try {
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
	}
	public static byte[] readFile(String srcFile) throws Exception {
		byte[] reqMsg = null;
		InputStream fi = null;
		try {
			fi = new FileInputStream(srcFile);
			reqMsg = new byte[fi.available()];
			fi.read(reqMsg);
		} finally {
			if (fi != null)	fi.close();
			fi=null;
		}
		return reqMsg;
	}
}
