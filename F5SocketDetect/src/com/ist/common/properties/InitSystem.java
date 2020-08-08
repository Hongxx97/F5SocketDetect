/**
 * Copyright 2003 (C) PANLAB ，All Rights Reserved.
 * 日期         作者 			动作
 * 2003-10-20   青蛙                     创建
 */
package com.ist.common.properties;

import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;



/**
 * <p>Title: 基础类:系统启动初始化参数设置</p>
 * <p>Description: 设置系统配置文件主目录,通过本目录可以查看全部系统配置文件</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class InitSystem extends HttpServlet {
    
    /** 系统配置文件主目录 */
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
     * 初始化
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
     * 取得系统配置文件主目录
     * @return 系统注目录
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