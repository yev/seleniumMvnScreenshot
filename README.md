# Selenium auto screenshots for failed TestNg 
Allow your **Selenium** suites to store automatically **screenshots** taken for every **failed** test in **maven** target folder for further usage. For example, you can include the screenshot of a failed test in email sent by your CI-build.

##Configuration
1. Any configuration at all - just put this jar to your classpath and annotate your class with special annotation (see below).
2. No third-party dependencies.

## Pre-requirements
1. JDK 1.7 minimum


## Usage

1. Add the following maven dependency in you ```pom.xml``` file:

    ```xml 
    <dependency>
      <groupId>com.github.yev</groupId>
      <artifactId>screenshot</artifactId>
      <version>1.1</version>
    </dependency>
    ```
    
2. Add this annotation ```@org.testng.annotations.Listeners(org.yev.selenium.testng.FailTestScreenshotListener.class)``` to your TestNG Selenium class:

    ![listener example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/ListenerExample.png)
    
3. The plugin will find by reflection the webDriver instance you are using and will do the rest for you.

## Result
After running your testNg selenium tests with maven, you will find for each failed test the screenshot which was taken automatically when this particular test failed.
![maven target dir example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/mvnTargerFolder.png)

## Integration with Jenkins build
It's very useful to get the screenshot of failed test directly within the email alert sent by Jenkins when build becomes unstable. So for our example case the jenkins config will be following:
![jenkins conf example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/selenium_Config_Jenkins_.png)

