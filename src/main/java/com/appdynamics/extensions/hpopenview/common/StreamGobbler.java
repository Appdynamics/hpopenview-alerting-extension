/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 *  All Rights Reserved.
 *  This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 *  The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.extensions.hpopenview.common;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * As mentioned http://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=2
 */
public class StreamGobbler extends Thread{

    private static Logger logger = Logger.getLogger(StreamGobbler.class);

    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }

    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null) {
                logger.debug(type + ">" + line);
            }
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
