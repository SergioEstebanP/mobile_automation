package utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import net.thucydides.core.util.SystemEnvironmentVariables;

public class Configuration {

    private String env;
    private String appName;
    private String executionPlatform;

    private static Config runConfig;
    private static Configuration configuration;


    private Configuration() {
        this.initializeEmptyVariables();
        this.setEnvVariables();
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    // INITIALIZING FUNCTIONS
    private void initializeEmptyVariables() {
        runConfig = ConfigFactory.load("run");
    }

    private void setEnvVariables() {
        env = runConfig.getString("env");
        appName = runConfig.getString("appName");
        executionPlatform = SystemEnvironmentVariables.createEnvironmentVariables().getProperty("appium.platformName");
    }

    // ACCESSOR METHODS
    public String getEnv() {
        return this.env;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getExecutionPlatform() {
        return this.executionPlatform.toLowerCase();
    }
}