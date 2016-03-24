package com.github.yev;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

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
            System.err.println("Error while detecting current working dir. Reason:" + e.getMessage());
        }
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);

        WebDriver webDriver = findWebDriverByReflection(tr.getInstance()    );
        if (webDriver == null) {
            System.err.println(String.format("The test class '%s' does not have any field/method of type 'org.openqa.selenium.WebDriver'. " +
                    "ScreenshotTestListener can not continue.", tr.getInstance().getClass().getName()));
            return;
        }

        File f = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            final long timestamp = new Date().getTime();
            Path screenshotPath = Paths.get(currentDir, "target", "screenshot_" + tr.getMethod().getMethodName() + "_" + timestamp + ".png");
            System.out.println("copying " + screenshotPath);
            Files.copy(f.toPath(), screenshotPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("error during the screenshot copy file operation:" + e.getMessage());
        }

    }
    //default visibility of this method for testing purposes
    WebDriver findWebDriverByReflection(Object obj) {
        Class<?> c = obj.getClass();
        Field[] fields = c.getDeclaredFields();
        WebDriver webDriver = null;
        for (Field eachField : fields) {
            eachField.setAccessible(true);
            if (eachField.getType() == WebDriver.class) {
                try {
                    webDriver = (WebDriver) eachField.get(obj);

                } catch (IllegalAccessException e) {
                    System.err.println(String.format("error accessing [%s] property of the [%s] instance", eachField.getName(),
                            obj.getClass().getName()));
                    return null;
                } finally {
                    eachField.setAccessible(false);
                }
                break;
            }
        }
        if (webDriver == null){
            webDriver = tryToFindWebDriverInPublic(obj);
        }
        if (webDriver == null){
            webDriver = tryToFindWebDriverInPrivate(obj);
        }
        return webDriver;
    }

    /**
     * Try to find out the WebDriver within public methods, including the inherited methods
     * @param obj
     * @return
     */
    private static WebDriver tryToFindWebDriverInPublic(Object obj){
        WebDriver webDriver = null;
        Method[] methods = obj.getClass().getMethods();
        return processMethods(methods, obj);
    }

    private static WebDriver tryToFindWebDriverInPrivate(Object obj){
        WebDriver webDriver = null;
        Method[] methods = obj.getClass().getDeclaredMethods();
        return processMethods(methods, obj);
    }

    private static WebDriver processMethods(Method[] methods, Object obj){
        for (Method eachMethod : methods) {
            if (eachMethod.getReturnType() == WebDriver.class) {
                eachMethod.setAccessible(true);
                try {
                    return (WebDriver) eachMethod.invoke(obj);
                } catch (Exception e) {
                    System.err.println(String.format("error accessing [%s] method of the [%s] instance. Error: %s",
                            eachMethod.getName(), obj.getClass().getName(), e.getMessage()));
                }finally {
                    eachMethod.setAccessible(false);
                }
            }
        }
        return null;
    }
}
