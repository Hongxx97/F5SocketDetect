/**
 * Copyright 2003 (C) PANLAB ��All Rights Reserved.
 * ����         ���� 			����
 * 2003-10-20   ����                     ����
 */
package com.ist.common.properties;

/**
 * <p>Title: ������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.io.*;

import org.apache.log4j.Logger;
import com.ist.common.LogUtils;

public class XmlReader {
    //log4j
    private static Logger logger = LogUtils.getLogger(SysConfig.class.getName());

    public XmlReader() {
    }
    /**
     * @param key ��������
     * @return ��������ֵ
     */
    public String getProperty(String key) {

        String xmlFileName="";
        String action="";

        xmlFileName = key.substring(0,key.indexOf("."));
        xmlFileName = xmlFileName+".xml";
//        logger.debug("xmlFileName-->"+xmlFileName);
        action = key.substring(key.indexOf(".")+1,key.length());
//        logger.debug("action-->"+action);
        XMLProperties properties = new XMLProperties(SysConfig.getSysHomeDir() + File.separator + xmlFileName);
        return properties.getProperty(action);
    }

    public static void main(String[] args) {
        XmlReader xmlReader1 = new XmlReader();
    }
}
