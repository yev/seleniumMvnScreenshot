package com.github.yev;

import com.github.yev.mocks.*;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

/**
 * Created by admin on 3/6/16.
 */
public class FailTestScreenshotListenerTest {

    private ITestResult testResult = mock(ITestResult.class);;
    private FailTestScreenshotListener myListener = new FailTestScreenshotListener();

    @Test
    public void testSimplePrivateField() throws Exception {
        assertEquals(myListener.findWebDriverByReflection(new NoInheritancePrivateField()).getClass(), MockWebDriver.class);
    }

     @Test
     public void testSimplePrivateMethodNoInheritance() throws Exception {
         assertEquals(myListener.findWebDriverByReflection(new NoInheritancePrivateMethod()).getClass(), MockWebDriver.class);
     }

     @Test
     public void testSimplePublicMethodNoInheritance() throws Exception {
         assertEquals(myListener.findWebDriverByReflection(new NoInheritancePublicMethod()).getClass(), MockWebDriver.class);
     }
     @Test
     public void testSimplePublicFieldNoInheritance() throws Exception {
         assertEquals(myListener.findWebDriverByReflection(new NoInheritancePublicField()).getClass(), MockWebDriver.class);
     }

     @Test
     public void testSimplePublicFieldWithInheritance() throws Exception {
         assertEquals(myListener.findWebDriverByReflection(new InheritancePublicField()).getClass(), MockWebDriver.class);
     }

}
