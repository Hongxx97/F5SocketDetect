/**
 * Copyright 2003 (C) PANLAB ，All Rights Reserved.
 * 日期         作者 			动作
 * 2003-10-20   青蛙                     创建
 */
package com.ist.common.properties;

import java.io.File;
/**
 * <p>Title: 基础类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:  </p>
 * @author
 * @version 1.0
 */

public class SysConfig {

    /** 系统主要的配置文件名称。 */
    private static final String SYSTEM_CONFIG_FILENAME = "system_config.xml";

    /** 这个类从system_init.properties得到它。*/
    public static String SysHomeDir = null;

    /** XML 属性 */
    private static XMLProperties properties = null;

    /**
     * 从系统初始化的Servlet中取得系统配置主目录
     * @return SysHomeDir 系统的主目录
     */
    public static String getSysHomeDir() {
        if (SysHomeDir == null) {
            SysHomeDir = InitSystem.getSysHomeDir();
            if (SysHomeDir == null) {
                SysHomeDir = System.getProperty("SysHomeDir");
            }
        }
        return SysHomeDir;
    }

    /**
     * 系统主目录是否可以读取
     * @return isSysHomeDirReadable()  是否可以读取
     */
    public static boolean isSysHomeDirReadable() {
        return (new File(getSysHomeDir())).canRead();
    }

    /**
     * 系统主目录是否可以写
     * @return isSysHomeDirWritable() 是否可以写
     */
    public static boolean isSysHomeDirWritable() {
        return (new File(getSysHomeDir())).canWrite();
    }

    /**
     * @param name 属性名称
     * @return 返回属性值
     */
    public static String getProperty(String name) {
        loadProperties();
        return properties.getProperty(name);
    }

    /**
     * 设置系统属性
     * @param name 属性名称
     * @param value 属性值
     */
    public static void setProperty(String name, String value) {
        loadProperties();
        properties.setProperty(name, value);
    }

    /**
     * 删除属性
     * @param name 属性名称
     */
    public static void deleteProperty(String name) {
        loadProperties();
        properties.deleteProperty(name);
    }

    /**
     * 装载系统属性文件
     */
    private synchronized static void loadProperties() {
        if (properties == null) {
            if (SysHomeDir == null) {
                SysHomeDir =SysConfig.getSysHomeDir();
            }
            //System.out.println(SysHomeDir + File.separator + SYSTEM_CONFIG_FILENAME);
            properties = new XMLProperties(SysHomeDir + File.separator + SYSTEM_CONFIG_FILENAME);
            
        }
    }

    //==========================================================================
    /**
     * @see 获得数据库类型
     * @return String 数据库的类型值（“oracle”、“db2”、“informix”）。
     */
    public static String getDbType() {
        String dbType = "";
        try {
            dbType = getProperty("database.dbtype").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return dbType;
        }
    }
    /**
     * 提供properties
     * @return
     */
    public static XMLProperties getXmlObj()
    {
    	loadProperties();
        return properties;
    } 
}
