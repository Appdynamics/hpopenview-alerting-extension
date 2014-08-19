package com.appdynamics.extensions.hpopenview.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.appdynamics.extensions.hpopenview.Configuration;


public class ConfigUtil<T> {

	private static Logger logger = Logger.getLogger(ConfigUtil.class);
	
	public T readConfig(String configFilename,Class<T> clazz) throws FileNotFoundException {
        logger.info("Reading config file::" + configFilename);
        Yaml yaml = new Yaml(new Constructor(Configuration.class));
        T config = (T) yaml.load(new FileInputStream(configFilename));
        return config;
    }
}
