# seleniumMvnScreenshot
Allow your selenium suites to store automatically screenshots taken for every failed test in maven target folder for further usage. For example, you can include the screenshot of a failed test in email sent by your CI-build.

## Usage
1. Just add *FailTestScreenshotListener* to your TestNG class
![listener example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/ListenerExample.png)
2. The plugin will find by reflection the webDriver instance you are using and will do the rest for you.

## Results
After running your testNg selenium tests with maven, you will find for each failed test the screenshot which was taken automatically when this particular test failed.
![maven target dir example](https://raw.githubusercontent.com/yev/seleniumMvnScreenshot/master/docs/mvnTargerFolder.png)

