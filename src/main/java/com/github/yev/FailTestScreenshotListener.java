package com.github.yev;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * TestNg listeners which is responsible to make call the Selenium Screenshot API and store the images in
 * target directory of current maven project.
 * Next, those images can be used after by jenkins job to send then as attachments in email notification.
 * TODO: we print error messages to system.err, for don't importing any log dependency. Find out more elegant way
 * User: yev
 * Date: 6/7/15
 * Time: 21:33
 */
public class FailTestScreenshotListener<T> extends TestListenerAdapter {
  private static String currentDir = null;

  static {
    try {
      currentDir = new File(".").getCanonicalPath();
    } catch (IOException e) {
      System.err.println("Error while detecting current working dir. Reason:"+e.getMessage());
    }
  }

  @Override
  public void onTestFailure(ITestResult tr) {
    super.onTestFailure(tr);

    WebDriver webDriver = findWebDriverByReflection(tr);
    if ( webDriver == null){
      System.err.println( String.format("The test class '%s' does not have any field of type 'org.openqa.selenium.WebDriver'. " +
              "ScreenshotTestListener can not continue.", tr.getClass().getName()));
      return;
    }

    File f = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
    try {
      final long timestamp = new Date().getTime();
      Path screenshotPath = Paths.get(currentDir, "target", "screenshot_" + tr.getMethod().getMethodName() + "_"+ timestamp+".png");
      System.out.println("copying "+screenshotPath);
      Files.copy(f.toPath(),screenshotPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      System.err.println("error during the screenshot copy file operation:" + e.getMessage());
    }

  }

  private WebDriver findWebDriverByReflection(ITestResult tr){
    Class<?> c = tr.getInstance().getClass();
    Field[] fields = c.getDeclaredFields();
    WebDriver webDriver = null;
    for (Field eachField: fields){
      if (eachField.getType() == WebDriver.class){
        try {
          webDriver = (WebDriver) eachField.get(tr.getInstance());
        } catch (IllegalAccessException e) {
          System.err.println(String.format("error accessing [%s] property of the [%s] instance", eachField.getName(), tr.getClass().getName()));
          return null;
        }
        break;
      }
    }
    Method[] methods = c.getMethods();
    for (Method eachMethod: methods){
      if (eachMethod.getReturnType()== WebDriver.class){
        try {
          webDriver = (WebDriver) eachMethod.invoke(tr.getInstance());
        } catch (Exception e) {
          System.err.println(String.format("error accessing [%s] method of the [%s] instance", eachMethod.getName(), tr.getClass().getName()));
          return null;
        }
      }
    }
    return webDriver;
  }
}
