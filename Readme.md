```
                 __  __         _      _  _            
                |  \/  |       | |    (_)| |           
                | \  / |  ___  | |__   _ | |  ___      
                | |\/| | / _ \ | '_ \ | || | / _ \     
                | |  | || (_) || |_) || || ||  __/     
                |_|  |_| \___/ |_.__/ |_||_| \___|     
     /\          | |                          | |  (_)              
    /  \   _   _ | |_  ___   _ __ ___    __ _ | |_  _   ___   _ __  
   / /\ \ | | | || __|/ _ \ | '_ ` _ \  / _` || __|| | / _ \ | '_ \ 
  / ____ \| |_| || |_| (_) || | | | | || (_| || |_ | || (_) || | | |
 /_/    \_\\__,_| \__|\___/ |_| |_| |_| \__,_| \__||_| \___/ |_| |_|
                                                                                                                                                                                                                
```
![bugs](https://img.shields.io/badge/Bugs-0-sucess)
![vulnerabilites](https://img.shields.io/badge/Vulnerabilities-2-success)
![codeSmells](https://img.shields.io/badge/Code%20Smells-2-success)
![coverage](https://img.shields.io/badge/Coverage-0.0%25-critical)
![duplications](https://img.shields.io/badge/Duplications-0.0%25-critical)

---

Mobile Automation is the automation framework for Mobile. This devices includes all of the platforms available: Android

## Table of contents
1. [Requirements](#requirements)
2. [Hot to run it?](#how-to-run-it)
    1. [Android Configuration](#android-configuration)
3. [Configuration File](#configuration-file)
4. [Sonarqube](#sonarqube)
5. [Other information](#other-information)

---

## Requirements

To run it locally you will need to install:

- **Java 8**: and not above, this project fails with Java 14 because Serenity dependencies are not adapted and you have to manually add several libraries for compatibility.
- **Node**: node programming language as Appium server is written in Node. 
- **npm**: npm package manager in order to install some dependencies and also Appium server. 
- **Appium**: proxy server to run automatic tests in mobile devices. 
- **Appium Gui**: its not required but it helps to manage server configuration and some capabilites, also provide you with manual sessions in order to inspect the elements in the devices for automation functions. 
- **Appium Doctor**: tool developed from appium team in order to health check all the environment is sucessfully configured. 
- **Android SDK**: to run tests in Android devices is required to have the Android SDK installer in your machine and properly configured. 
- **Docker**: not required but if you want to run sonarqube you will need it. Also could be use to emulate devices, but we are not going to explain that here.
- **Gradle**: in order to execute and maintain the libraries in the project i use Gradle due to its easy use and configuration of the build.gradle file.

## How to run it?
#### Android configuration

Ensure you have all the above software and package installed properly. Remember to set JAVA_HOME, ANDROID_HOME and run Appium doctor to check if some configuration is missing. 

See the file `run.sh` and change ENV variables if need according to your system. And the execute the command: 
```
./run.sh
```

Description: first you have to create a new capabilities set for your execution, this is made in the file `src/test/java/utils/Utils.java` in the `function DesiredCapabilites(...)`  In this case the data is set via code in the capabilities handler. But this information could be obtained from a request, from a properties file or from some default configurations and switching between them. Here is just a POC and the use case is the simplest one. But we should change this for the test obiously.

```
# APPIUM OPTIONS CHANGE ACCORDING TO YOUR DEVICE
appium.platformVersion = 10
appium.deviceName = device-testing

# SERENITY OPTIONS DO NOT CHANGE
serenity.console.colors = true
serenity.project.name = mobileframework
serenity.restart.browser.for.each = feature
serenity.take.screenshots = FOR_EACH_ACTION
webdriver.driver= appium
appium.platformName = Android
appium.app = src/test/resources/app/testing.apk
```

Then you have to attach the device you want to run test into and activate the developers mode. Once its done just type the following command: 
```
./gradlew clean test aggregate -Dcucumber.options="--tags @run"
```
- **gradlew**: is an script to download gradle and orchestrate the whole project and dependencies. 
- **clean**: gradle task to clean build directories and outputs.
- **test**: gradle task to execute the tests. By default it execute test located in `src/test/resources/features` directory.
- **aggregate**: gradle task to generate all the reports with serenity.
- **-Dcucumber.options**: we can pass the tag of the test we want to execute, in this case @run should be passed to execute the test.

You can debug and run applications remotely and no having the real device attached to the machine using abd manager. You need to know the ip of the device. The type in the terminal the following commands: 
```
adb tcpip 5555
adb connect 192.168.1.xxx
adb devices
```

Substitute the `192.168.1.xxx` with your device IP address. The last command will show you the device linked to the machine, then just run the tests. 

---
Then you have to attach the device you want to run test into and activate the developers mode. Once its done just type the following command: 
```
./gradlew clean test aggregate -Dproperties="properties/android.properties"
```
- **gradlew**: is an script to download gradle and orchestrate the whole project and dependencies. 
- **clean**: gradle task to clean build directories and outputs.
- **test**: gradle task to execute the tests. By default it execute test located in `src/test/resources/features` directory.
- **aggregate**: gradle task to generate all the reports with serenity.
- **-Dproperties**: properties files to run the devices

You can debug and run applications remotely and no having the real device attached to the machine using XCode manager. Usually there is no need to configure nothing, when connecting a device then XCode recognise it and allows to you to debug automatically over network via wifi.


To run tests filtering by tags:
```
./gradlew clean test -Dcucumber.options="--tags @run" aggregate
```

You'll see a lot of output in the terminal due to appium server and also Serenity test. If no errors are encountered and all the above requirements are met, then you should wee tests running in your device. 

---

## Configuration File
We can use different parameters in order to customize the test execution. This parameters are set in the configuration file located in: `src/test/resources/run.conf` and the possible values are the following: 

- **env**: we can select the environment to launch the tests. Currently the accepted values are: ["sbx", "stg", "prd"]. Default value = "prd".
- **app**: we have to select the application name we want to download from apk combo web page. Exact matching name.

Also we can pass several parameters in the command execution for a higher level of customization for executing the tests: 
- **serenity.properties**: this parameter is almost required because with this file we specify the device we want to run the tests into.
- **cucumber.options**: here we can pass some options for cucumber, for example the directory with the features. We use this parameter to select different tests to execute by using tags. Available tags are:

| Tag          | Service                                 |  
|:-------------|:----------------------------------------|
| @android     | Run all tests marked for android devices|
| @register    | Run tests for Register feature          |


---

## Sonarqube
This projects counts with Sonarqube statics, if you want to see the whole project and metrics, first you have to deploy sonarqube in local:
```
docker run -d --name sonarqube -p 9000:9000 sonarqube
```
Open `http://localhost:9000/` in your browser and login as admin (admin as password). Create a new project called: `apiframework`mandatory this name. Then just run the following gradle task: 
```
./gradlew sonarqube
```
As soon the task finishes, refresh the browser and you'll be able to see the metrics.

---

## Other information
Here we have some valuable information about configuration, files or other information related to the project. 

Reports made from serenity could be located once the execution is done in the file: `target/site/serenity/index.html`

