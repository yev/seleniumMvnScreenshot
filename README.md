# auto screenshot from selenium for TestNg
Allow your selenium suites to store automatically screenshots taken for every failed test in maven target folder for further usage. For example, you can include the screenshot of a failed test in email sent by your CI-build.

## Usage

1. Add the following maven dependency in you ```pom.xml``` file:

    ```xml 
    <dependency>
      <groupId>com.github.yev</groupId>
      <artifactId>screenshot</artifactId>
      <version>0.2</version>
    </dependency>
    ```
    
2. Add this annotation ```@org.testng.annotations.Listeners(FailTestScreenshotListener.class)``` to your TestNG Selenium class:

    ![listener example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/ListenerExample.png)
    
3. The plugin will find by reflection the webDriver instance you are using and will do the rest for you.

## Result
After running your testNg selenium tests with maven, you will find for each failed test the screenshot which was taken automatically when this particular test failed.
![maven target dir example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/mvnTargerFolder.png)

## Integration with Jenkins build
It's very useful to get the screenshot of failed test directly within the email alert sent by Jenkins when build becomes unstable. So for our example case the jenkins config will be following:
![jenkins conf example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/selenium_Config_Jenkins_.png)

