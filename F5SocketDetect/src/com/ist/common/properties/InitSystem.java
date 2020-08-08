/**
 * Copyright 2003 (C) PANLAB ��All Rights Reserved.
 * ����         ���� 			����
 * 2003-10-20   ����                     ����
 */
package com.ist.common.properties;

import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;



/**
 * <p>Title: ������:ϵͳ������ʼ����������</p>
 * <p>Description: ����ϵͳ�����ļ���Ŀ¼,ͨ����Ŀ¼���Բ鿴ȫ��ϵͳ�����ļ�</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class InitSystem extends HttpServlet {
    
    /** ϵͳ�����ļ���Ŀ¼ */
    private static String SysHomeDir = "";
    
    public String getSysHomeDirFromProperties() {
    	
    	try{
    		String strClasspath = this.getClass().getResource("").getPath();
    		String SysHomeDir = URLDecoder.decode(strClasspath,"UTF-8");  //new String((strClasspath).getBytes("ISO-8859-1"),"GBK");
	    	//SysHomeDir=SysHomeDir.substring(0,SysHomeDir.indexOf("/classes/com/ist/common/properties/"));
    		SysHomeDir=SysHomeDir.substring(0,SysHomeDir.indexOf("/com/ist/common/properties/"));
    		return SysHomeDir;	    	
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return "";
    	
    }


    /**
     * ��ʼ��
     * @throws ServletException
     */
    @Override
	public void init() throws ServletException {
        ServletConfig conf = this.getServletConfig();
        SysHomeDir = conf.getInitParameter("SysHomeDir");
        if (SysHomeDir != null && !SysHomeDir.equals("")) {
            SysHomeDir = SysHomeDir.trim();
            while (SysHomeDir.endsWith("/") || SysHomeDir.endsWith("\\")) {
                SysHomeDir = SysHomeDir.substring(0, SysHomeDir.length()-1);
            }
        }
    }

    /**
     * ȡ��ϵͳ�����ļ���Ŀ¼
     * @return ϵͳעĿ¼
     */
    public static String getSysHomeDir(){
        if(SysHomeDir.equals("")){
            SysHomeDir = null;
        }
        if(SysHomeDir==null){
            InitSystem init = new InitSystem();
            SysHomeDir = init.getSysHomeDirFromProperties();
        }
        return SysHomeDir;
    }

   
}