/**
 * Copyright 2003 (C) PANLAB ��All Rights Reserved.
 * ����         ���� 			����
 * 2003-10-20   ����                     ����
 */
package com.ist.common.properties;

import java.io.File;
/**
 * <p>Title: ������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:  </p>
 * @author
 * @version 1.0
 */

public class SysConfig {

    /** ϵͳ��Ҫ�������ļ����ơ� */
    private static final String SYSTEM_CONFIG_FILENAME = "system_config.xml";

    /** ������system_init.properties�õ�����*/
    public static String SysHomeDir = null;

    /** XML ���� */
    private static XMLProperties properties = null;

    /**
     * ��ϵͳ��ʼ����Servlet��ȡ��ϵͳ������Ŀ¼
     * @return SysHomeDir ϵͳ����Ŀ¼
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
     * ϵͳ��Ŀ¼�Ƿ���Զ�ȡ
     * @return isSysHomeDirReadable()  �Ƿ���Զ�ȡ
     */
    public static boolean isSysHomeDirReadable() {
        return (new File(getSysHomeDir())).canRead();
    }

    /**
     * ϵͳ��Ŀ¼�Ƿ����д
     * @return isSysHomeDirWritable() �Ƿ����д
     */
    public static boolean isSysHomeDirWritable() {
        return (new File(getSysHomeDir())).canWrite();
    }

    /**
     * @param name ��������
     * @return ��������ֵ
     */
    public static String getProperty(String name) {
        loadProperties();
        return properties.getProperty(name);
    }

    /**
     * ����ϵͳ����
     * @param name ��������
     * @param value ����ֵ
     */
    public static void setProperty(String name, String value) {
        loadProperties();
        properties.setProperty(name, value);
    }

    /**
     * ɾ������
     * @param name ��������
     */
    public static void deleteProperty(String name) {
        loadProperties();
        properties.deleteProperty(name);
    }

    /**
     * װ��ϵͳ�����ļ�
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
     * @see ������ݿ�����
     * @return String ���ݿ������ֵ����oracle������db2������informix������
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
     * �ṩproperties
     * @return
     */
    public static XMLProperties getXmlObj()
    {
    	loadProperties();
        return properties;
    } 
}
