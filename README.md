[![Maven Package](https://github.com/yev/seleniumMvnScreenshot/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/yev/seleniumMvnScreenshot/actions/workflows/maven-publish.yml)

# Welcome to the repository for Selenium auto screenshots for failed TestNg tests
-----------

This is an open source framework integrating TestNG, Selenium 2 and maven, giving you a possibilty to  automatically store **screenshots**, taken for every **failed** test in **maven** target folder, for their further investigation.


It features: 

* Taking a **screenshot** of a **failed test**
* Including the **screenshot** to an **email sent** by your **CI-build**.
* Stocking taken **screenshots** in easy managable way


Getting started
-----------

###Configuration

1. Put this jar to your classpath and annotate your class with special annotation ([see below](#annotation)).
2. No third-party dependencies.

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
    
 3. 	Subsequently the plugin will find by reflection the **WebDriver instance** you are using and will do the rest for you.

### Result
--
After running your TestNg selenium tests with Maven, you will find for each failed test the screenshot which was taken automatically when this particular test failed.


![maven target dir example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/mvnTargerFolder.png)

### Integration with Jenkins build
--
It could be very useful to get the screenshot of failed test directly with email alert notification sent by Jenkins, when build becomes unstable. 


For our example case the jenkins config will be following:


![jenkins conf example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/selenium_Config_Jenkins_.png)


