
# Welcome to the repository for Selenium auto screenshots for failed TestNg tests framework
--

This is an open source framework implementing TestNG and Selenium 2 , giving you a possibilty to  automatically store **screenshots**, taken for every **failed** test in **maven** target folder, for their further investigation.

It features: 

* Taking a **screenshot** of a **failed test**
* Including the **screenshot** to an **email sent** by your **CI-build**.
* Stocking taken **screenshots** in easy managable way

###Getting started
--

1. Put this jar to your classpath and annotate your class with special annotation ([see below](#annotation)).
2. No third-party dependencies.

### Development Prerequisites
--

1. JDK 1.7 +
2. Eclipse
3. IntelliJ IDEA
4. Apache Maven 3.0 +
5. JUnit 4.10 +
6. TestNG 6.0 +
6. Selenium 2.0 +
7. Jenkins

### How to use
--
 1. Adding the following maven dependency in you ```pom.xml``` file:


    ```xml 
    <dependency>
      <groupId>com.github.yev</groupId>
      <artifactId>screenshot</artifactId>
      <version>1.1</version>
    </dependency>
    ```

 2. <a name="annotation"></a> Adding next annotation: ```@org.testng.annotations.Listeners(org.yev.selenium.testng.FailTestScreenshotListener.class)``` to your TestNG Selenium class:

![listener example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/ListenerExample.png)

 3. Subsequently the plugin will find by reflection the WebDriver instance you are using and will do the rest for you.

### Result
--
After running your TestNg selenium tests with Maven, you will find for each failed test the screenshot which was taken automatically when this particular test failed.

![maven target dir example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/mvnTargerFolder.png)

### Integration with Jenkins build
--
It could be very useful to get the screenshot of failed test directly with email alert notification sent by Jenkins, when build becomes unstable. 


For our example case the jenkins config will be following:


![jenkins conf example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/selenium_Config_Jenkins_.png)

### License
--
Apache License, Version 2.0
http://www.apache.org/licenses/LICENSE-2.0

