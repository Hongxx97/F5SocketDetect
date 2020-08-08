/**
 * Copyright 2003 (C) PANLAB ，All Rights Reserved.
 * 日期         作者 			动作
 * 2003-10-20   青蛙                     创建
 */
package com.ist.common.properties;

/**
 * <p>Title: 基础类</p>
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
     * @param key 属性名称
     * @return 返回属性值
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
