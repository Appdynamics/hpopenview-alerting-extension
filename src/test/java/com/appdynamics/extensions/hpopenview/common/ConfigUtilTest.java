/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 *  All Rights Reserved.
 *  This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 *  The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.extensions.hpopenview.common;


import com.appdynamics.extensions.hpopenview.Configuration;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class ConfigUtilTest {
    ConfigUtil<Configuration> configUtil = new ConfigUtil<Configuration>();

    @Test
    public void canLoadLinuxConfigFile() throws FileNotFoundException {
        Configuration configuration = configUtil.readConfig(this.getClass().getResource("/conf/config.linux.yaml").getFile(),Configuration.class);
        Assert.assertTrue(configuration != null);
    }

    @Test
    public void canLoadWindowsConfigFile() throws FileNotFoundException {
        Configuration configuration = configUtil.readConfig(this.getClass().getResource("/conf/config.windows.yaml").getFile(),Configuration.class);
        Assert.assertTrue(configuration != null);
    }
}
