package prometheus.messages.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class Loger {

    static {// src/main/resources/
        PropertyConfigurator.configure("config/Log4j.properties");
    }

    public static final Logger Info_log = Logger.getLogger("message");

}
